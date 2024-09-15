package ru.nsu.gaskov.deck;


import ru.nsu.gaskov.card.Card;
import ru.nsu.gaskov.card.Rank;
import ru.nsu.gaskov.card.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDeck implements Deck {
    protected List<Card> cards;
    protected int index;

    public Card getOne() {
        return cards.get(index++);
    }

    public void collect() {
        index = 0;
    }

    protected void init(List<Card> cards) {
        this.cards = cards;
        this.index = 0;
    }

    protected static List<Card> getStandardDeckCards() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        return cards;
    }

    protected void shuffle() {
        Collections.shuffle(cards);
    }

    public int getCardsInDeckCount() {
        return cards.size() - index;
    }
}
