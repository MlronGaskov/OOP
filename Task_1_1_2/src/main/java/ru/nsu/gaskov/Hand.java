package ru.nsu.gaskov;

import ru.nsu.gaskov.card.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hand {
    private int bet;
    private boolean isInsured = false;
    private boolean isDoubled = false;
    private final List<Card> cards1 = new ArrayList<>();
    private List<Card> cards2;
    private int currentCardsPile = 0;

    public boolean isSplat() {
        return cards2 != null;
    }

    public void setBet(int bet) {
        if (bet < 0) {
            throw new IllegalArgumentException("Bet could not be less than 0");
        }
        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }

    public void insure() {
        if (isInsured) {
            throw new IllegalStateException("Hand is already insured");
        }
        isInsured = true;
    }

    public void setDoubled() {
        isDoubled = true;
    }

    public boolean isInsured() {
        return isInsured;
    }

    public boolean isDoubled() {
        return isDoubled;
    }

    public List<Card> getCards1() {
        return cards1;
    }

    public List<Card> getCards2() {
        return cards2;
    }

    public int getCards1Value() {
        return ValueCalculator.calculate(cards1);
    }

    public int getCards2Value() {
        if (!isSplat()) {
            throw new IllegalStateException("Hand is not splat.");
        }
        return ValueCalculator.calculate(cards2);
    }

    public int getCurrentPileValue() {
        if (currentCardsPile == 0)
            return getCards1Value();
        return getCards2Value();
    }

    public boolean isHandSplittable() {
        return cards2 == null &&
            cards1.size() == 2 &&
            cards1.get(0).rank() == cards1.get(1).rank();
    }

    public void split(Card card1, Card card2) {
        if (!isHandSplittable()) {
            throw new IllegalStateException("Cannot split non-splittable hand.");
        }
        cards2 = new ArrayList<>();
        cards2.add(cards1.removeLast());
        cards1.add(Objects.requireNonNull(card1));
        cards2.add(Objects.requireNonNull(card2));
    }

    public void take(Card card) {
        if (currentCardsPile == 0) {
            if (getCards1Value() >= 21) {
                throw new IllegalStateException("Hand is already full.");
            }
            cards1.add(Objects.requireNonNull(card));
        } else {
            if (getCards2Value() >= 21) {
                throw new IllegalStateException("Hand is already full.");
            }
            cards2.add(Objects.requireNonNull(card));
        }
    }

    public int getCurrentCardsPile() {
        return currentCardsPile;
    }

    public void changePile() {
        if (cards2 == null) {
            throw new IllegalStateException("Hand is not splat.");
        }
        if (currentCardsPile == 1) {
            throw new IllegalStateException("Cards pile is already changed");
        }
        currentCardsPile = 1;
    }
}