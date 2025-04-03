package ru.nsu.gaskov.snake.controller;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import ru.nsu.gaskov.snake.models.GameBoard;
import ru.nsu.gaskov.snake.models.Level;
import ru.nsu.gaskov.snake.user.InputHandler;
import ru.nsu.gaskov.snake.view.BoardRenderer;

/**
 * Combines game logic and controller for the snake game.
 */
public class GameController {
    @FXML
    private Canvas gameCanvas;

    @FXML
    private Label scoreLabel;

    private GameState state;
    private final Level level = new Level();
    private GameBoard gameBoard;
    private GraphicsContext gc;
    private InputHandler inputHandler;
    private BoardRenderer renderer;
    private final IntegerProperty scoreProperty = new SimpleIntegerProperty();

    /**
     * Initializes the game, sets up input handling and starts the game loop.
     */
    @FXML
    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        startLevel();
        inputHandler = new InputHandler(gameBoard.getUserSnake());
        renderer = new BoardRenderer(gameCanvas.getGraphicsContext2D());
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
                if (now - lastUpdate >= level.getUpdateSpeedNano()) {
                    update();
                    scoreLabel.textProperty().bind(Bindings.createStringBinding(
                            () -> "Score: " + scoreProperty.get() + "/" + level.getTargetScore(),
                            scoreProperty));
                    if (state == GameState.WON) {
                        nextLevel();
                    } else if (state == GameState.LOST) {
                        restartLevel();
                    }
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Updates the game state and redraws the game board.
     */
    public void update() {
        gameBoard.update();
        renderer.render(gameBoard);
        scoreProperty.set(gameBoard.getUserSnake().getSegments().size());
        if (scoreProperty.get() >= level.getTargetScore()) {
            state = GameState.WON;
        } else if (!gameBoard.getUserSnake().isAlive()) {
            state = GameState.LOST;
        }
    }

    /**
     * Starts or restarts the level.
     */
    private void startLevel() {
        state = GameState.RUNNING;
        int initialRows = 9 * 3;
        int initialCols = 16 * 3;
        gameBoard = new GameBoard(initialCols, initialRows, level);
    }

    /**
     * Advances the game to the next level.
     */
    public void nextLevel() {
        level.increaseDifficulty();
        startLevel();
        inputHandler.setSnake(gameBoard.getUserSnake());
    }

    /**
     * Restarts the current level.
     */
    public void restartLevel() {
        startLevel();
        inputHandler.setSnake(gameBoard.getUserSnake());
    }
}
