package ru.nsu.gaskov;

import java.util.List;

public class ParallelStreamInspector implements PrimeInspector {
    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        return numbers.parallelStream().anyMatch(PrimeChecker::isNotPrime);
    }
}
