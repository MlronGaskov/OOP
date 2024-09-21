package ru.nsu.gaskov.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class InputValidatorTest {

    private Scanner scanner;
    private Predicate<String> condition;

    @Test
    void testGetValidInputSuccessful() {
        String input = "validInput";
        String simulatedInput = input + "\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        condition = str -> !str.isEmpty();
        String result = InputValidator.getValidInput(scanner,
            condition,
            "Enter something:",
            "Invalid input, try again.");
        assertEquals(input, result);
    }

    @Test
    void testGetValidInputExceedsAttempts() {
        String simulatedInput = "a\na\na\na\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        condition = str -> !Objects.equals(str, "a");
        String result = InputValidator.getValidInput(scanner,
            condition,
            "Enter something:",
            "Invalid input, try again.");
        assertNull(result);
    }

    @Test
    void testGetValidInputMultipleAttempts() {
        String simulatedInput = "\n\nvalidInput\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        condition = str -> !str.isEmpty();
        String result = InputValidator.getValidInput(scanner,
            condition,
            "Enter something:",
            "Invalid input, try again.");
        assertEquals("validInput", result);
    }
}
