package ru.nsu.gaskov.core;

import java.util.Objects;
import java.util.Scanner;
import ru.nsu.gaskov.ui.HumanPlayer;
import ru.nsu.gaskov.ui.InputValidator;

/**
 * The Game class represents a card game, managing players,
 * the deck of cards, and player money.
 */
public class Game {
    private final Player[] players;
    private final int playersNumber;
    private final Deck deck;
    private final int[] money;

    /**
     * Constructs a new Game instance, initializing players, deck,
     * and their respective starting money based on user input.
     *
     * @param scanner a Scanner instance used for input validation
     */
    public Game(Scanner scanner) {
        String answer = InputValidator.getValidInput(
            scanner,
            n -> n != null && n.matches("\\d") && Integer.parseInt(n) <= 3,
            "Enter players number: ",
            "The players number must be a positive number not more than 3."
        );
        playersNumber = (answer != null) ? Integer.parseInt(answer) : 1;
        players = new Player[playersNumber];
        money = new int[playersNumber];
        for (int i = 0; i < playersNumber; ++i) {
            players[i] = new HumanPlayer(scanner);
            money[i] = 1000;
        }
        String decksAnswer = InputValidator.getValidInput(
            scanner,
            n -> n != null && (n.matches("\\d") && Integer.parseInt(n) <= 8
                || n.matches("test\\|.*")),
            "\nEnter standard decks number: ",
            "The standard decks number must be a positive number not more than 8."
        );
        if (decksAnswer != null && decksAnswer.matches("test\\|.*")) {
            deck = new TestingDeck(decksAnswer.split("\\|"));
        } else if (Objects.equals(decksAnswer, "1") || decksAnswer == null) {
            deck = new SmallDeck();
        } else {
            deck = new LargeDeck(Integer.parseInt(decksAnswer));
        }
    }

    /**
     * Retrieves the array of players in the game.
     *
     * @return a cloned array of {@link Player} objects representing the players in the game.
     */
    public Player[] getPlayers() {
        return players.clone();
    }

    /**
     * Retrieves the number of players currently active in the game.
     *
     * @return the total count of players as an integer.
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * Retrieves the deck of cards used in the game.
     *
     * @return the {@link Deck} object representing the current deck of cards.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Retrieves the array of monetary values associated with the players.
     *
     * @return a cloned array of integers representing the money amounts for each player.
     */
    public int[] getMoney() {
        return money.clone();
    }

    /**
     * Starts a new round of the game.
     *
     * @return a new {@link Round} object representing the current round
     */
    public Round playRound() {
        return new Round(deck, players);
    }

    /**
     * Updates the money for each player based on the outcomes
     * of the current round.
     *
     * @param roundOutcomes an array of integers representing
     *                      the outcomes for each player in the round
     */
    public void countMoney(int[] roundOutcomes) {
        if (roundOutcomes.length == playersNumber) {
            for (int i = 0; i < roundOutcomes.length; ++i) {
                money[i] += roundOutcomes[i];
            }
        }
    }
}
