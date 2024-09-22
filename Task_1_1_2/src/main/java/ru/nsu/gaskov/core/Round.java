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
    private final Player[] players;
    private final Hand[] hands;
    private final List<Card> dealerOpenCards = new ArrayList<>();
    private Card dealerClosedCard;
    private StepType currentStep = StepType.PLAYER_BETS;
    private int currentPlayerIndex = 0;
    private int[] outcomes = new int[] {};

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
     * Retrieves the current step.
     *
     * @return the current step as a {@link StepType} enum.
     */
    public StepType getCurrentStep() {
        return currentStep;
    }

    /**
     * Retrieves the outcomes of the game.
     *
     * @return a cloned array of integers representing the outcomes.
     */
    public int[] getOutcomes() {
        return outcomes.clone();
    }

    /**
     * Retrieves the closed card of the dealer.
     *
     * @return the dealer's closed card as a {@link Card} object.
     */
    public Card getDealerClosedCard() {
        return dealerClosedCard;
    }

    /**
     * Retrieves the list of open cards held by the dealer.
     *
     * @return a list of {@link Card} objects representing the dealer's open cards.
     */
    public List<Card> getDealerOpenCards() {
        return new ArrayList<>(dealerOpenCards);
    }

    /**
     * Retrieves the deck of cards used in the game.
     *
     * @return the {@link Deck} object being used in the game.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Retrieves the array of players in the game.
     *
     * @return a cloned array of {@link Player} objects.
     */
    public Player[] getPlayers() {
        return players.clone();
    }

    /**
     * Retrieves the array of hands in play.
     *
     * @return a cloned array of {@link Hand} objects representing the hands in the game.
     */
    public Hand[] getHands() {
        return hands.clone();
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
        if (playerHand.getCurrentPileValue() >= Constants.BLACKJACK_SCORE) {
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
            if (currentPileValue < Constants.BLACKJACK_SCORE && player.hit()) {
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
            while (ValueCalculator.calculate(dealerOpenCards) < Constants.DEALER_STAND_SCORE) {
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
