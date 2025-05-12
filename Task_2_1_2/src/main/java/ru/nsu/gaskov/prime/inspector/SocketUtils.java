package ru.nsu.gaskov.prime.inspector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SocketUtils {

    public static Socket openTcpSocketFromMulticast(MulticastSocket multicastSocket) throws IOException {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        multicastSocket.receive(packet);

        String msg = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
        System.out.println(msg);
        String[] parts = msg.trim().split(":");
        if (parts.length != 2) {
            throw new IOException("Invalid multicast message format, expected host:port but got: " + msg);
        }

        String host = parts[0];
        int port = Integer.parseInt(parts[1]);
        return new Socket(host, port);
    }

    public static void sendBoolean(Socket socket, boolean value) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeBoolean(value);
        dos.flush();
    }

    public static List<Integer> receiveIntList(Socket socket) throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int count = dis.readInt();
        List<Integer> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(dis.readInt());
        }
        return list;
    }

    public static void sendMulticastMessage(String multicastAddress, int multicastPort,
                                            String host, int tcpPort) throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            String message = host + ":" + tcpPort;
            byte[] buf = message.getBytes(StandardCharsets.UTF_8);
            InetAddress group = InetAddress.getByName(multicastAddress);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, multicastPort);
            socket.send(packet);
        }
    }

    public static void sendIntArray(Socket socket, List<Integer> data) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(data.size());
        for (int v : data) {
            dos.writeInt(v);
        }
        dos.flush();
    }

    public static void receiveAndHandleBoolean(Socket socket,
                                               Consumer<Boolean> onAnswer,
                                               Consumer<Exception> onException) {
        new Thread(() -> {
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                boolean answer = dis.readBoolean();
                onAnswer.accept(answer);
            } catch (Exception e) {
                onException.accept(e);
            }
        }).start();
    }
}
