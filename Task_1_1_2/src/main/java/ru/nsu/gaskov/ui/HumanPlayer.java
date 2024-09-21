package ru.nsu.gaskov.ui;

import java.util.Scanner;
import ru.nsu.gaskov.core.Player;
import ru.nsu.gaskov.core.Round;

/**
 * The HumanPlayer class represents a human player in the blackjack game.
 * It manages input from the player through the console, enabling actions
 * such as betting, taking insurance, splitting cards, doubling bets,
 * and hitting.
 */
public class HumanPlayer implements Player {
    private final Scanner scanner;
    private final String name;

    /**
     * Constructs a HumanPlayer and prompts the user for their name.
     *
     * @param scanner the Scanner instance used for user input
     */
    public HumanPlayer(Scanner scanner) {
        this.scanner = scanner;
        System.out.println("Enter player name: ");
        name = scanner.nextLine();
        System.out.println("Welcome to blackjack, " + name + "!");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setRound(Round round) {
    }

    @Override
    public boolean insurance() {
        String answer = InputValidator.getValidInput(
            scanner,
            n -> n != null && n.matches("(?i)^(yes|no|y|n)$"),
            name + ", insurance?: ",
            "Enter <Y/N> or <YES/NO>."
        );
        return answer != null && answer.matches("(?i)^(yes|y)$");
    }

    @Override
    public boolean split() {
        String answer = InputValidator.getValidInput(
            scanner,
            n -> n != null && n.matches("(?i)^(yes|no|y|n)$"),
            name + ", split?: ",
            "Enter <Y/N> or <YES/NO>."
        );
        return answer != null && answer.matches("(?i)^(yes|y)$");
    }

    @Override
    public boolean doubleBet() {
        String answer = InputValidator.getValidInput(
            scanner,
            n -> n != null && n.matches("(?i)^(yes|no|y|n)$"),
            name + ", double?: ",
            "Enter <Y/N> or <YES/NO>."
        );
        return answer != null && answer.matches("(?i)^(yes|y)$");
    }

    @Override
    public boolean hit() {
        String answer = InputValidator.getValidInput(
            scanner,
            n -> n != null && n.matches("(?i)^(yes|no|y|n)$"),
            name + ", hit?: ",
            "Enter <Y/N> or <YES/NO>."
        );
        return answer != null && answer.matches("(?i)^(yes|y)$");
    }

    @Override
    public int makeStartingBet() {
        String answer = InputValidator.getValidInput(
            scanner,
            n -> n != null && n.matches("\\d+"),
            name + ", enter your bet: ",
            "Enter a positive number."
        );
        if (answer != null) {
            return Integer.parseInt(answer);
        }
        System.out.println("Your bet is now 100.");
        return 100;
    }
}
