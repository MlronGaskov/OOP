package ru.nsu.gaskov.core;

/**
 * The SmallDeck class represents a specialized version of a deck of cards.
 * It extends the AbstractDeck class, initializing a standard set of cards
 * and providing functionality to shuffle and reset the deck.
 */
public class SmallDeck extends AbstractDeck {

    /**
     * Constructs a SmallDeck object, initializes it with a standard set of cards,
     * and shuffles the deck for ready use.
     */
    public SmallDeck() {
        init(getStandardDeckCards());
        shuffle();
    }

    /**
     * Resets the deck by collecting all cards back into the deck and reshuffling.
     */
    public void collect() {
        index = 0;
        shuffle();
    }
}
