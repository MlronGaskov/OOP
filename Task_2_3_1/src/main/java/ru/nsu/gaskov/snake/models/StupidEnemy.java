package ru.nsu.gaskov.snake.models;

import java.util.Random;
import javafx.scene.paint.Color;

/**
 * Stupid enemy that chases random direction.
 */
public class StupidEnemy extends Enemy {
    private static final Random random = new Random();

    /**
     * Constructor.
     */
    public StupidEnemy(GameBoard gameBoard, int startX, int startY) {
        super(gameBoard, startX, startY);
        getSnake().setColor(Color.PINK);
    }

    /**
     * Chose direction.
     */
    @Override
    public void choseDirection() {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[random.nextInt(directions.length)];
        getSnake().setDirection(randomDirection);
    }
}
