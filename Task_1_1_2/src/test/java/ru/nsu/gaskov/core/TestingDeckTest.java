package ru.nsu.gaskov.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class TestingDeckTest {

    @Test
    void testValidDeckConstruction() {
        String[] cardCodes = {"test", "2 H", "3 D", "A C", "10 S"};
        TestingDeck deck = new TestingDeck(cardCodes);
        assertEquals(4, deck.getCardsInDeckCount());
    }

    @Test
    void testInvalidRank() {
        String[] cardCodes = {"test", "Z H"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TestingDeck(cardCodes);
        });
        assertEquals("Invalid rank", exception.getMessage());
    }

    @Test
    void testInvalidSuit() {
        String[] cardCodes = {"test", "2 X"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TestingDeck(cardCodes);
        });
        assertEquals("Invalid suit", exception.getMessage());
    }

    @Test
    void testInvalidFormat() {
        String[] cardCodes = {"test", "2H"};  // No space between rank and suit
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TestingDeck(cardCodes);
        });
        assertEquals("Invalid card format.", exception.getMessage());
    }
}