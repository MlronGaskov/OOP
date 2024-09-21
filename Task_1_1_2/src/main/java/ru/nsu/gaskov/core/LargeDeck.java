package ru.nsu.gaskov.core;

import java.util.ArrayList;
import java.util.List;

public class LargeDeck extends AbstractDeck {
    public LargeDeck(int decksCount) {
        List<Card> list = new ArrayList<>();
        for (int i = 0; i < decksCount; ++i) {
            list.addAll(getStandardDeckCards());
        }
        init(list);
        shuffle();
    }

    public void collect() {
        if (index * 2 > cards.size()) {
            index = 0;
            shuffle();
        }
    }
}
