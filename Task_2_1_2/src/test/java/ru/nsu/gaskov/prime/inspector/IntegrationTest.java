package ru.nsu.gaskov.prime.inspector;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.gaskov.prime.inspector.master.Master;
import ru.nsu.gaskov.prime.inspector.worker.Worker;

/**
 * Master Worker IntegrationTest.
 */
class IntegrationTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private PrintStream originalErr;

    @BeforeEach
    void setUpStreams() {
        originalIn = System.in;
        originalOut = System.out;
        originalErr = System.err;
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private String runIntegration(String inputNumbers) throws Exception {
        ByteArrayOutputStream masterOut = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream(inputNumbers.getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(masterOut));
        System.setErr(new PrintStream(new ByteArrayOutputStream()));

        String addr = "230.0.0.1";
        int port = 4446;
        String ifName = NetworkInterface
                .getByInetAddress(InetAddress.getLoopbackAddress())
                .getName();

        int listenPort;
        try (ServerSocket ss = new ServerSocket(0)) {
            listenPort = ss.getLocalPort();
        }

        Thread workerThread = new Thread(() ->
                Worker.main(new String[]{ addr, String.valueOf(port), ifName })
        );
        workerThread.setDaemon(true);
        workerThread.start();

        Thread.sleep(500);

        Master.main(new String[]{
            addr,
            String.valueOf(port),
            ifName,
            "127.0.0.1",
            String.valueOf(listenPort), "2000"});

        return masterOut.toString(StandardCharsets.UTF_8);
    }

    @Test
    void testCompositeDetected() throws Exception {
        String input = "5 2 3 4 5 7";
        String output = runIntegration(input);
        assertTrue(output.trim().endsWith("true"),
                () -> "Expected 'true' (composite present), got:\n" + output);
    }

    @Test
    void testAllPrimes() throws Exception {
        String input = "5 2 3 5 7 11";
        String output = runIntegration(input);
        assertTrue(output.trim().endsWith("false"),
                () -> "Expected 'false' (all primes), got:\n" + output);
    }

    @Test
    void testAllComposite() throws Exception {
        String input = "6 1000 10000 744 595485 7323234 434343112";
        String output = runIntegration(input);
        assertTrue(output.trim().endsWith("true"),
                () -> "Expected 'true' (all composite), got:\n" + output);
    }
}
