package ru.nsu.gaskov.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class HumanPlayerTest {

    private Scanner scanner;
    private HumanPlayer player;

    @Test
    void testInsuranceYes() {
        String simulatedInput = "test\nyes\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertTrue(player.insurance());
    }

    @Test
    void testInsuranceNo() {
        String simulatedInput = "test\nno\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertFalse(player.insurance());
    }

    @Test
    void testInsuranceInvalidInput() {
        String simulatedInput = "test\nmaybe\nno\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertFalse(player.insurance());
    }

    @Test
    void testSplitYes() {
        String simulatedInput = "test\nyes\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertTrue(player.split());
    }

    @Test
    void testSplitNo() {
        String simulatedInput = "test\nno\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertFalse(player.split());
    }

    @Test
    void testDoubleBetYes() {
        String simulatedInput = "test\nyes\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertTrue(player.doubleBet());
    }

    @Test
    void testHitYes() {
        String simulatedInput = "test\nyes\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertTrue(player.hit());
    }

    @Test
    void testMakeStartingBetValidInput() {
        String simulatedInput = "test\n100\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertEquals(100, player.makeStartingBet());
    }

    @Test
    void testMakeStartingBetInvalidInput() {
        String simulatedInput = "test\nabc\n200\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertEquals(200, player.makeStartingBet());
    }

    @Test
    void testMakeStartingBetWithNoInput() {
        String simulatedInput = "test\n-1\n-1\n-1";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(in);
        player = new HumanPlayer(scanner);
        assertEquals(100, player.makeStartingBet());
    }
}
