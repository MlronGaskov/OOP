package ru.nsu.gaskov.core;

import java.util.Collection;

public class ValueCalculator {
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

    public static int calculate(Collection<Card> cards) {
        int value = cards.stream().mapToInt(ValueCalculator::getNotBustCardValue).sum();
        int aceCount = (int) cards.stream().filter(e -> e.rank() == Rank.ACE).count();
        while (aceCount > 0 && value > 21) {
            value -= 10;
            aceCount--;
        }
        return value;
    }
}
