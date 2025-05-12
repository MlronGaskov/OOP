package ru.nsu.gaskov.prime.inspector.worker;

import ru.nsu.gaskov.prime.inspector.SocketUtils;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class MasterConnector implements AutoCloseable {

    private Socket masterSocket = null;
    private final MulticastSocket multicastSocket;

    public MasterConnector(String multicastAddress, int multicastPort, NetworkInterface netIf) throws IOException {
        multicastSocket = new MulticastSocket(multicastPort);
        InetAddress group = InetAddress.getByName(multicastAddress);
        SocketAddress groupAddr = new InetSocketAddress(group, multicastPort);
        multicastSocket.joinGroup(groupAddr, netIf);
    }

    public void findMaster() throws IOException {
        closeMasterSocket();
        Socket socket;
        while (true) {
            try {
                socket = SocketUtils.openTcpSocketFromMulticast(multicastSocket);
                break;
            } catch (IOException ignored) {}
        }
        masterSocket = socket;
    }

    public void sendBoolean(boolean response) throws IOException {
        if (masterSocket == null) {
            throw new IllegalStateException("No master connected");
        }
        SocketUtils.sendBoolean(masterSocket, response);
    }

    public List<Integer> receiveIntList() throws IOException {
        if (masterSocket == null) {
            throw new IllegalStateException("No master connected");
        }
        return SocketUtils.receiveIntList(masterSocket);
    }

    public void closeMasterSocket() {
        if (masterSocket == null) {
            return;
        }
        try {
            masterSocket.close();
            masterSocket = null;
        } catch (IOException ignored) {}
    }

    @Override
    public void close() {
        closeMasterSocket();
        multicastSocket.close();
    }
}
