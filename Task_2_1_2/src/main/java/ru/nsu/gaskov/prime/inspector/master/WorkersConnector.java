package ru.nsu.gaskov.prime.inspector.master;

import ru.nsu.gaskov.prime.inspector.SocketUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WorkersConnector implements AutoCloseable {

    private final String multicastAddress;
    private final int multicastPort;
    private final String host;
    private final int listenPort;
    private final ServerSocket serverSocket;

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

    public List<Socket> findWorkers(int timeoutMillis) throws IOException {
        SocketUtils.sendMulticastMessage(multicastAddress, multicastPort, host, listenPort);

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

    public void sendIntArrayAndHandleAnswer(Socket worker,
                             List<Integer> data,
                             Consumer<Boolean> onAnswer,
                             Consumer<Exception> onException) throws IOException {
        SocketUtils.sendIntArray(worker, data);
        SocketUtils.receiveAndHandleBoolean(worker, onAnswer, onException);
    }

    @Override
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException ignored) {}
    }
}
