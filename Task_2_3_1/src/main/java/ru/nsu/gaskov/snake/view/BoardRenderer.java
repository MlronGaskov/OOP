package ru.nsu.gaskov.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.gaskov.snake.controller.Game;
import ru.nsu.gaskov.snake.models.Food;
import ru.nsu.gaskov.snake.models.Obstacle;
import ru.nsu.gaskov.snake.models.SnakeSegment;

public class BoardRenderer {
    private final GraphicsContext gc;
    private final Game game;

    public BoardRenderer(GraphicsContext gc, Game game) {
        this.gc = gc;
        this.game = game;
    }

    public void render() {
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();

        gc.clearRect(0, 0, width, height);

        int cols = game.getBoard().getCols();
        int rows = game.getBoard().getRows();
        double cellWidth = width / cols;
        double cellHeight = height / rows;

        gc.setFill(Color.GRAY);
        for (Obstacle o : game.getBoard().getObstacles()) {
            gc.fillRect(o.x() * cellWidth, o.y() * cellHeight,
                    cellWidth, cellHeight);
        }

        gc.setFill(Color.GREEN);
        for (SnakeSegment segment : game.getSnake().getSegments()) {
            gc.fillRect(segment.x() * cellWidth, segment.y() * cellHeight,
                    cellWidth, cellHeight);
        }

        gc.setFill(Color.RED);
        for (Food f : game.getFoodList()) {
            gc.fillOval(f.x() * cellWidth, f.y() * cellHeight,
                    cellWidth, cellHeight);
        }
    }
}
