package ru.nsu.gaskov.snake.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a segment of the snake.
 */
public record SnakeSegment(int x, int y) {

    /**
     * Draws the snake segment.
     */
    public void draw(GraphicsContext gc, Color color, double cellWidth, double cellHeight) {
        gc.setFill(color);
        gc.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
    }
}
