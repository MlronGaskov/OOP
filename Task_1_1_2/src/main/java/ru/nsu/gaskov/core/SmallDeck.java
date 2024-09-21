package ru.nsu.gaskov.core;

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
