package ru.nsu.gaskov;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

/**
 * PrimeInspectors tests.
 */
class InspectorsTest {
    private static final List<Integer> PRIMES = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
    private static final List<Integer> MIXED = List.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
    private static final List<Integer> NON_PRIMES = List.of(4, 6, 8, 9, 10, 12, 14, 15, 16, 18);

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
}
