package ru.nsu.gaskov.prime.inspector.master;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class DistributedPrimeInspector implements PrimeInspector, AutoCloseable {

    private final WorkersConnector connector;
    private final int timeoutMillis;

    public DistributedPrimeInspector(String multicastAddress,
                                     int multicastPort,
                                     String host,
                                     int listenPort,
                                     int timeoutMillis) throws IOException {
        this.connector = new WorkersConnector(multicastAddress, multicastPort, host, listenPort);
        this.timeoutMillis = timeoutMillis;
    }

    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        while (!numbers.isEmpty()) {
            CheckResult checkResult = hasNonPrimeWithRest(numbers);
            if (checkResult.hasNonPrime()) {
                return true;
            }
            numbers = checkResult.untestedNumbers();
        }
        return false;
    }

    @Override
    public void close() {
        connector.close();
    }

    public CheckResult hasNonPrimeWithRest(List<Integer> numbers) {
        List<Socket> workers;
        try {
            workers = null;
            while (workers == null || workers.isEmpty()) {
                workers = connector.findWorkers(timeoutMillis);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to find workers", e);
        }

        int numWorkers = workers.size();
        int total = numbers.size();
        int chunkSize = (total + numWorkers - 1) / numWorkers;

        CountDownLatch latch = new CountDownLatch(numWorkers);
        AtomicBoolean foundComposite = new AtomicBoolean(false);
        List<Integer> untested = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < numWorkers; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, total);
            List<Integer> sub = numbers.subList(start, end);
            Socket workerSocket = workers.get(i);

            try {
                connector.sendIntArrayAndHandleAnswer(
                        workerSocket,
                        sub,
                        answer -> {
                            if (answer) {
                                foundComposite.set(true);
                            }
                            latch.countDown();
                        },
                        ex -> {
                            untested.addAll(sub);
                            latch.countDown();
                        }
                );
            } catch (IOException e) {
                untested.addAll(sub);
                latch.countDown();
            }
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Socket s : workers) {
            try {
                s.close();
            } catch (IOException ignored) {}
        }

        return new CheckResult(foundComposite.get(), untested);
    }

    public record CheckResult(boolean hasNonPrime, List<Integer> untestedNumbers) {
    }
}
