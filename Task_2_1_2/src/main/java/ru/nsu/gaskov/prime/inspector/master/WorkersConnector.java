package ru.nsu.gaskov.prime.inspector.master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ru.nsu.gaskov.prime.inspector.SocketUtils;

/**
 * Manages discovery and communication with worker nodes over sockets.
 */
public class WorkersConnector implements AutoCloseable {

    private final String multicastAddress;
    private final int multicastPort;
    private final String host;
    private final int listenPort;
    private final ServerSocket serverSocket;

    /**
     * Initializes connector with multicast discovery and a listening server socket.
     */
    public WorkersConnector(String multicastAddress,
                            int multicastPort,
                            String host,
                            int listenPort) throws IOException {
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
        this.host = host;
        this.listenPort = listenPort;
        serverSocket = new ServerSocket(listenPort);
    }

    /**
     * Discovers available workers by sending a multicast and accepting connections until timeout.
     */
    public List<Socket> findWorkers(int timeoutMillis) throws IOException {
        SocketUtils.sendMulticastMessage(
                multicastAddress, multicastPort, host, listenPort
        );

        List<Socket> workers = new ArrayList<>();
        long deadline = System.currentTimeMillis() + timeoutMillis;
        while (true) {
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0) {
                break;
            }
            serverSocket.setSoTimeout((int) remaining);
            try {
                Socket worker = serverSocket.accept();
                workers.add(worker);
            } catch (SocketTimeoutException e) {
                break;
            }
        }
        return workers;
    }

    /**
     * Sends an array of integers to a worker and handles its boolean response or errors.
     */
    public void sendIntArrayAndHandleAnswer(Socket worker,
                             List<Integer> data,
                             Consumer<Boolean> onAnswer,
                             Consumer<Throwable> onThrowable) throws IOException {
        SocketUtils.sendIntArray(worker, data);
        SocketUtils.receiveAndHandleBoolean(worker, onAnswer, onThrowable);
    }

    @Override
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
