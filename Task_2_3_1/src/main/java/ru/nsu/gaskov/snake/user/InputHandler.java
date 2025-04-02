package ru.nsu.gaskov.snake.user;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.gaskov.snake.models.Direction;
import ru.nsu.gaskov.snake.models.Snake;

/**
 * Handles keyboard input for the snake movement.
 */
public class InputHandler {
    private Snake snake;

    /**
     * Creates an input handler for the specified snake.
     */
    public InputHandler(Snake snake) {
        this.snake = snake;
    }

    /**
     * Sets snake to move.
     */
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    /**
     * Processes a key pressed event and updates the snake's direction.
     */
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            snake.setDirection(Direction.UP);
        } else if (event.getCode() == KeyCode.DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (event.getCode() == KeyCode.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (event.getCode() == KeyCode.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        }
    }
}
