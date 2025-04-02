package ru.nsu.gaskov.snake.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GameControllerTest {

    @BeforeAll
    public static void initJFX() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new Exception("JavaFX Platform failed to start.");
        }
    }

    @Test
    public void testInitializeDoesNotThrowException() throws Exception {
        GameController controller = new GameController();

        // Устанавливаем необходимые поля через рефлексию
        Field gameCanvasField = GameController.class.getDeclaredField("gameCanvas");
        gameCanvasField.setAccessible(true);
        gameCanvasField.set(controller, new Canvas(400, 400));

        Field scoreLabelField = GameController.class.getDeclaredField("scoreLabel");
        scoreLabelField.setAccessible(true);
        scoreLabelField.set(controller, new Label());

        // Выполнение инициализации в потоке JavaFX
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            assertDoesNotThrow(controller::initialize);
            latch.countDown();
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Initialization did not complete in time");
        }
    }
}
