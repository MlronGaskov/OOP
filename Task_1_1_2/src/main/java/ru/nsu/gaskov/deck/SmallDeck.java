package ru.nsu.gaskov.deck;

public class SmallDeck extends AbstractDeck {
    public SmallDeck() {
        init(getStandardDeckCards());
        shuffle();
    }

    public void collect() {
        index = 0;
        shuffle();
    }
}
