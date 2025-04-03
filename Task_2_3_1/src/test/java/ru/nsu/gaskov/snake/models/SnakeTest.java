package ru.nsu.gaskov.snake.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
public class SnakeTest {
    private Snake snake;

    /**
     * Setup.
     */
    @BeforeEach
    public void setUp() {
        Level level = new Level();
        GameBoard board = new GameBoard(100, 100, level);
        snake = new Snake(board, 50, 50);
    }

    @Test
    public void testSetDirection() {
        snake.setDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, snake.getDirection());
    }

    @Test
    public void testMove() {
        snake.setDirection(Direction.DOWN);
        snake.move();
        assertEquals(50, snake.getHead().x());
        assertEquals(51, snake.getHead().y());
    }

    @Test
    public void testAlive() {
        assertTrue(snake.isAlive());
        for (int i = 0; i < 50; ++i) {
            snake.move();
        }
        assertFalse(snake.isAlive());
    }
}
