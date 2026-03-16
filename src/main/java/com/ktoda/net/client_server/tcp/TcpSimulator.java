package com.ktoda.net.client_server.tcp;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
@SuppressWarnings("TryFinallyCanBeTryWithResources")
public class TcpSimulator {

    static void main(String @NonNull [] args) {
        SimulationInput input = readSimulationInput();

        TcpServer server = new TcpServer(input.serverPort());
        TcpClient client = new TcpClient(input.serverPort(), input.clientPort());

        try {
            server.start();

            log.debug("This is the [{}] thread", Thread.currentThread().getName());
            Thread.sleep(1000); // Give the server a moment to start up and listen for connections (main thread)

            client.connect(server);
            client.send(input.message());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("TCP simulation was interrupted.", e);
            throw new RuntimeException(e);
        } finally {
            client.close();
            server.close();
        }

    }

    private static SimulationInput readSimulationInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Server port ? ");
            int serverPort = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Client port ? ");
            int clientPort = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("message (optional) ? ");
            String message = scanner.nextLine().trim();
            if (message.isEmpty()) {
                message = "Hello from client!";
            }

            return new SimulationInput(serverPort, clientPort, message);
        }
    }

    private record SimulationInput(int serverPort, int clientPort, String message) {
    }

}
