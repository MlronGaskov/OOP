package ru.nsu.gaskov;

import ru.nsu.gaskov.actions.*;
import ru.nsu.gaskov.card.Card;
import ru.nsu.gaskov.deck.Deck;
import ru.nsu.gaskov.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Round {
    public enum RoundState {
        BETS_PLACING, INITIAL_CARDS_DEALING, PLAYER_ACTS, DEALER_ACTS, ROUND_ENDED;
    }

    Deck deck;
    Player[] players;
    Hand[] hands;
    int activePlayerIndex = 0;
    List<Card> dealerOpenCards = new ArrayList<>();
    private Card dealerClosedCard;
    RoundState state = RoundState.BETS_PLACING;
    PlayerAct playerActState = PlayerAct.INSURANCE;
    int[] outcomes;

    public Round(Deck deck, Player[] players) {
        this.deck = Objects.requireNonNull(deck);
        this.players = players;
        for (Player player : players) {
            player.setRound(this);
        }
        this.outcomes = new int[players.length];
        this.hands = new Hand[players.length];
        for (int i = 0; i < players.length; ++i) {
            this.hands[i] = new Hand();
        }
    }

    public int[] getOutcomes() {
        return outcomes;
    }

    public Hand[] getHands() {
        return hands;
    }

    public List<Card> getDealerOpenCards() {
        return dealerOpenCards;
    }

    public Card getDealerClosedCard() {
        return dealerClosedCard;
    }

    private void nextPlayer() {
        activePlayerIndex++;
        if (activePlayerIndex == players.length) {
            state = RoundState.DEALER_ACTS;
        } else {
            playerActState = PlayerAct.INSURANCE;
        }
    }

    private Action stepPlayerActs() {
        Hand playerHand = hands[activePlayerIndex];
        Player player = players[activePlayerIndex];

        if (playerActState == PlayerAct.INSURANCE) {
            playerActState = PlayerAct.SPLIT;
            if (ValueCalculator.calculate(dealerOpenCards) >= 10 && player.insurance()) {
                playerHand.insure();
                return new PlayerAction(activePlayerIndex, PlayerAct.INSURANCE);
            }
        }
        if (playerHand.getCurrentPileValue() >= 21) {
            Action result = new PlayerAction(activePlayerIndex, PlayerAct.STAND);
            if (playerHand.isSplat() && playerHand.getCurrentCardsPile() == 0) {
                playerHand.changePile();
            } else {
                nextPlayer();
            }
            return result;
        }
        if (playerActState == PlayerAct.SPLIT) {
            playerActState = PlayerAct.DOUBLE;
            if (playerHand.isHandSplittable() && player.split()) {
                playerActState = PlayerAct.HIT;
                playerHand.split(deck.getOne(), deck.getOne());
                return new PlayerAction(activePlayerIndex, PlayerAct.SPLIT);
            }
        }
        if (playerActState == PlayerAct.DOUBLE) {
            playerActState = PlayerAct.HIT;
            if (player.doubleBet()) {
                playerHand.setDoubled();
                playerHand.take(deck.getOne());
                Action result = new PlayerAction(activePlayerIndex, PlayerAct.DOUBLE);
                nextPlayer();
                return result;
            }
        }
        if (playerActState == PlayerAct.HIT) {
            int currentPileValue = playerHand.getCurrentPileValue();
            if (currentPileValue < 21 && player.hit()) {
                playerHand.take(deck.getOne());
                return new PlayerAction(activePlayerIndex, PlayerAct.HIT);
            }
            Action result = new PlayerAction(activePlayerIndex, PlayerAct.STAND);
            if (playerHand.isSplat() && playerHand.getCurrentCardsPile() == 0) {
                playerHand.changePile();
            } else {
                nextPlayer();
            }
            return result;
        }
        throw new IllegalStateException("Unknown player act state");
    }

    public Action step() {
        if (state == RoundState.BETS_PLACING) {
            int betSize = players[activePlayerIndex].makeStartingBet();
            hands[activePlayerIndex].setBet(betSize);
            Action result = new PlayerAction(activePlayerIndex, PlayerAct.BET);
            activePlayerIndex++;
            if (activePlayerIndex == players.length) {
                activePlayerIndex = 0;
                state = RoundState.INITIAL_CARDS_DEALING;
            }
            return result;
        } else if (state == RoundState.INITIAL_CARDS_DEALING) {
            for (int i = 0; i < players.length; ++i) {
                for (int j = 0; j < 2; ++j) {
                    hands[i].take(deck.getOne());
                }
            }
            dealerOpenCards.add(deck.getOne());
            dealerClosedCard = deck.getOne();
            state = RoundState.PLAYER_ACTS;
            return GameAction.INITIAL_CARDS_DEALS;
        } else if (state == RoundState.PLAYER_ACTS) {
            return stepPlayerActs();
        } else if (state == RoundState.DEALER_ACTS) {
            dealerOpenCards.add(dealerClosedCard);
            while (ValueCalculator.calculate(dealerOpenCards) < 17) {
                dealerOpenCards.add(deck.getOne());
            }
            for (int i = 0; i < players.length; ++i) {
                outcomes[i] = OutcomeCalculator.calculate(hands[i], dealerOpenCards);
            }
            state = RoundState.ROUND_ENDED;
            return GameAction.OUTCOMES_DETERMINED;
        }
        throw new IllegalStateException("Unknown round state.");
    }
}
