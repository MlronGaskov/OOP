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
    public final Player[] players;
    public final int playersNumber;
    public final Deck deck;
    public final int[] money;

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
            n -> n != null && n.matches("\\d") && Integer.parseInt(n) <= 8,
            "Enter standard decks number: ",
            "The standard decks number must be a positive number not more than 8."
        );
        if (Objects.equals(decksAnswer, "1") || decksAnswer == null) {
            deck = new SmallDeck();
        } else {
            deck = new LargeDeck(Integer.parseInt(decksAnswer));
        }
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
