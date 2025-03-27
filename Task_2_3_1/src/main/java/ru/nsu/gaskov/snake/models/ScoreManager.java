package ru.nsu.gaskov.snake.models;

public class ScoreManager {
    private int score;

    public ScoreManager() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    public void incrementScore(int score) {
        this.score += score;
    }
}
