package ru.nsu.gaskov.snake.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import ru.nsu.gaskov.snake.user.InputHandler;
import ru.nsu.gaskov.snake.view.BoardRenderer;

public class GameController {
    @FXML
    private Canvas gameCanvas;

    @FXML
    private Label scoreLabel;

    private Game game;
    private BoardRenderer renderer;
    private InputHandler inputHandler;

    public void initialize() {
        game = new Game(9 * 3, 16 * 3);
        renderer = new BoardRenderer(gameCanvas.getGraphicsContext2D(), game);
        inputHandler = new InputHandler(game);

        gameCanvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed((KeyEvent event) ->
                        inputHandler.handleKeyPressed(event));
            }
        });

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= game.getLevel().getSpeedNano()) {
                    if (game.getState() == GameState.RUNNING) {
                        try {
                            game.update();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    renderer.render();
                    scoreLabel.setText("Score: " + game.getScoreManager().getScore()
                            + "/" + game.getLevel().getTargetScore());
                    if (game.getState() == GameState.WON) {
                        game.nextLevel();
                    } else if (game.getState() == GameState.LOST) {
                        game.restartLevel();
                    }
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }
}
