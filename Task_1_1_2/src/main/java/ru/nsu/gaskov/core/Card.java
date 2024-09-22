package ru.nsu.gaskov.core;

/**
 * A record representing a playing card in a standard deck.
 * Each card has a suit and a rank.
 *
 * @param suit the suit of the card (e.g., hearts, diamonds)
 * @param rank the rank of the card (e.g., Ace, 2, King)
 */
public record Card(Suit suit, Rank rank) {}