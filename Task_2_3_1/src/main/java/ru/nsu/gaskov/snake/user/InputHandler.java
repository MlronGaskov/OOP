package ru.nsu.gaskov.snake.user;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.gaskov.snake.controller.Game;
import ru.nsu.gaskov.snake.models.Direction;

public class InputHandler {
    private final Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            game.getSnake().setDirection(Direction.UP);
        } else if (event.getCode() == KeyCode.DOWN) {
            game.getSnake().setDirection(Direction.DOWN);
        } else if (event.getCode() == KeyCode.LEFT) {
            game.getSnake().setDirection(Direction.LEFT);
        } else if (event.getCode() == KeyCode.RIGHT) {
            game.getSnake().setDirection(Direction.RIGHT);
        }
    }
}
