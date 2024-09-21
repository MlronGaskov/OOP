package ru.nsu.gaskov.ui;

public class BetCondition implements InputCondition {
    @Override
    public boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        try {
            int number = Integer.parseInt(input);
            return (number > 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
