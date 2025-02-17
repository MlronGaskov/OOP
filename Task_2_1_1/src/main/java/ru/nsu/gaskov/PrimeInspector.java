package ru.nsu.gaskov;

import java.util.List;

/**
 * Interface for checking if a list contains non-prime numbers.
 */
public interface PrimeInspector {
    boolean hasNonPrime(List<Integer> numbers);
}
