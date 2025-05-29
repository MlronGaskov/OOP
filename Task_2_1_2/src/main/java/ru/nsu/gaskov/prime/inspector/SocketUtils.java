package ru.nsu.gaskov.prime.inspector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;

/** Utility methods for TCP and multicast socket communication. */
public class SocketUtils {

    /** Opens a TCP socket after receiving a host:port announcement via multicast. */
    public static Socket openTcpSocketFromMulticast(
            MulticastSocket multicastSocket
    ) throws IOException {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        multicastSocket.receive(packet);

        String msg = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
        System.out.println(msg);
        String[] parts = msg.trim().split(":");
        if (parts.length != 2) {
            throw new IOException(
                    "Invalid multicast message format, expected host:port but got: " + msg
            );
        }

        String host = parts[0];
        int port = Integer.parseInt(parts[1]);
        return new Socket(host, port);
    }

    /** Sends a boolean value over the given socket. */
    public static void sendBoolean(Socket socket, boolean value) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeBoolean(value);
        dos.flush();
    }

    /** Receives a list of integers from the given socket. */
    public static List<Integer> receiveIntList(Socket socket) throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int count = dis.readInt();
        List<Integer> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(dis.readInt());
        }
        return list;
    }

    /** Sends a discovery message to workers via multicast containing host and TCP port. */
    public static void sendMulticastMessage(
            String multicastAddress, int multicastPort,
            String interfaceName, String host, int tcpPort
    ) throws IOException {
        try (MulticastSocket socket = new MulticastSocket()) {
            socket.setNetworkInterface(NetworkInterface.getByName(interfaceName));
            String message = host + ":" + tcpPort;
            byte[] buf = message.getBytes(StandardCharsets.UTF_8);
            InetAddress group = InetAddress.getByName(multicastAddress);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, multicastPort);
            socket.send(packet);
        }
    }

    /** Sends an array of integers to the given socket. */
    public static void sendIntArray(Socket socket, List<Integer> data) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(data.size());
        for (int v : data) {
            dos.writeInt(v);
        }
        dos.flush();
    }

    /** Asynchronously receives a boolean from the socket and handles result or errors. */
    public static void receiveAndHandleBoolean(Socket socket,
                                               Consumer<Boolean> onAnswer,
                                               Consumer<Throwable> onThrowable) {
        CompletableFuture
                .supplyAsync(() -> {
                    try {
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        return dis.readBoolean();
                    } catch (Exception e) {
                        throw new CompletionException(e);
                    }
                })
                .thenAccept(onAnswer)
                .exceptionally(ex -> {
                    onThrowable.accept(ex);
                    return null;
                });
    }

}
