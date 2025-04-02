package ru.nsu.gaskov.snake.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Tests.
 */
public class GameBoardTest {

    @BeforeAll
    public static void initJFX() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new Exception("JavaFX Platform failed to start.");
        }
    }

    @Test
    public void testInitialization() {
        Level level = new Level();
        int cols = 20;
        int rows = 20;
        GameBoard board = new GameBoard(cols, rows, level);

        assertAll(
                () -> assertEquals(cols, board.getCols()),
                () -> assertEquals(rows, board.getRows()),
                () -> assertNotNull(board.getUserSnake()),
                () -> assertNotNull(board.getObstacles()),
                () -> assertNotNull(board.getFood()),
                () -> assertFalse(board.getFood().isEmpty())
        );
    }

    @Test
    public void testUpdate() {
        Level level = new Level();
        GameBoard board = new GameBoard(20, 20, level);

        int initialX = board.getUserSnake().getSegments().getFirst().x();
        int initialY = board.getUserSnake().getSegments().getFirst().y();

        board.update();

        int newX = board.getUserSnake().getSegments().getFirst().x();
        int newY = board.getUserSnake().getSegments().getFirst().y();

        assertTrue((initialX != newX) || (initialY != newY));
    }

    @Test
    public void testDraw() {
        Level level = new Level();
        level.increaseDifficulty();
        GameBoard board = new GameBoard(20, 20, level);
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        assertDoesNotThrow(() -> board.draw(gc));
    }
}
