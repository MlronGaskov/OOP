package ru.nsu.gaskov.deck;

import org.junit.jupiter.api.Test;
import ru.nsu.gaskov.card.Card;
import ru.nsu.gaskov.card.Rank;
import ru.nsu.gaskov.card.Suit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SmallDeckTest {
    @Test
    public void smallDeckGetOneTest() {
        Deck deck = new SmallDeck();
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < Suit.values().length * Rank.values().length; ++i) {
            Card card = deck.getOne();
            if (cards.contains(card)) {
                fail();
            }
            cards.add(card);
        }
    }

    @Test
    public void smallDeckCollectTest() {
        Deck deck = new SmallDeck();
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 15; ++i) {
            Card card = deck.getOne();
            if (cards.contains(card)) {
                fail();
            }
            cards.add(card);
        }
        deck.collect();
        cards = new ArrayList<>();
        for (int i = 0; i < Suit.values().length * Rank.values().length; ++i) {
            Card card = deck.getOne();
            if (cards.contains(card)) {
                fail();
            }
            cards.add(card);
        }
    }
}