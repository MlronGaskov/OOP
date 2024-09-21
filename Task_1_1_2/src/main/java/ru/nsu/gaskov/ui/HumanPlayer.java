package ru.nsu.gaskov.ui;

import ru.nsu.gaskov.core.Player;

import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner scanner;
    private final String name;

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
    public boolean insurance() {
        AnswerCondition condition = new AnswerCondition();
        String answer = InputValidator.getValidInput(
            scanner,
            condition,
            name + ", insurance?: ",
            "Error: Enter <Y/N> or <YES/NO>."
        );
        return condition.isAnswerYes(answer);
    }

    @Override
    public boolean split() {
        AnswerCondition condition = new AnswerCondition();
        String answer = InputValidator.getValidInput(
            scanner,
            condition,
            name + ", split?: ",
            "Error: Enter <Y/N> or <YES/NO>."
        );
        return condition.isAnswerYes(answer);
    }

    @Override
    public boolean doubleBet() {
        AnswerCondition condition = new AnswerCondition();
        String answer = InputValidator.getValidInput(
            scanner,
            condition,
            name + ", double?: ",
            "Error: Enter <Y/N> or <YES/NO>."
        );
        return condition.isAnswerYes(answer);
    }

    @Override
    public boolean hit() {
        AnswerCondition condition = new AnswerCondition();
        String answer = InputValidator.getValidInput(
            scanner,
            condition,
            name + ", hit?: ",
            "Error: Enter <Y/N> or <YES/NO>."
        );
        return condition.isAnswerYes(answer);
    }

    @Override
    public int makeStartingBet() {
        BetCondition condition = new BetCondition();
        String answer = InputValidator.getValidInput(
            scanner,
            condition,
            name + ", enter your bet?: ",
            "Error: The bet must be a positive number."
        );
        if (answer != null) {
            return Integer.parseInt(answer);
        }
        System.out.println("Your bet is now 100.");
        return 100;
    }
}
