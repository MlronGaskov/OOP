package ru.nsu.gaskov.ui;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
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
    public static void printMoney(Player[] players, int[] money) {
        for (int i = 0; i < players.length; ++i) {
            System.out.print(players[i].getName() + ": " + money[i] + " ");
        }
        System.out.println();
    }

    /**
     * Prints the card.
     *
     * @param card a card to print;
     */
    public static String printCard(Card card) {
        String suitSymbol = switch (card.suit()) {
            case HEARTS -> "Hearts";
            case DIAMONDS -> "Diamonds";
            case CLUBS -> "Clubs";
            case SPADES -> "Spades";
        };
        String rankSymbol = switch (card.rank()) {
            case TWO -> "2";
            case THREE -> "3";
            case FOUR -> "4";
            case FIVE -> "5";
            case SIX -> "6";
            case SEVEN -> "7";
            case EIGHT -> "8";
            case NINE -> "9";
            case TEN -> "10";
            case JACK -> "Jack";
            case QUEEN -> "Queen";
            case KING -> "King";
            case ACE -> "Ace";
        };
        return rankSymbol + " of " + suitSymbol;
    }

    /**
     * Displays the dealer's cards and their values.
     *
     * @param round the current Round object containing dealer card info
     */
    public static void printDealerCards(Round round) {
        List<Card> dealerCards = round.getDealerOpenCards();
        if (dealerCards.size() == 1) {
            System.out.println("\nDealer cards: \n["
                + printCard(dealerCards.getFirst()) + ", <closed card>]");
        } else {
            System.out.println("\nDealer opens: " + printCard(dealerCards.get(1)));
            for (int i = 2; i < dealerCards.size(); ++i) {
                System.out.println("Dealer takes: " + printCard(dealerCards.get(i)));
            }
            System.out.println("Dealer cards: \n"
                + dealerCards.stream().map(GameUserInterface::printCard).toList()
                + " -> " + ValueCalculator.calculate(dealerCards));
        }
    }

    /**
     * Prints the details of a player's hand including bets and card values.
     *
     * @param playerHand the Hand object representing the player's hand
     */
    public static void printPlayerHand(Hand playerHand) {
        System.out.print("\nBet: " + playerHand.getBet()
            + (playerHand.isSplit() || playerHand.isDoubled() ? "x2 " : " "));
        System.out.println(playerHand.isInsured()
            ? ("Insure: " + playerHand.getBet() / 2 + " ") : "");
        System.out.println(playerHand
            .getCards1().stream().map(GameUserInterface::printCard).toList()
            + " -> " + playerHand.getCards1Value());
        if (playerHand.isSplit()) {
            System.out.println(playerHand
                .getCards2().stream().map(GameUserInterface::printCard).toList()
                + " -> " + playerHand.getCards2Value());
        }
        System.out.println();
    }

    /**
     * Checks if the current step involves player decisions regarding their hand.
     *
     * @param step the StepType representing the current game step
     * @return true if the step is a player decision step, false otherwise
     */
    private static boolean isPlayerHandPlayStep(StepType step) {
        return step == StepType.PLAYER_MAKES_INSURANCE_DECISION
            || step == StepType.PLAYER_MAKES_SPLIT_DECISION
            || step == StepType.PLAYER_MAKES_DOUBLE_DECISION
            || step == StepType.PLAYER_HITS;
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
            System.out.println("\nRound " + i + "\n");
            Round round = game.playRound();
            while (round.getCurrentStep() != null) {
                if (round.getCurrentStep() == StepType.PLAYER_BETS) {
                    Step act = round.step();
                    System.out.println("\n" + game.getPlayers()[act.playerIndex()].getName()
                        + " BETS " + round.getHands()[act.playerIndex()].getBet() + "\n");
                } else if (round.getCurrentStep() == StepType.INITIAL_CARDS_DEAL) {
                    round.step();
                    System.out.println("INITIAL CARDS HAVE BEEN DEALT");
                    for (int j = 0; j < game.getPlayersNumber(); ++j) {
                        System.out.print("\n" + game.getPlayers()[j].getName() + " cards: ");
                        printPlayerHand(round.getHands()[j]);
                    }
                    printDealerCards(round);
                } else if (isPlayerHandPlayStep(round.getCurrentStep())) {
                    Step act = round.step();
                    if (act.act() == StepType.PLAYER_MAKES_INSURANCE_DECISION) {
                        System.out.println(game.getPlayers()[act.playerIndex()].getName()
                            + ": insure");
                    } else if (act.act() == StepType.PLAYER_MAKES_SPLIT_DECISION) {
                        System.out.println(game.getPlayers()[act.playerIndex()].getName()
                            + ": split");
                    } else if (act.act() == StepType.PLAYER_MAKES_DOUBLE_DECISION) {
                        System.out.println(game.getPlayers()[act.playerIndex()].getName()
                            + ": double");
                    } else if (act.act() == StepType.PLAYER_HITS) {
                        System.out.println(game.getPlayers()[act.playerIndex()].getName()
                            + ": hit");
                    } else if (act.act() == StepType.PLAYER_STANDS) {
                        System.out.println(game.getPlayers()[act.playerIndex()].getName()
                            + ": stand");
                    }
                    System.out.print("\n" + game.getPlayers()[act.playerIndex()].getName()
                        + " cards: ");
                    printPlayerHand(round.getHands()[act.playerIndex()]);
                } else if (round.getCurrentStep() == StepType.OUTCOME_CALCULATION) {
                    round.step();
                    printDealerCards(round);
                    printMoney(round.getPlayers(), round.getOutcomes());
                    game.countMoney(round.getOutcomes());
                    game.getDeck().collect();
                    System.out.println("ROUND ENDED (print <end> to exit game)");
                    String input = scanner.nextLine();
                    if (Objects.equals(input, "end")) {
                        return;
                    }
                }
            }
        }
    }
}
