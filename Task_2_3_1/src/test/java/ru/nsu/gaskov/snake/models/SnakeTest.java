package ru.nsu.gaskov.snake.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests.
 */
public class SnakeTest {
    private Snake snake;

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
        assertEquals(50, snake.getSegments().getFirst().x());
        assertEquals(51, snake.getSegments().getFirst().y());
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
