package ru.nsu.gaskov.card;

import java.util.Objects;

public record Card(Suit suit, Rank rank) {
    @Override
    public String toString() {
        return capitalize(rank.name()) + " of " + capitalize(suit.name());
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != this.getClass()) {
            return false;
        }
        Card card = (Card) object;
        return Objects.equals(card.suit.name(), this.suit.name()) &&
            Objects.equals(card.rank.name(), this.rank.name());
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}