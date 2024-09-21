package ru.nsu.gaskov.ui;

import ru.nsu.gaskov.core.*;

import java.util.List;
import java.util.Scanner;

public class GameUI {
    private static void printMoney(Player[] players, int[] money) {
        for (int i = 0; i < players.length; ++i) {
            System.out.print(players[i].getName() + ": " + money[i] + " ");
        }
        System.out.println();
    }

    private static void printDealerCards(Round round) {
        List<Card> dealerCards = round.getDealerOpenCards();
        if (dealerCards.size() == 1) {
            System.out.println("Dealer cards: \n[" + dealerCards.getFirst() + ", <closed card>]");
        } else {
            System.out.println("Dealer opens: " + dealerCards.get(1));
            for (int i = 2; i < dealerCards.size(); ++i) {
                System.out.println("Dealer takes: " + dealerCards.get(i));
            }
            System.out.println("Dealer cards: \n" +
                dealerCards +
                " -> " + ValueCalculator.calculate(dealerCards));
        }
    }

    private static void printPlayerHand(Hand playerHand) {
        System.out.print("Bet: " + playerHand.getBet() +
            (playerHand.isSplat() || playerHand.isDoubled() ? "x2 " : " "));
        System.out.println(playerHand.isInsured() ?
            ("Insure: " + playerHand.getBet() / 2 + " ") : "");
        System.out.println(playerHand.getCards1() + " -> " + playerHand.getCards1Value());
        if (playerHand.isSplat()) {
            System.out.println(playerHand.getCards2() + " -> " + playerHand.getCards2Value());
        }
    }

    private static boolean isPlayerHandPlayStep(StepType step) {
        return step == StepType.PLAYER_MAKES_INSURANCE_DECISION ||
            step == StepType.PLAYER_MAKES_SPLIT_DECISION ||
            step == StepType.PLAYER_MAKES_DOUBLE_DECISION ||
            step == StepType.PLAYER_HITS;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        for (int i = 1; i <= 10; ++i) {
            System.out.println("Round " + i);
            printMoney(game.players, game.getMoney());
            Round round = game.PlayRound();
            while (round.currentStep != null) {
                if (round.currentStep == StepType.PLAYER_BETS) {
                    Step act = round.step();
                    System.out.println(game.players[act.playerIndex()].getName() +
                        " BETS " + round.getHands()[act.playerIndex()].getBet());
                } else if (round.currentStep == StepType.INITIAL_CARDS_DEAL) {
                    round.step();
                    System.out.println("INITIAL CARDS HAVE BEEN DEALT");
                    for (int j = 0; j < game.playersNumber; ++j) {
                        System.out.println(game.players[j].getName() + " cards: ");
                        printPlayerHand(round.getHands()[j]);
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
                    printPlayerHand(round.getHands()[act.playerIndex()]);
                } else if (round.currentStep == StepType.OUTCOME_CALCULATION) {
                    round.step();
                    printDealerCards(round);
                    printMoney(round.players, round.getOutcomes());
                    game.countMoney(round.getOutcomes());
                    game.deck.collect();
                    System.out.println("ROUND ENDED");
                }
            }
        }
    }
}
