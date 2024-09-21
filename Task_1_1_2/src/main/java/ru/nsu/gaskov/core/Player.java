package ru.nsu.gaskov.core;

/**
 * The Player interface represents a generic player in the blackjack game.
 * It defines actions that a player can take during a game round.
 */
public interface Player {

    /**
     * Decides whether to make an insurance bet.
     *
     * @return true if the player opts for an insurance bet, false otherwise
     */
    default boolean insurance() {
        return false;
    }

    /**
     * Decides whether to split the current hand into two separate hands.
     *
     * @return true if the player chooses to split their hand, false otherwise
     */
    default boolean split() {
        return false;
    }

    /**
     * Decides whether to double the current bet.
     *
     * @return true if the player opts to double their bet, false otherwise
     */
    default boolean doubleBet() {
        return false;
    }

    /**
     * Decides whether to take another card (hit) from the dealer.
     *
     * @return true if the player chooses to hit, false otherwise
     */
    default boolean hit() {
        return false;
    }

    /**
     * Sets the initial starting bet for the player.
     *
     * @return the starting bet amount, default is 50
     */
    default int makeStartingBet() {
        return 50;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return the name of the player, default is "Player"
     */
    default String getName() {
        return "Player";
    }

    /**
     * Sets the current round for the player.
     *
     * @param round the round object representing the current round
     */
    default void setRound(Round round) {
        // Default implementation does nothing
    }
}
