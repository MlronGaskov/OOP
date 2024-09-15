package ru.nsu.gaskov.input;

import java.util.Scanner;

public class InputValidator {
    public static String getValidInput(
        Scanner scanner,
        InputCondition condition,
        String promptMessage,
        String errorMessage
    ) {
        int triesCount = 0;
        String input = "";
        while (triesCount < 3) {
            System.out.println(promptMessage);
            do {
                input = scanner.nextLine();
            } while (input.isEmpty());
            if (condition.isValid(input)) {
                return input;
            }
            System.out.println(errorMessage);
            triesCount += 1;
        }
        return null;
    }
}
