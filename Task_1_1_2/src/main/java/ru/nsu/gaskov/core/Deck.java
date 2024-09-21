package ru.nsu.gaskov.core;

/**
 * An interface representing a deck of cards.
 * This interface provides methods for dealing cards
 * and managing the deck's state.
 */
public interface Deck {

    /**
     * Retrieves the next card from the deck.
     *
     * @return the next card in the deck
     */
    Card getOne();

    /**
     * Resets the deck, allowing cards to be dealt again.
     * This method prepares the deck for a new game or round
     * by collecting all dealt cards back to the beginning of the deck.
     */
    void collect();

    /**
     * Returns the count of remaining cards in the deck.
     *
     * @return the number of cards left in the deck
     */
    int getCardsInDeckCount();
}
