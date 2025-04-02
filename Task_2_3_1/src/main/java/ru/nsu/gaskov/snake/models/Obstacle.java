package ru.nsu.gaskov.snake.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents an obstacle on the game board.
 */
public record Obstacle(int x, int y) {

    /**
     * Draws the obstacle.
     */
    public void draw(GraphicsContext gc, double cellWidth, double cellHeight) {
        gc.setFill(Color.GREY);
        gc.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
    }
}
