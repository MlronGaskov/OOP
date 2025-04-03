package ru.nsu.gaskov.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.gaskov.snake.models.Food;
import ru.nsu.gaskov.snake.models.GameBoard;
import ru.nsu.gaskov.snake.models.Obstacle;
import ru.nsu.gaskov.snake.models.Snake;
import ru.nsu.gaskov.snake.models.SnakeSegment;

/**
 * Class responsible for rendering the game board.
 */
public class BoardRenderer {
    private final GraphicsContext gc;

    public BoardRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void render(GameBoard gameBoard) {
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();

        gc.clearRect(0, 0, width, height);

        int cols = gameBoard.getCols();
        int rows = gameBoard.getRows();
        double cellWidth = width / cols;
        double cellHeight = height / rows;

        gc.setFill(Color.GRAY);
        for (Obstacle o : gameBoard.getObstacles()) {
            gc.fillRect(o.x() * cellWidth, o.y() * cellHeight, cellWidth, cellHeight);
        }

        for (Snake snake : gameBoard.getSnakes()) {
            gc.setFill(snake.getColor());
            for (SnakeSegment segment : snake.getSegments()) {
                gc.fillRect(segment.x() * cellWidth, segment.y() * cellHeight, cellWidth, cellHeight);
            }
        }

        gc.setFill(Color.RED);
        for (Food f : gameBoard.getFood()) {
            gc.fillOval(f.x() * cellWidth, f.y() * cellHeight, cellWidth, cellHeight);
        }
    }
}