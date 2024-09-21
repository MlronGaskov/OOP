package ru.nsu.gaskov.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Hand class represents a player's hand in a blackjack game,
 * managing the cards, betting, and game rules related to the hand.
 */
public class Hand {
    private int bet;
    private boolean isInsured = false;
    private boolean isDoubled = false;
    private final List<Card> cards1 = new ArrayList<>();
    private List<Card> cards2;
    private int currentCardsPile = 0;

    /**
     * Checks if the hand is split into two piles.
     *
     * @return true if the hand is split, false otherwise
     */
    public boolean isSplit() {
        return cards2 != null;
    }

    /**
     * Sets the bet amount for the hand.
     *
     * @param bet the amount to set as the bet
     * @throws IllegalArgumentException if the bet is less than 0
     */
    public void setBet(int bet) {
        if (bet < 0) {
            throw new IllegalArgumentException("Bet could not be less than 0");
        }
        this.bet = bet;
    }

    /**
     * Retrieves the current bet amount.
     *
     * @return the current bet amount
     */
    public int getBet() {
        return bet;
    }

    /**
     * Insures the hand, marking it as insured.
     *
     * @throws IllegalStateException if the hand is already insured
     */
    public void insure() {
        if (isInsured) {
            throw new IllegalStateException("Hand is already insured");
        }
        isInsured = true;
    }

    /**
     * Marks the hand as doubled.
     */
    public void setDoubled() {
        isDoubled = true;
    }

    /**
     * Checks if the hand is insured.
     *
     * @return true if the hand is insured, false otherwise
     */
    public boolean isInsured() {
        return isInsured;
    }

    /**
     * Checks if the hand is doubled.
     *
     * @return true if the hand is doubled, false otherwise
     */
    public boolean isDoubled() {
        return isDoubled;
    }

    /**
     * Retrieves the primary cards in the hand.
     *
     * @return a list of primary {@link Card} objects
     */
    public List<Card> getCards1() {
        return cards1;
    }

    /**
     * Retrieves the secondary cards in the hand (if split).
     *
     * @return a list of secondary {@link Card} objects or null if not split
     */
    public List<Card> getCards2() {
        return cards2;
    }

    /**
     * Calculates and retrieves the total value of the primary cards.
     *
     * @return the total value of cards in cards1
     */
    public int getCards1Value() {
        return ValueCalculator.calculate(cards1);
    }

    /**
     * Calculates and retrieves the total value of the secondary cards.
     *
     * @return the total value of cards in cards2
     * @throws IllegalStateException if cards2 is null (hand is not split)
     */
    public int getCards2Value() {
        if (!isSplit()) {
            throw new IllegalStateException("Hand is not splat.");
        }
        return ValueCalculator.calculate(cards2);
    }

    /**
     * Gets the current total value of the active card pile in the hand.
     *
     * @return the value of the current cards pile
     */
    public int getCurrentPileValue() {
        if (currentCardsPile == 0)
            return getCards1Value();
        return getCards2Value();
    }

    /**
     * Checks if the hand can be split.
     *
     * @return true if the hand is splittable, false otherwise
     */
    public boolean isHandSplittable() {
        return cards2 == null &&
            cards1.size() == 2 &&
            cards1.get(0).rank() == cards1.get(1).rank();
    }

    /**
     * Splits the hand into two separate piles of cards.
     *
     * @param card1 the first card for the new pile
     * @param card2 the second card for the new pile
     * @throws IllegalStateException if the hand cannot be split
     */
    public void split(Card card1, Card card2) {
        if (!isHandSplittable()) {
            throw new IllegalStateException("Cannot split non-splittable hand.");
        }
        cards2 = new ArrayList<>();
        cards2.add(cards1.removeLast());
        cards1.add(Objects.requireNonNull(card1));
        cards2.add(Objects.requireNonNull(card2));
    }

    /**
     * Adds a card to the current pile of cards.
     *
     * @param card the card to add
     * @throws IllegalStateException if the maximum value of the hand is reached
     */
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

    /**
     * Gets the current cards pile indicator (0 or 1).
     *
     * @return the current cards pile (0 for cards1, 1 for cards2)
     */
    public int getCurrentCardsPile() {
        return currentCardsPile;
    }

    /**
     * Changes the active cards pile to the other pile (if split).
     *
     * @throws IllegalStateException if the hand is not split or if the pile is already changed
     */
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