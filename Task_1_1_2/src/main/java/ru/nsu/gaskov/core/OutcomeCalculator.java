package ru.nsu.gaskov.core;

import java.util.List;

public class OutcomeCalculator {
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

    private static int pileWinning(List<Card> pile, List<Card> dealerCards) {
        int pileValue = ValueCalculator.calculate(pile);
        int dealerValue = ValueCalculator.calculate(dealerCards);
        if (pileValue > 21) {
            return -1;
        } else if (dealerValue > 21) {
            return 1;
        }
        if (pileValue == 21 || dealerValue < pileValue) {
            return 1;
        } else if (dealerValue == pileValue) {
            return 0;
        } else {
            return -1;
        }
    }

    public static int calculate(Hand playerHand, List<Card> dealerCards) {
        int winning = insurancePayment(playerHand, dealerCards);
        int pileWinning = pileWinning(playerHand.getCards1(), dealerCards) * playerHand.getBet();
        if (playerHand.isDoubled()) {
            pileWinning *= 2;
        }
        winning += pileWinning;
        if (playerHand.isSplat()) {
            winning += pileWinning(playerHand.getCards2(), dealerCards) * playerHand.getBet();
        }
        return winning;
    }
}
