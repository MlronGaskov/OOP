package ru.nsu.gaskov;

import java.util.List;

/**
 * Sequential implementation of PrimeInspector.
 */
public class SequentialInspector implements PrimeInspector {
    @Override
    public boolean hasNonPrime(List<Integer> numbers) {
        for (int num : numbers) {
            if (PrimeChecker.isNotPrime(num)) {
                return true;
            }
        }
        return false;
    }
}
