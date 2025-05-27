package ru.nsu.gaskov.prime.inspector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

/**
 * SocketUtils tests.
 */
class SocketUtilsTest {

    static class FakeMulticastSocket extends MulticastSocket {
        private final String message;
        FakeMulticastSocket(String message) throws IOException { super(); this.message = message; }
        @Override public void receive(DatagramPacket p) throws IOException {
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(data, 0, p.getData(), 0, data.length);
            p.setLength(data.length);
        }
    }

    static class FakeSocket extends Socket {
        private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        private InputStream in = new ByteArrayInputStream(new byte[0]);
        void setInput(byte[] data) { in = new ByteArrayInputStream(data); }
        @Override public InputStream getInputStream() { return in; }
        @Override public OutputStream getOutputStream() { return byteArrayOutputStream; }
        byte[] getOutputBytes() { return byteArrayOutputStream.toByteArray(); }
    }

    @Test
    void testSendAndReceivePrimitives() throws IOException {
        FakeSocket sock = new FakeSocket();
        SocketUtils.sendBoolean(sock, true);
        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(sock.getOutputBytes()))) {
            assertTrue(dis.readBoolean());
        }
        sock = new FakeSocket();
        List<Integer> data = Arrays.asList(1, 2, 3);
        SocketUtils.sendIntArray(sock, data);
        DataInputStream dis2 = new DataInputStream(new ByteArrayInputStream(sock.getOutputBytes()));
        assertEquals(3, dis2.readInt());
        assertEquals(1, dis2.readInt());
        assertEquals(2, dis2.readInt());
        assertEquals(3, dis2.readInt());
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(tmp);
        dos.writeInt(2);
        dos.writeInt(42);
        dos.writeInt(99);
        sock.setInput(tmp.toByteArray());
        List<Integer> list = SocketUtils.receiveIntList(sock);
        assertEquals(Arrays.asList(42, 99), list);
    }

    @Test
    void testOpenTcpSocketFromMulticast_Success() throws Exception {
        try (ServerSocket server = new ServerSocket(0)) {
            int port = server.getLocalPort();
            FakeMulticastSocket fake = new FakeMulticastSocket("127.0.0.1:" + port);
            ExecutorService exec = Executors.newSingleThreadExecutor();
            Future<Socket> clientF = exec.submit(() -> SocketUtils.openTcpSocketFromMulticast(fake));
            try (Socket serverSide = server.accept()) {
                Socket client = clientF.get(1, TimeUnit.SECONDS);
                assertTrue(client.isConnected());
                assertTrue(serverSide.isConnected());
                client.close();
            } finally { exec.shutdownNow(); }
        }
    }

    @Test
    void testReceiveAndHandleBoolean_Success() throws InterruptedException {
        FakeSocket sock = new FakeSocket();
        sock.setInput(new byte[]{1});
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean result = new AtomicBoolean(false);

        SocketUtils.receiveAndHandleBoolean(sock,
                b -> { result.set(b); latch.countDown(); },
                t -> fail("Не ожидали ошибки"));

        assertTrue(latch.await(1, TimeUnit.SECONDS));
        assertTrue(result.get());
    }
}
