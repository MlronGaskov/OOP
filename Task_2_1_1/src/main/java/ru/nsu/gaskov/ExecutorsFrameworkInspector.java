package ru.nsu.gaskov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Implementation of PrimeInspector using ExecutorService and Futures.
 */
public class ExecutorsFrameworkInspector implements PrimeInspector {
    private final int numThreads;

    public ExecutorsFrameworkInspector(int numThreads) {
        this.numThreads = numThreads;
    }

    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        List<Future<Boolean>> futures;
        try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
            futures = new ArrayList<>();
            int chunkSize = (int) Math.ceil(numbers.size() / (double) numThreads);

            for (int i = 0; i < numThreads; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, numbers.size());
                if (start >= end) {
                    break;
                }

                futures.add(executor.submit(() -> {
                    for (int j = start; j < end; j++) {
                        if (PrimeChecker.isNotPrime(numbers.get(j))) {
                            return true;
                        }
                    }
                    return false;
                }));
            }
        }
        try {
            for (Future<Boolean> future : futures) {
                if (future.get()) {
                    return true;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Execution interrupted", e);
        }

        return false;
    }
}
