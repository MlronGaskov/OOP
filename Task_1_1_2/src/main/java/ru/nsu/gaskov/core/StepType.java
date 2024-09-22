package ru.nsu.gaskov.core;

/**
 * Enum representing the types of steps that can occur during a card game.
 */
public enum StepType {
    PLAYER_BETS,
    INITIAL_CARDS_DEAL,
    PLAYER_MAKES_INSURANCE_DECISION,
    PLAYER_MAKES_SPLIT_DECISION,
    PLAYER_MAKES_DOUBLE_DECISION,
    PLAYER_HITS,
    PLAYER_STANDS,
    OUTCOME_CALCULATION
}