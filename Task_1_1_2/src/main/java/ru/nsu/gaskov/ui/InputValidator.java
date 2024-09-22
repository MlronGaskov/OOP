package ru.nsu.gaskov.ui;

import java.util.Scanner;
import java.util.function.Predicate;

/**
 * The InputValidator class is responsible for validating user input.
 * It prompts the user for input, checks it against a specified condition,
 * and re-prompts the user if the input is invalid, up to a fixed number of attempts.
 */
public class InputValidator {

    /**
     * Prompts the user for input until valid input is provided or the maximum number
     * of attempts is reached.
     *
     * @param scanner       the Scanner instance used for reading user input
     * @param condition     a Predicate that defines the validity condition for the input
     * @param promptMessage the message displayed to the user to prompt for input
     * @param errorMessage  the message displayed when the input is invalid
     * @return the valid input provided by the user, or null if the maximum attempts are exceeded
     */
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
