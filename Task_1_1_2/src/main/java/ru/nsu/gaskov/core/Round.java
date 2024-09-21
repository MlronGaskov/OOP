package ru.nsu.gaskov.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Round class manages a single round of play in a card game.
 * It coordinates the actions of players and the dealer, processes bets, deals cards,
 * and calculates the outcomes for each player at the end of the round.
 */
public class Round {
    private final Deck deck;
    public final Player[] players;
    public final Hand[] hands;
    public final List<Card> dealerOpenCards = new ArrayList<>();
    public Card dealerClosedCard;
    public StepType currentStep = StepType.PLAYER_BETS;
    public int currentPlayerIndex = 0;
    public final int[] outcomes;

    /**
     * Constructs a Round with the specified deck and player array.
     *
     * @param deck   the deck of cards to be used in this round
     * @param players the players participating in this round
     */
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

    /**
     * Moves to the next player or updates the game state if all players have acted.
     */
    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.length) {
            currentStep = StepType.OUTCOME_CALCULATION;
        } else {
            currentStep = StepType.PLAYER_MAKES_INSURANCE_DECISION;
        }
    }

    /**
     * Handles the actions of the current player based on the current game step.
     *
     * @return a Step object indicating the action taken by the player
     * @throws IllegalStateException if the player action state is unknown
     */
    private Step stepPlayerActs() {
        Hand playerHand = hands[currentPlayerIndex];
        Player player = players[currentPlayerIndex];

        if (currentStep == StepType.PLAYER_MAKES_INSURANCE_DECISION) {
            currentStep = StepType.PLAYER_MAKES_SPLIT_DECISION;
            if (ValueCalculator.calculate(dealerOpenCards) >= 10 && player.insurance()) {
                playerHand.insure();
                return new Step(StepType.PLAYER_MAKES_INSURANCE_DECISION, currentPlayerIndex);
            }
        }
        if (playerHand.getCurrentPileValue() >= 21) {
            Step result = new Step(StepType.PLAYER_STANDS, currentPlayerIndex);
            if (playerHand.isSplit() && playerHand.getCurrentCardsPile() == 0) {
                playerHand.changePile();
            } else {
                nextPlayer();
            }
            return result;
        }
        if (currentStep == StepType.PLAYER_MAKES_SPLIT_DECISION) {
            currentStep = StepType.PLAYER_MAKES_DOUBLE_DECISION;
            if (playerHand.isHandSplittable() && player.split()) {
                currentStep = StepType.PLAYER_HITS;
                playerHand.split(deck.getOne(), deck.getOne());
                return new Step(StepType.PLAYER_MAKES_SPLIT_DECISION, currentPlayerIndex);
            }
        }
        if (currentStep == StepType.PLAYER_MAKES_DOUBLE_DECISION) {
            currentStep = StepType.PLAYER_HITS;
            if (player.doubleBet()) {
                playerHand.setDoubled();
                playerHand.take(deck.getOne());
                Step result = new Step(
                    StepType.PLAYER_MAKES_DOUBLE_DECISION,
                    currentPlayerIndex);
                nextPlayer();
                return result;
            }
        }
        if (currentStep == StepType.PLAYER_HITS) {
            int currentPileValue = playerHand.getCurrentPileValue();
            if (currentPileValue < 21 && player.hit()) {
                playerHand.take(deck.getOne());
                return new Step(StepType.PLAYER_HITS, currentPlayerIndex);
            }
            Step result = new Step(StepType.PLAYER_STANDS, currentPlayerIndex);
            if (playerHand.isSplit() && playerHand.getCurrentCardsPile() == 0) {
                playerHand.changePile();
            } else {
                nextPlayer();
            }
            return result;
        }
        throw new IllegalStateException("Unknown player act state");
    }

    /**
     * Checks if the current step is one that requires the player to take action with their hand.
     *
     * @return true if the current step requires player action, false otherwise
     */
    private boolean isPlayerHandPlayStep() {
        return currentStep == StepType.PLAYER_MAKES_INSURANCE_DECISION
            || currentStep == StepType.PLAYER_MAKES_SPLIT_DECISION
            || currentStep == StepType.PLAYER_MAKES_DOUBLE_DECISION
            || currentStep == StepType.PLAYER_HITS;
    }

    /**
     * Executes the current step of the game round.
     *
     * @return a Step object representing the result of the executed step
     * @throws IllegalStateException if the round state is unknown
     */
    public Step step() {
        if (currentStep == StepType.PLAYER_BETS) {
            int betSize = players[currentPlayerIndex].makeStartingBet();
            hands[currentPlayerIndex].setBet(betSize);
            Step result = new Step(StepType.PLAYER_BETS, currentPlayerIndex);
            currentPlayerIndex++;
            if (currentPlayerIndex == players.length) {
                currentPlayerIndex = 0;
                currentStep = StepType.INITIAL_CARDS_DEAL;
            }
            return result;
        } else if (currentStep == StepType.INITIAL_CARDS_DEAL) {
            for (int i = 0; i < players.length; ++i) {
                for (int j = 0; j < 2; ++j) {
                    hands[i].take(deck.getOne());
                }
            }
            dealerOpenCards.add(deck.getOne());
            dealerClosedCard = deck.getOne();
            currentStep = StepType.PLAYER_MAKES_INSURANCE_DECISION;
            return new Step(StepType.INITIAL_CARDS_DEAL, -1);
        } else if (isPlayerHandPlayStep()) {
            return stepPlayerActs();
        } else if (currentStep == StepType.OUTCOME_CALCULATION) {
            dealerOpenCards.add(dealerClosedCard);
            while (ValueCalculator.calculate(dealerOpenCards) < 17) {
                dealerOpenCards.add(deck.getOne());
            }
            for (int i = 0; i < players.length; ++i) {
                outcomes[i] = OutcomeCalculator.calculate(hands[i], dealerOpenCards);
            }
            currentStep = null;
            return new Step(StepType.OUTCOME_CALCULATION, -1);
        }
        throw new IllegalStateException("Unknown round state.");
    }
}
