package ru.nsu.gaskov.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The LargeDeck class represents a deck of playing cards consisting
 * of multiple standard decks combined into one. It provides functionality
 * to initialize, shuffle, and collect cards from the deck.
 */
public class LargeDeck extends AbstractDeck {

    /**
     * Constructs a LargeDeck instance with a specified number of standard decks.
     *
     * @param decksCount the number of standard decks to include in the LargeDeck
     */
    public LargeDeck(int decksCount) {
        List<Card> list = new ArrayList<>();
        for (int i = 0; i < decksCount; ++i) {
            list.addAll(getStandardDeckCards());
        }
        init(list);
        shuffle();
    }

    /**
     * Collects the cards from the deck. If more than half of the cards have
     * been dealt, the index is reset and the deck is shuffled again.
     */
    public void collect() {
        if (index * 2 > cards.size()) {
            index = 0;
            shuffle();
        }
    }
}
