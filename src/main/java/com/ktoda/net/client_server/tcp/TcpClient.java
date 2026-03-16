package com.ktoda.net.client_server.tcp;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j(topic = "TCP-CLIENT")
@Getter
public class TcpClient implements AutoCloseable {
    private final int serverPort;
    private final int clientPort;

    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public TcpClient(int serverPort, int clientPort) {
        this.serverPort = serverPort;
        this.clientPort = clientPort;
        this.socket = new Socket();

        try {
            if (clientPort > 0) {
                this.socket.bind(new InetSocketAddress(clientPort));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect TCP client.", e);
        }
    }

    public void connect(TcpServer server) {
        try {
            socket.connect(server.getAddress());

            out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            log.info("Connected to TCP server on {}.", server.getAddress());

            String ackMessage = in.readLine();
            log.info("Received ACK from TCP server: {}", ackMessage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect TCP client.", e);
        }
    }

    public void send(String message) {
        if (out == null) {
            throw new IllegalStateException("TCP client must connect before sending messages.");
        }

        log.info("Sending message to TCP server: {}", message);
        out.println(message);
    }

    @Override
    public void close() {
        try {
            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }

            if (!socket.isClosed()) {
                socket.close();

                log.trace("Client closed...");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to close TCP client socket.", e);
        }
    }

}
