package ru.nsu.gaskov.snake.models;

public class Level {
    private int targetScore;
    private long speedNano;
    private double obstacleParameter;

    public Level() {
        targetScore = 10;
        speedNano = 200_000_000L;
        obstacleParameter = 0;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public long getSpeedNano() {
        return speedNano;
    }

    public double getObstacleParameter() {
        return obstacleParameter;
    }

    public void increaseDifficulty() {
        targetScore += 5;
        speedNano -= 25_000_000L;
        if (speedNano < 50_000_000L) {
            speedNano = 50_000_000L;
        }
        obstacleParameter += 0.1;
        if (obstacleParameter > 1.0) {
            obstacleParameter = 1.0;
        }
    }
}
