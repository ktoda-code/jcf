package com.ktoda.net.client_server.tcp;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j(topic = "TCP-SERVER")
@Getter
public class TcpServer implements AutoCloseable {
    private static final String ACK = "ACK MESSAGE: Connection established.";

    private final int port;

    private final ServerSocket serverSocket;
    private Socket clientSocket;

    public TcpServer(int port) {
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(port);
            log.info("Server started on port {}", port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        log.trace("Starting TCP server to accept connections on port {} ...", port);

        Thread serverThread = new Thread(() -> {
            try {
                clientSocket = serverSocket.accept();
                log.info("Accepted TCP client connection from {}.", clientSocket.getRemoteSocketAddress());

                try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8)) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))) {

                        out.println(ACK);
                        log.info("Sent ACK to TCP client: {}", ACK);

                        String message;
                        while ((message = in.readLine()) != null) {
                            log.info("Received message from TCP client: {}", message);
                        }
                    }
                }
            } catch (IOException e) {
                if (!serverSocket.isClosed()) {
                    log.error("Failed to accept connections on port {} ...", port, e);
                }
            }
        }, "tcp-server-thread");

        serverThread.start();
    }


    @Override
    public void close() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }

            if (!serverSocket.isClosed()) {
                serverSocket.close();

                log.trace("Server closed...");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to close TCP server socket.", e);
        }
    }

    public SocketAddress getAddress() {
        return serverSocket.getLocalSocketAddress();
    }

}
