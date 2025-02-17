package ru.nsu.gaskov;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Implementation of PrimeInspector using CompletableFuture.
 */
public class CompletableFutureInspector implements PrimeInspector {
    private final int numThreads;

    public CompletableFutureInspector(int numThreads) {
        this.numThreads = numThreads;
    }

    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            return false;
        }
        int chunkSize = (int) Math.ceil(numbers.size() / (double) numThreads);
        boolean result;
        try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
            List<CompletableFuture<Boolean>> futures = numbers.stream()
                .collect(Collectors.groupingBy(num -> numbers.indexOf(num) / chunkSize))
                .values()
                .stream()
                .map(chunk -> CompletableFuture.supplyAsync(() ->
                    chunk.stream().anyMatch(PrimeChecker::isNotPrime), executor))
                .toList();

            CompletableFuture<Boolean> combined = CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                    .anyMatch(CompletableFuture::join));

            result = combined.join();
        }
        return result;
    }
}
