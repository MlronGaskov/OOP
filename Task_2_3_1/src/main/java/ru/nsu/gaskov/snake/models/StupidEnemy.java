package ru.nsu.gaskov.snake.models;

import javafx.scene.paint.Color;

import java.util.Random;

public class StupidEnemy extends Enemy {
    private static final Random random = new Random();

    public StupidEnemy(GameBoard gameBoard, int startX, int startY) {
        super(gameBoard, startX, startY);
        getSnake().setColor(Color.PINK);
    }

    @Override
    public void choseDirection() {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[random.nextInt(directions.length)];
        getSnake().setDirection(randomDirection);
    }
}
