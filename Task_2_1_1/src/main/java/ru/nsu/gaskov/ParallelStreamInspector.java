package ru.nsu.gaskov;

import java.util.List;

/**
 * Parallel implementation of PrimeInspector using Java Streams.
 */
public class ParallelStreamInspector implements PrimeInspector {
    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        return numbers.parallelStream().anyMatch(PrimeChecker::isNotPrime);
    }
}
