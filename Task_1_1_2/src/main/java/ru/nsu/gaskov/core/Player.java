package ru.nsu.gaskov.core;

/**
 * The Player interface represents a generic player in the blackjack game.
 * It defines actions that a player can take during a game round.
 */
public interface Player {

    /**
     * Decides whether to make an insurance bet.
     */
    boolean insurance();

    /**
     * Decides whether to split the current hand into two separate hands.
     */
    boolean split();

    /**
     * Decides whether to double the current bet.
     */
    boolean doubleBet();

    /**
     * Decides whether to take another card (hit) from the dealer.
     */
    boolean hit();

    /**
     * Sets the initial starting bet for the player.
     */
    int makeStartingBet();

    /**
     * Retrieves the name of the player.
     *
     * @return the name of the player, default is "Player"
     */
    String getName();

    /**
     * Sets the current round for the player.
     *
     * @param round the round object representing the current round
     */
    void setRound(Round round);
}
