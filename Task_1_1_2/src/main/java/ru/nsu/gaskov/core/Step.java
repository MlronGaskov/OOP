package ru.nsu.gaskov.core;

/**
 * The Step record represents a single action.
 *
 * @param act the type of action (step) that was performed
 * @param playerIndex the index of the player who performed the action
 */
public record Step(StepType act, int playerIndex) {}
