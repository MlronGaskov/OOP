package ru.nsu.gaskov;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * PrimeInspectors tests.
 */
class InspectorsTest {
    private static final List<Integer> PRIMES =
        List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
    private static final List<Integer> MIXED =
        List.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
    private static final List<Integer> NON_PRIMES =
        List.of(4, 6, 8, 9, 10, 12, 14, 15, 16, 18);

    private static final int NUM_THREADS = 4;

    static Stream<PrimeInspector> inspectors() {
        return Stream.of(
            new SequentialInspector(),
            new ForkJoinPoolInspector(NUM_THREADS),
            new ForkJoinPoolInspector(1),
            new ExecutorsFrameworkInspector(NUM_THREADS),
            new ExecutorsFrameworkInspector(1),
            new CompletableFutureInspector(NUM_THREADS),
            new CompletableFutureInspector(1),
            new ParallelThreadsInspector(NUM_THREADS),
            new ParallelThreadsInspector(1),
            new ParallelStreamInspector()
        );
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testOnlyPrimes(PrimeInspector inspector) {
        assertFalse(inspector.hasNonPrime(PRIMES));
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testMixedNumbers(PrimeInspector inspector) {
        assertTrue(inspector.hasNonPrime(MIXED));
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testOnlyNonPrimes(PrimeInspector inspector) {
        assertTrue(inspector.hasNonPrime(NON_PRIMES));
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testEmptyList(PrimeInspector inspector) {
        assertFalse(inspector.hasNonPrime(List.of()));
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testSinglePrimeNumber(PrimeInspector inspector) {
        assertFalse(inspector.hasNonPrime(List.of(17)));
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testSingleNonPrimeNumber(PrimeInspector inspector) {
        assertTrue(inspector.hasNonPrime(List.of(10)));
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testLargeListOfPrimes(PrimeInspector inspector) {
        List<Integer> largePrimes = Collections.nCopies(10000, 2);
        assertFalse(inspector.hasNonPrime(largePrimes));
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testVeryLargeListOfPrimes(PrimeInspector inspector) {
        List<Integer> veryLargePrimes = Collections.nCopies(100000, 2);
        assertFalse(inspector.hasNonPrime(veryLargePrimes));
    }

    @Test
    void testPerformanceLargeListDifferentThreadCounts() {
        List<Integer> largePrimes = Collections.nCopies(10_000_000, 3571);

        PrimeInspector sequential = new SequentialInspector();
        long start = System.nanoTime();
        boolean result = sequential.hasNonPrime(largePrimes);
        long duration = System.nanoTime() - start;
        System.out.println("No1 (Sequential): " + (duration / 1_000_000.0) + " ms, result: " + result);

        int[] threadCounts = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024};
        for (int threads : threadCounts) {
            PrimeInspector parallel = new ExecutorsFrameworkInspector(threads);
            start = System.nanoTime();
            result = parallel.hasNonPrime(largePrimes);
            duration = System.nanoTime() - start;
            System.out.println("No2 (Parallel, threads=" + threads + "): " + (duration / 1_000_000.0) + " ms, result: " + result);
        }

        PrimeInspector parallelStream = new ParallelStreamInspector();
        start = System.nanoTime();
        result = parallelStream.hasNonPrime(largePrimes);
        duration = System.nanoTime() - start;
        System.out.println("No3 (ParallelStream): " + (duration / 1_000_000.0) + " ms, result: " + result);
    }
}
