package ru.nsu.gaskov.prime.inspector.worker;

import ru.nsu.gaskov.prime.inspector.SocketUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

/** Manages discovery and communication with the master node. */
public class MasterConnector implements AutoCloseable {

    private Socket masterSocket = null;
    private final MulticastSocket multicastSocket;

    /** Creates a connector listening for master announcements via multicast. */
    public MasterConnector(String multicastAddress, int multicastPort, NetworkInterface netIf) throws IOException {
        multicastSocket = new MulticastSocket(multicastPort);
        InetAddress group = InetAddress.getByName(multicastAddress);
        SocketAddress groupAddr = new InetSocketAddress(group, multicastPort);
        multicastSocket.joinGroup(groupAddr, netIf);
    }

    /** Listens for a master connection announcement and establishes a TCP socket. */
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

    /** Sends a boolean response to the master. */
    public void sendBoolean(boolean response) throws IOException {
        if (masterSocket == null) {
            throw new IllegalStateException("No master connected");
        }
        SocketUtils.sendBoolean(masterSocket, response);
    }

    /** Receives a list of integers from the master. */
    public List<Integer> receiveIntList() throws IOException {
        if (masterSocket == null) {
            throw new IllegalStateException("No master connected");
        }
        return SocketUtils.receiveIntList(masterSocket);
    }

    /** Closes the TCP connection to the master if open. */
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
