package ru.nsu.gaskov;

import ru.nsu.gaskov.actions.Action;
import ru.nsu.gaskov.actions.GameAction;
import ru.nsu.gaskov.actions.PlayerAct;
import ru.nsu.gaskov.actions.PlayerAction;
import ru.nsu.gaskov.card.Card;
import ru.nsu.gaskov.player.Player;

import java.util.Arrays;
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
        System.out.print(playerHand.isInsured() ?
            ("Insure: " + playerHand.getBet() / 2 + " ") : "");
        System.out.println(playerHand.getCards1() + " -> " + playerHand.getCards1Value());
        if (playerHand.isSplat()) {
            System.out.println(playerHand.getCards2() + " -> " + playerHand.getCards2Value());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        for (int i = 1; i <= 10; ++i) {
            System.out.println("Round " + i);
            printMoney(game.players, game.getMoney());
            Round round = game.PlayRound();
            while (round.state != Round.RoundState.ROUND_ENDED) {
                if (round.state == Round.RoundState.BETS_PLACING) {
                    PlayerAction act = (PlayerAction) round.step();
                    System.out.println(game.players[act.playerIndex()].getName() +
                        " BETS " + round.hands[act.playerIndex()].getBet());
                } else if (round.state == Round.RoundState.INITIAL_CARDS_DEALING) {
                    round.step();
                    System.out.println("INITIAL CARDS HAVE BEEN DEALT");
                    for (int j = 0; j < game.playersNumber; ++j) {
                        System.out.println(game.players[j].getName() + " cards: ");
                        printPlayerHand(round.hands[j]);
                    }
                    printDealerCards(round);
                } else if (round.state == Round.RoundState.PLAYER_ACTS) {
                    PlayerAction act = (PlayerAction) round.step();
                    if (act.action() == PlayerAct.INSURANCE) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": insure");
                    } else if (act.action() == PlayerAct.SPLIT) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": split");
                    } else if (act.action() == PlayerAct.DOUBLE) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": double");
                    } else if (act.action() == PlayerAct.HIT) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": hit");
                    } else if (act.action() == PlayerAct.STAND) {
                        System.out.println(game.players[act.playerIndex()].getName() + ": stand");
                    }
                    printPlayerHand(round.hands[act.playerIndex()]);
                } else if (round.state == Round.RoundState.DEALER_ACTS) {
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
