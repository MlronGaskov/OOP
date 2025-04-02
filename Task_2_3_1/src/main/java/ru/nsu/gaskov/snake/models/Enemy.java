package ru.nsu.gaskov.snake.models;

/**
 * Abstract enemy class.
 */
public abstract class Enemy {
    private final Snake snake;
    private final GameBoard gameBoard;

    /**
     * Creates an enemy.
     */
    public Enemy(GameBoard gameBoard, int startX, int startY) {
        this.gameBoard = gameBoard;
        this.snake = new Snake(gameBoard, startX, startY);
    }

    /**
     * Returns the enemy snake.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Returns the game board.
     */
    protected GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Chooses the enemy direction.
     */
    public abstract void choseDirection();
}
