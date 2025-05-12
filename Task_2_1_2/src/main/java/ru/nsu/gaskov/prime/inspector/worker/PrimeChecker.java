package ru.nsu.gaskov.prime.inspector.worker;

/**
 * Utility class for checking if a number is not prime.
 */
public class PrimeChecker {
    /**
     * Checks if a number is not prime.
     */
    public static boolean isNotPrime(int n) {
        if (n < 2) {
            return true;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return true;
            }
        }
        return false;
    }
}