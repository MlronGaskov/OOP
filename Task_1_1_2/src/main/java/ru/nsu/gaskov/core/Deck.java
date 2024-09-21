package ru.nsu.gaskov.core;

public interface Deck {
    Card getOne();
    void collect();
    int getCardsInDeckCount();
}
