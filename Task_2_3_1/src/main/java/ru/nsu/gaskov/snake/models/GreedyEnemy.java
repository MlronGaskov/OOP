package ru.nsu.gaskov.snake.models;

import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * Greedy enemy that chases the nearest food.
 */
public class GreedyEnemy extends Enemy {
    private static final Random random = new Random();

    /**
     * Constructs a greedy enemy.
     */
    public GreedyEnemy(GameBoard gameBoard, int startX, int startY) {
        super(gameBoard, startX, startY);
        getSnake().setColor(Color.RED);
    }

    /**
     * Chooses the direction towards the nearest food ignoring obstacles and snakes.
     */
    @Override
    public void choseDirection() {
        Snake snake = getSnake();
        int headX = snake.getHead().x();
        int headY = snake.getHead().y();
        GameBoard board = getGameBoard();

        List<Food> foods = board.getFood();
        if (foods.isEmpty()) {
            snake.setDirection(Direction.values()[random.nextInt(Direction.values().length)]);
            return;
        }
        Food nearestFood = foods.getFirst();
        int minDist = Math.abs(nearestFood.x() - headX) + Math.abs(nearestFood.y() - headY);
        for (Food f : foods) {
            int d = Math.abs(f.x() - headX) + Math.abs(f.y() - headY);
            if (d < minDist) {
                minDist = d;
                nearestFood = f;
            }
        }
        int dx = nearestFood.x() - headX;
        int dy = nearestFood.y() - headY;

        Direction horizontal = (dx > 0) ? Direction.RIGHT : Direction.LEFT;
        Direction vertical = (dy > 0) ? Direction.DOWN : Direction.UP;
        if (horizontal == snake.getDirection().opposite()) {
            snake.setDirection(vertical);
        } else if (vertical == snake.getDirection().opposite() || dy == 0) {
            snake.setDirection(horizontal);
        } else if (dx == 0) {
            snake.setDirection(vertical);
        } else {
            snake.setDirection(random.nextBoolean() ? horizontal : vertical);
        }
    }
}
