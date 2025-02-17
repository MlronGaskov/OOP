package ru.nsu.gaskov;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * PrimeChecker tests.
 */
class PrimeCheckerTest {
    private static final List<Integer> PRIMES =
        List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 3559, 3571);
    private static final List<Integer> NON_PRIMES =
        List.of(0, 1, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 100000000);

    @Test
    void testPrimeNumbers() {
        for (int prime : PRIMES) {
            assertFalse(PrimeChecker.isNotPrime(prime));
        }
    }

    @Test
    void testNonPrimeNumbers() {
        for (int nonPrime : NON_PRIMES) {
            assertTrue(PrimeChecker.isNotPrime(nonPrime));
        }
    }
}