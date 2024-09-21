package ru.nsu.gaskov.core;

import ru.nsu.gaskov.ui.InputValidator;
import ru.nsu.gaskov.ui.PlayersNumberCondition;
import ru.nsu.gaskov.ui.StandardDecksNumberCondition;
import ru.nsu.gaskov.ui.HumanPlayer;

import java.util.Objects;
import java.util.Scanner;

public class Game {
    public Player[] players;
    public int playersNumber;
    public Deck deck;
    private final int[] money;

    public Game(Scanner scanner) {
        PlayersNumberCondition condition = new PlayersNumberCondition();
        String answer = InputValidator.getValidInput(
            scanner,
            condition,
            "Enter player number: ",
            "Error: The player number must be a positive number not more than 3."
        );
        playersNumber = (answer != null) ? Integer.parseInt(answer) : 1;
        players = new Player[playersNumber];
        money = new int[playersNumber];
        for (int i = 0; i < playersNumber; ++i) {
            players[i] = new HumanPlayer(scanner);
            money[i] = 1000;
        }
        StandardDecksNumberCondition decksCondition = new StandardDecksNumberCondition();
        String decksAnswer = InputValidator.getValidInput(
            scanner,
            decksCondition,
            "Enter standard decks number: ",
            "Error: The player number must be a positive number not more than 8."
        );
        if (Objects.equals(decksAnswer, "1") || decksAnswer == null) {
            deck = new SmallDeck();
        } else {
            deck = new LargeDeck(Integer.parseInt(decksAnswer));
        }
    }

    public Round PlayRound() {
        return new Round(deck, players);
    }

    public int[] getMoney() {
        return money;
    }

    public void countMoney(int[] roundOutcomes) {
        if (roundOutcomes.length == playersNumber) {
            for (int i = 0; i < roundOutcomes.length; ++i) {
                money[i] += roundOutcomes[i];
            }
        }
    }
}
