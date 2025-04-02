package ru.nsu.gaskov.snake.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
public class EnemyTest {
    private GameBoard board;

    @BeforeEach
    public void setUp() {
        Level level = new Level();
        board = new GameBoard(100, 100, level);
    }

    @Test
    public void testChoseDirectionForAllEnemies() {
        Enemy stupidEnemy = new StupidEnemy(board, 50, 50);
        stupidEnemy.choseDirection();
        Enemy smartEnemy = new SmartEnemy(board, 50, 50);
        smartEnemy.choseDirection();
        Enemy greedyEnemy = new GreedyEnemy(board, 50, 50);
        greedyEnemy.choseDirection();
        Enemy satiatedEnemy = new SatiatedEnemy(board, 50, 50);
        satiatedEnemy.choseDirection();

        assertAll("Enemy choseDirection checks",
                () -> assertNotNull(stupidEnemy.getSnake().getDirection()),
                () -> assertNotNull(smartEnemy.getSnake().getDirection()),
                () -> assertNotNull(greedyEnemy.getSnake().getDirection()),
                () -> assertNotNull(satiatedEnemy.getSnake().getDirection())
        );
    }
}
