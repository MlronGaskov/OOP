package ru.nsu.gaskov.core;

import java.util.Collection;

/**
 * The ValueCalculator class provides utility methods for calculating the
 * total value of a collection of cards in a card game.
 * This includes handling the special cases for the Ace card to avoid busting.
 */
public class ValueCalculator {

    /**
     * Retrieves the non-bust value of a given card based on its rank.
     *
     * @param card the Card to evaluate
     * @return the integer value of the card, where Aces are counted as 11
     *         and face cards (Jack, Queen, King) are counted as 10
     */
    private static int getNotBustCardValue(Card card) {
        return switch (card.rank()) {
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            case TEN, JACK, QUEEN, KING -> 10;
            case ACE -> 11;
        };
    }

    /**
     * Calculates the total value of a collection of cards, adjusting for
     * Aces to prevent busting above 21 points.
     *
     * @param cards a Collection of Card objects to evaluate
     * @return the calculated value of the cards, considering Ace adjustments
     */
    public static int calculate(Collection<Card> cards) {
        int value = cards.stream().mapToInt(ValueCalculator::getNotBustCardValue).sum();
        int aceCount = (int) cards.stream().filter(e -> e.rank() == Rank.ACE).count();

        while (aceCount > 0 && value > Constants.BLACKJACK_SCORE) {
            value -= 10;
            aceCount--;
        }

        return value;
    }
}
