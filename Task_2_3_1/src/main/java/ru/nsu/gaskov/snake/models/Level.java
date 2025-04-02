package ru.nsu.gaskov.snake.models;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Represents the game level settings.
 */
public class Level {
    private int targetScore;
    private long updateSpeedNano;
    private double obstacleParameter;
    private int enemiesCount;
    private int foodCount;

    /**
     * Creates a level with default settings.
     */
    public Level() {
        targetScore = 10;
        updateSpeedNano = 200_000_000L;
        obstacleParameter = 0;
        enemiesCount = 0;
        foodCount = 5;
    }

    /**
     * Returns the target score.
     */
    public int getTargetScore() {
        return targetScore;
    }

    /**
     * Returns the update speed in nanoseconds.
     */
    public long getUpdateSpeedNano() {
        return updateSpeedNano;
    }

    /**
     * Returns the obstacle parameter.
     */
    public double getObstacleParameter() {
        return obstacleParameter;
    }

    /**
     * Returns the number of enemies.
     */
    public int getEnemiesCount() {
        return enemiesCount;
    }

    /**
     * Returns the food count.
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * Increases the difficulty of the level.
     */
    public void increaseDifficulty() {
        targetScore += 5;
        enemiesCount += 1;
        foodCount += 1;
        updateSpeedNano = max(updateSpeedNano - 25_000_000L, 50_000_000L);
        obstacleParameter = min(obstacleParameter + 0.01, 0.1);
    }
}
