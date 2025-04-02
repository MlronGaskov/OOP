package ru.nsu.gaskov.snake.models;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 * An enemy that chooses a random safe direction.
 */
public class SatiatedEnemy extends Enemy {
    private static final Random random = new Random();

    /**
     * Creates a satiated enemy.
     */
    public SatiatedEnemy(GameBoard gameBoard, int startX, int startY) {
        super(gameBoard, startX, startY);
        getSnake().setColor(Color.ORANGE);
    }

    /**
     * Chooses a random safe direction.
     */
    @Override
    public void choseDirection() {
        Snake snake = getSnake();
        int headX = snake.getSegments().getFirst().x();
        int headY = snake.getSegments().getFirst().y();
        GameBoard board = getGameBoard();

        List<Direction> safeDirections = new ArrayList<>();
        for (Direction d : Direction.values()) {
            if (d == snake.getDirection().opposite()) {
                continue;
            }
            int nx = headX + d.dx;
            int ny = headY + d.dy;
            if (board.isFreeCell(nx, ny)) {
                safeDirections.add(d);
            }
        }
        if (!safeDirections.isEmpty()) {
            Direction chosen = safeDirections.get(random.nextInt(safeDirections.size()));
            snake.setDirection(chosen);
        } else {
            snake.setDirection(Direction.values()[random.nextInt(Direction.values().length)]);
        }
    }
}
