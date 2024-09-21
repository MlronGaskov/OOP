package ru.nsu.gaskov.ui;

import java.util.Scanner;
import java.util.function.Predicate;

public class InputValidator {
    public static String getValidInput(
        Scanner scanner,
        Predicate<String> condition,
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
            if (condition.test(input)) {
                return input;
            }
            System.out.println(errorMessage);
            triesCount += 1;
        }
        return null;
    }
}
