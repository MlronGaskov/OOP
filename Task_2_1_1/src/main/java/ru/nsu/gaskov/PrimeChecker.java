package ru.nsu.gaskov;

public class PrimeChecker {
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
