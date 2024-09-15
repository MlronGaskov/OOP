package ru.nsu.gaskov.actions;

public record PlayerAction(int playerIndex, PlayerAct action) implements Action {}
