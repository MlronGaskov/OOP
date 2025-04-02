package ru.nsu.gaskov.snake.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests.
 */
public class LevelTest {

    @Test
    void testIncreaseDifficulty() {
        Level level = new Level();
        int initialTarget = level.getTargetScore();
        long initialSpeed = level.getUpdateSpeedNano();
        double initialObstacle = level.getObstacleParameter();
        int initialEnemies = level.getEnemiesCount();
        int initialFood = level.getFoodCount();

        level.increaseDifficulty();

        assertAll(
                () -> assertEquals(initialTarget + 5, level.getTargetScore()),
                () -> assertEquals(initialEnemies + 1, level.getEnemiesCount()),
                () -> assertEquals(initialFood + 1, level.getFoodCount()),
                () -> assertTrue(level.getUpdateSpeedNano() <= initialSpeed),
                () -> assertTrue(level.getObstacleParameter() >= initialObstacle)
        );
    }
}
