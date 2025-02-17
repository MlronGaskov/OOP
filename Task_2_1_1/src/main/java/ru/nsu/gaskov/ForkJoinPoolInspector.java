package ru.nsu.gaskov;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Implementation of PrimeInspector using ForkJoinPool.
 */
public class ForkJoinPoolInspector implements PrimeInspector {
    private final int numThreads;

    public ForkJoinPoolInspector(int numThreads) {
        this.numThreads = numThreads;
    }

    private static class PrimeCheckTask extends RecursiveTask<Boolean> {
        private static final int THRESHOLD = 100;
        private final List<Integer> numbers;
        private final int start;
        private final int end;

        public PrimeCheckTask(List<Integer> numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Boolean compute() {
            if (end - start <= THRESHOLD) {
                return numbers.subList(start, end).stream().anyMatch(PrimeChecker::isNotPrime);
            }

            int mid = (start + end) / 2;
            PrimeCheckTask leftTask = new PrimeCheckTask(numbers, start, mid);
            PrimeCheckTask rightTask = new PrimeCheckTask(numbers, mid, end);

            leftTask.fork();
            boolean rightResult = rightTask.compute();
            boolean leftResult = leftTask.join();

            return leftResult || rightResult;
        }
    }

    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        try (ForkJoinPool forkJoinPool = new ForkJoinPool(numThreads)) {
            return forkJoinPool.invoke(new PrimeCheckTask(numbers, 0, numbers.size()));
        }
    }
}
