package ru.nsu.gaskov.deck;

import ru.nsu.gaskov.card.Card;

public interface Deck {
    Card getOne();
    void collect();
    int getCardsInDeckCount();
}
