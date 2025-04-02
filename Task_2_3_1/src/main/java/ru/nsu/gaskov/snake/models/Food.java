package ru.nsu.gaskov.snake.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Food record.
 */
public record Food(int x, int y) {

    /**
     * Draws the food.
     */
    public void draw(GraphicsContext gc, double cellWidth, double cellHeight) {
        gc.setFill(Color.RED);
        gc.fillOval(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
    }
}
