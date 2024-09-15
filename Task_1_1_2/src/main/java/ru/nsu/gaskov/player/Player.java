package ru.nsu.gaskov.player;

import ru.nsu.gaskov.Round;

public interface Player {
    default boolean insurance() {
        return false;
    }

    default boolean split() {
        return false;
    }

    default boolean doubleBet() {
        return false;
    }

    default boolean hit() {
        return false;
    }

    default int makeStartingBet() {
        return 50;
    }

    default String getName() {
        return "Player";
    }

    default void setRound(Round round) {
    }
}
