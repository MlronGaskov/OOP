package ru.nsu.gaskov.snake.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.Test;
import ru.nsu.gaskov.snake.models.Direction;
import ru.nsu.gaskov.snake.models.Snake;

/**
 * Tests.
 */
public class InputHandlerTest {

    private static class DummySnake extends Snake {
        private Direction direction;

        public DummySnake() {
            super(null, 0, 0);
        }

        @Override
        public void setDirection(Direction newDirection) {
            this.direction = newDirection;
        }

        public Direction getDirection() {
            return direction;
        }
    }

    @Test
    public void testHandleKeyPressed() {
        DummySnake dummySnake = new DummySnake();
        InputHandler handler = new InputHandler(dummySnake);

        KeyEvent upEvent = new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false
        );
        handler.handleKeyPressed(upEvent);
        Direction upDirection = dummySnake.getDirection();
        assertEquals(Direction.UP, upDirection);

        KeyEvent downEvent = new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false
        );
        handler.handleKeyPressed(downEvent);
        Direction downDirection = dummySnake.getDirection();
        assertEquals(Direction.DOWN, downDirection);

        KeyEvent leftEvent = new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false
        );
        handler.handleKeyPressed(leftEvent);
        Direction leftDirection = dummySnake.getDirection();
        assertEquals(Direction.LEFT, leftDirection);

        KeyEvent rightEvent = new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false
        );
        handler.handleKeyPressed(rightEvent);
        Direction rightDirection = dummySnake.getDirection();
        assertEquals(Direction.RIGHT, rightDirection);
    }
}
