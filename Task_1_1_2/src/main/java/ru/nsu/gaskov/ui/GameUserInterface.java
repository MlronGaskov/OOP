package ru.nsu.gaskov.ui;

import java.util.Scanner;
import java.util.List;
import ru.nsu.gaskov.core.Card;
import ru.nsu.gaskov.core.Game;
import ru.nsu.gaskov.core.Hand;
import ru.nsu.gaskov.core.Player;
import ru.nsu.gaskov.core.Round;
import ru.nsu.gaskov.core.Step;
import ru.nsu.gaskov.core.StepType;
import ru.nsu.gaskov.core.ValueCalculator;

/**
 * The GameUI class manages the user interface for the blackjack game,
 * facilitating interaction with players and displaying game progress,
 * including betting, cards, and outcomes.
 */
public class GameUserInterface {

    /**
     * Prints the current amount of money for each player.
     *
     * @param players an array of Player objects
     * @param money an array of integers representing the money for each player
     */
    private static void printMoney(Player[] players, int[] money) {
        for (int i = 0; i < players.length; ++i) {
            System.out.print(players[i].getName() + ": " + money[i] + " ");
        }
        System.out.println();
    }

    /**
     * Displays the dealer's cards and their values.
     *
     * @param round the current Round object containing dealer card info
     */
    private static void printDealerCards(Round round) {
        List<Card> dealerCards = round.dealerOpenCards;
        if (dealerCards.size() == 1) {
            System.out.println("Dealer cards: \n[" + dealerCards.getFirst() + ", <closed card>]");
        } else {
            System.out.println("Dealer opens: " + dealerCards.get(1));
            for (int i = 2; i < dealerCards.size(); ++i) {
                System.out.println("Dealer takes: " + dealerCards.get(i));
            }
            System.out.println("Dealer cards: \n" + dealerCards + " -> " + ValueCalculator.calculate(dealerCards));
        }
    }

    /**
     * Prints the details of a player's hand including bets and card values.
     *
     * @param playerHand the Hand object representing the player's hand
     */
    private static void printPlayerHand(Hand playerHand) {
        System.out.print("Bet: " + playerHand.getBet() +
            (playerHand.isSplit() || playerHand.isDoubled() ? "x2 " : " "));
        System.out.println(playerHand.isInsured() ?
            ("Insure: " + playerHand.getBet() / 2 + " ") : "");
        System.out.println(playerHand.getCards1() + " -> " + playerHand.getCards1Value());
        if (playerHand.isSplit()) {
            System.out.println(playerHand.getCards2() + " -> " + playerHand.getCards2Value());
        }
    }

    /**
     * Checks if the current step involves player decisions regarding their hand.
     *
     * @param step the StepType representing the current game step
     * @return true if the step is a player decision step, false otherwise
     */
    private static boolean isPlayerHandPlayStep(StepType step) {
        return step == StepType.PLAYER_MAKES_INSURANCE_DECISION ||
            step == StepType.PLAYER_MAKES_SPLIT_DECISION ||
            step == StepType.PLAYER_MAKES_DOUBLE_DECISION ||
            step == StepType.PLAYER_HITS;
    }

    /**
     * The main method that initiates the game UI, prompting players for
     * their actions, and managing the flow of the game for multiple rounds.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        for (int i = 1; i <= 10; ++i) {
            System.out.println("Round " + i);
            Round round = game.PlayRound();
            while (round.currentStep != null) {
                if (round.currentStep == StepType.PLAYER_BETS) {
                    Step act = round.step();
                    System.out.println(game.players[act.playerIndex()].getName()
                        + " BETS " + round.hands[act.playerIndex()].getBet());
                } else if (round.currentStep == StepType.INITIAL_CARDS_DEAL) {
                    round.step();
                    System.out.println("INITIAL CARDS HAVE BEEN DEALT");
                    for (int j = 0; j < game.playersNumber; ++j) {
                        System.out.println(game.players[j].getName() + " cards: ");
                        printPlayerHand(round.hands[j]);
                    }
                    printDealerCards(round);
                } else if (isPlayerHandPlayStep(round.currentStep)) {
                    Step act = round.step();
                    if (act.act() == StepType.PLAYER_MAKES_INSURANCE_DECISION) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": insure");
                    } else if (act.act() == StepType.PLAYER_MAKES_SPLIT_DECISION) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": split");
                    } else if (act.act() == StepType.PLAYER_MAKES_DOUBLE_DECISION) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": double");
                    } else if (act.act() == StepType.PLAYER_HITS) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": hit");
                    } else if (act.act() == StepType.PLAYER_STANDS) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": stand");
                    }
                    printPlayerHand(round.hands[act.playerIndex()]);
                } else if (round.currentStep == StepType.OUTCOME_CALCULATION) {
                    round.step();
                    printDealerCards(round);
                    printMoney(round.players, round.outcomes);
                    game.countMoney(round.outcomes);
                    game.deck.collect();
                    System.out.println("ROUND ENDED");
                }
            }
        }
    }
}
