package ru.nsu.gaskov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Parallel implementation of PrimeInspector using threads.
 */
public class ParallelThreadsInspector implements PrimeInspector {
    private final int numThreads;

    public ParallelThreadsInspector(int numThreads) {
        this.numThreads = numThreads;
    }

    /**
     * Helper class to store a flag for detecting non-prime numbers.
     */
    private static class NonPrimeFlag {
        private final AtomicBoolean hasNonPrime = new AtomicBoolean(false);

        public boolean hasNonPrime() {
            return hasNonPrime.get();
        }

        public void setHasNonPrime() {
            hasNonPrime.set(true);
        }
    }

    /**
     * Thread for checking prime numbers in a segment of the list.
     */
    private static class PrimeCheckThread extends Thread {
        private final List<Integer> numbers;
        private final int start, end;
        private final NonPrimeFlag flag;

        public PrimeCheckThread(List<Integer> numbers, int start, int end, NonPrimeFlag flag) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
            this.flag = flag;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                if (flag.hasNonPrime()) {
                    return;
                }
                if (PrimeChecker.isNotPrime(numbers.get(i))) {
                    flag.setHasNonPrime();
                }
            }
        }
    }

    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        int chunkSize = (int) Math.ceil(numbers.size() / (double) numThreads);
        List<PrimeCheckThread> threads = new ArrayList<>();
        NonPrimeFlag flag = new NonPrimeFlag();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, numbers.size());
            if (start >= end) {
                break;
            }

            PrimeCheckThread thread = new PrimeCheckThread(numbers, start, end, flag);
            threads.add(thread);
            thread.start();
        }

        for (PrimeCheckThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted", e);
            }
        }

        return flag.hasNonPrime();
    }
}
