package ru.nsu.gaskov.ui;

import java.util.Arrays;

public class AnswerCondition implements InputCondition{
    @Override
    public boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        String[] validAnswers = new String[] {"YES", "Y", "NO", "N"};
        return Arrays.stream(validAnswers).anyMatch(e -> e.equals(input.toUpperCase()));
    }

    public boolean isAnswerYes(String input) {
        if (!isValid(input)) {
            return false;
        }
        String[] validAnswers = new String[] {"YES", "Y"};
        return Arrays.stream(validAnswers).anyMatch(e -> e.equals(input.toUpperCase()));
    }
}
