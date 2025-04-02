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

public class GameBoardTest {

    @BeforeAll
    public static void initJFX() throws Exception {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            if (!latch.await(5, TimeUnit.SECONDS)) {
                throw new Exception("JavaFX Platform failed to start.");
            }
        } catch (IllegalStateException ignored) {
        }
    }


    @Test
    public void testInitialization() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        final GameBoard[] boardHolder = new GameBoard[1];
        Platform.runLater(() -> {
            Level level = new Level();
            int cols = 20;
            int rows = 20;
            boardHolder[0] = new GameBoard(cols, rows, level);
            latch.countDown();
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Initialization did not complete in time");
        }
        GameBoard board = boardHolder[0];
        assertAll(
                () -> assertEquals(20, board.getCols()),
                () -> assertEquals(20, board.getRows()),
                () -> assertNotNull(board.getUserSnake()),
                () -> assertNotNull(board.getObstacles()),
                () -> assertNotNull(board.getFood()),
                () -> assertFalse(board.getFood().isEmpty())
        );
    }

    @Test
    public void testUpdate() throws Exception {
        Level level = new Level();
        GameBoard board = new GameBoard(20, 20, level);

        final int[] initialX = new int[1];
        final int[] initialY = new int[1];
        final int[] newX = new int[1];
        final int[] newY = new int[1];

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            initialX[0] = board.getUserSnake().getSegments().getFirst().x();
            initialY[0] = board.getUserSnake().getSegments().getFirst().y();
            board.update();
            newX[0] = board.getUserSnake().getSegments().getFirst().x();
            newY[0] = board.getUserSnake().getSegments().getFirst().y();
            latch.countDown();
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Update did not complete in time");
        }
        assertTrue((initialX[0] != newX[0]) || (initialY[0] != newY[0]));
    }

    @Test
    public void testDraw() throws Exception {
        Level level = new Level();
        level.increaseDifficulty();
        GameBoard board = new GameBoard(20, 20, level);
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> board.draw(gc));
            latch.countDown();
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Draw did not complete in time");
        }
    }
}
