package ru.nsu.gaskov.input;

public class StandardDecksNumberCondition implements InputCondition{
    @Override
    public boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        try {
            int number = Integer.parseInt(input);
            return (number > 0 && number <= 8);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
