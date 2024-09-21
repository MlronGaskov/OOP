package ru.nsu.gaskov.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An abstract class representing a deck of cards.
 * This class provides basic functionalities to manage a deck,
 * including shuffling and dealing cards.
 */
public abstract class AbstractDeck implements Deck {
    protected List<Card> cards;
    protected int index;

    /**
     * Retrieves the next card from the deck.
     *
     * @return the next card in the deck
     */
    @Override
    public Card getOne() {
        return cards.get(index++);
    }

    /**
     * Resets the index to the beginning of the deck,
     * preparing to collect cards back into the deck.
     */
    @Override
    public void collect() {
        index = 0;
    }

    /**
     * Initializes the deck with a specified list of cards.
     *
     * @param cards a List of Card objects to initialize the deck
     */
    protected void init(List<Card> cards) {
        this.cards = cards;
        this.index = 0;
    }

    /**
     * Creates a standard deck of cards (52 cards).
     *
     * @return a List of {@link Card} objects representing a standard deck
     */
    protected static List<Card> getStandardDeckCards() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        return cards;
    }

    /**
     * Shuffles the deck of cards into a random order.
     */
    protected void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Returns the count of remaining cards in the deck.
     *
     * @return the number of cards left to deal
     */
    @Override
    public int getCardsInDeckCount() {
        return cards.size() - index;
    }
}
