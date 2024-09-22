package ru.nsu.gaskov.core;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class LargeDeckTest {
    @Test
    public void largeDeckGetOneTest() {
        int decksCount = 4;
        Deck deck = new LargeDeck(decksCount);
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < Suit.values().length * Rank.values().length * decksCount; ++i) {
            Card card = deck.getOne();
            if (cards.stream().filter(e -> e == card).count() > decksCount) {
                fail();
            }
            cards.add(card);
        }
    }

    @Test
    public void largeDeckCollectTest() {
        int decksCount = 8;
        Deck deck = new LargeDeck(decksCount);
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 150; ++i) {
            Card card = deck.getOne();
            if (cards.stream().filter(e -> e == card).count() > decksCount) {
                fail();
            }
            cards.add(card);
        }
        deck.collect();
        cards = new ArrayList<>();
        for (int i = 0; i < Suit.values().length * Rank.values().length; ++i) {
            Card card = deck.getOne();
            if (cards.stream().filter(e -> e == card).count() > decksCount) {
                fail();
            }
            cards.add(card);
        }
    }
}