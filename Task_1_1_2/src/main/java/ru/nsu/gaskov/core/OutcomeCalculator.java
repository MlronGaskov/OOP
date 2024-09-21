package ru.nsu.gaskov.core;

import java.util.List;

/**
 * The OutcomeCalculator class is responsible for calculating the outcome of a player's hand
 * in relation to the dealer's hand in a card game. It evaluates insurance payouts,
 * and overall winnings based on the game's rules.
 */
public class OutcomeCalculator {

    /**
     * Calculates the insurance payment for the player's hand based on the dealer's cards.
     *
     * @param playerHand  the player's hand to check for insurance
     * @param dealerCards the cards held by the dealer
     * @return the amount of the insurance payment, which can be positive, negative, or zero
     */
    private static int insurancePayment(Hand playerHand, List<Card> dealerCards) {
        int dealerCardsValue = ValueCalculator.calculate(dealerCards);
        if (playerHand.isInsured()) {
            if (dealerCardsValue == 21 && dealerCards.size() == 2) {
                return playerHand.getBet();
            }
            return -playerHand.getBet() / 2;
        }
        return 0;
    }

    /**
     * Determines the winning outcome of a player's pile compared to the dealer's cards.
     *
     * @param pile        the player's pile of cards
     * @param dealerCards the cards held by the dealer
     * @return 1 if the player wins, -1 if the player loses, or 0 for a tie
     */
    private static int pileWinning(List<Card> pile, List<Card> dealerCards) {
        int pileValue = ValueCalculator.calculate(pile);
        int dealerValue = ValueCalculator.calculate(dealerCards);
        if (pileValue > 21) {
            return -1;
        }
        if (dealerValue > 21) {
            return 1;
        }
        if (pileValue == 21 || dealerValue < pileValue) {
            return 1;
        }
        if (dealerValue == pileValue) {
            return 0;
        }
        return -1;
    }

    /**
     * Calculates the total winning amount for the player.
     *
     * @param playerHand  the player's hand
     * @param dealerCards the cards held by the dealer
     * @return the total winnings for the player, which may be positive or negative
     */
    public static int calculate(Hand playerHand, List<Card> dealerCards) {
        int winning = insurancePayment(playerHand, dealerCards);
        int pileWinning = pileWinning(playerHand.getCards1(), dealerCards) * playerHand.getBet();
        if (playerHand.isDoubled()) {
            pileWinning *= 2;
        }
        winning += pileWinning;
        if (playerHand.isSplit()) {
            winning += pileWinning(playerHand.getCards2(), dealerCards) * playerHand.getBet();
        }
        return winning;
    }
}
