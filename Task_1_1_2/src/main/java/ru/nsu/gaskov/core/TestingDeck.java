package ru.nsu.gaskov.core;

import java.util.Arrays;

/**
 * The TestingDeck class allows the creation of a deck of cards
 * from a provided array of card codes. It initializes the deck with
 * specified cards and shuffles them for immediate use.
 */
public class TestingDeck extends AbstractDeck {

    /**
     * Constructs a TestingDeck from an array of card codes.
     * Each card code should be in the format "rank suit".
     *
     * @param cardsCodes an array of strings representing the cards to be added to the deck
     *                   Card codes must be in the format "rank suit", where suit is
     *                   represented by its first letter (H, D, S, C).
     */
    public TestingDeck(String[] cardsCodes) {
        init(Arrays.stream(cardsCodes).skip(1).map(this::getCard).toList());
    }

    /**
     * Converts a card code into a Card object.
     *
     * @param cardCode the string representation of the card (format: "rank suit")
     * @return a Card object corresponding to the provided code
     * @throws IllegalArgumentException if the card code is invalid
     */
    private Card getCard(String cardCode) {
        String[] parts = cardCode.split(" ");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid card format.");
        }

        return new Card(
            switch (parts[1]) {
                case "H" -> Suit.HEARTS;
                case "D" -> Suit.DIAMONDS;
                case "S" -> Suit.SPADES;
                case "C" -> Suit.CLUBS;
                default -> throw new IllegalArgumentException("Invalid suit");
            },
            switch (parts[0]) {
                case "2" -> Rank.TWO;
                case "3" -> Rank.THREE;
                case "4" -> Rank.FOUR;
                case "5" -> Rank.FIVE;
                case "6" -> Rank.SIX;
                case "7" -> Rank.SEVEN;
                case "8" -> Rank.EIGHT;
                case "9" -> Rank.NINE;
                case "10" -> Rank.TEN;
                case "J" -> Rank.JACK;
                case "Q" -> Rank.QUEEN;
                case "K" -> Rank.KING;
                case "A" -> Rank.ACE;
                default -> throw new IllegalArgumentException("Invalid rank");
            }
        );
    }
}
