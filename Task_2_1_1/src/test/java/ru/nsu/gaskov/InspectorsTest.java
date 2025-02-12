package ru.nsu.gaskov;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InspectorsTest {
    private static final List<Integer> PRIMES = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
    private static final List<Integer> MIXED = List.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
    private static final List<Integer> NON_PRIMES = List.of(4, 6, 8, 9, 10, 12, 14, 15, 16, 18);

    private static final int NUM_THREADS = 4;

    static Stream<PrimeInspector> inspectors() {
        return Stream.of(
            new SequentialInspector(),
            new ParallelThreadsInspector(NUM_THREADS),
            new ParallelStreamInspector()
        );
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testOnlyPrimes(PrimeInspector inspector) {
        assertFalse(inspector.hasNonPrime(PRIMES), inspector.getClass().getSimpleName() + " failed on PRIMES");
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testMixedNumbers(PrimeInspector inspector) {
        assertTrue(inspector.hasNonPrime(MIXED), inspector.getClass().getSimpleName() + " failed on MIXED");
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testOnlyNonPrimes(PrimeInspector inspector) {
        assertTrue(inspector.hasNonPrime(NON_PRIMES), inspector.getClass().getSimpleName() + " failed on NON_PRIMES");
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testEmptyList(PrimeInspector inspector) {
        assertFalse(inspector.hasNonPrime(List.of()), inspector.getClass().getSimpleName() + " failed on EMPTY LIST");
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testSinglePrimeNumber(PrimeInspector inspector) {
        assertFalse(inspector.hasNonPrime(List.of(17)), inspector.getClass().getSimpleName() + " failed on SINGLE PRIME");
    }

    @ParameterizedTest
    @MethodSource("inspectors")
    void testSingleNonPrimeNumber(PrimeInspector inspector) {
        assertTrue(inspector.hasNonPrime(List.of(10)), inspector.getClass().getSimpleName() + " failed on SINGLE NON-PRIME");
    }
}
