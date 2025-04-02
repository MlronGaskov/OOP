package ru.nsu.gaskov.snake.models;

import java.util.Queue;
import java.util.LinkedList;
import javafx.scene.paint.Color;

/**
 * An enemy that uses BFS to find the nearest food.
 */
public class SmartEnemy extends Enemy {

    /**
     * Creates a smart enemy.
     */
    public SmartEnemy(GameBoard gameBoard, int startX, int startY) {
        super(gameBoard, startX, startY);
        getSnake().setColor(Color.BLUE);
    }

    /**
     * Chooses the direction towards the nearest food using BFS.
     */
    @Override
    public void choseDirection() {
        Snake snake = getSnake();
        int headX = snake.getSegments().getFirst().x();
        int headY = snake.getSegments().getFirst().y();
        GameBoard board = getGameBoard();
        int cols = board.getCols();
        int rows = board.getRows();

        boolean[][] visited = new boolean[cols][rows];

        record Node(int x, int y, Direction firstDirection) {
        }

        Queue<Node> queue = new LinkedList<>();
        visited[headX][headY] = true;

        for (Direction d : Direction.values()) {
            if (d == snake.getDirection().opposite()) {
                continue;
            }
            int nx = headX + d.dx;
            int ny = headY + d.dy;
            if (board.isFreeCell(nx, ny) && !visited[nx][ny]) {
                visited[nx][ny] = true;
                queue.add(new Node(nx, ny, d));
            }
        }

        Direction chosenDirection = snake.getDirection();
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (board.isFood(current.x, current.y)) {
                chosenDirection = current.firstDirection;
                break;
            }
            for (Direction d : Direction.values()) {
                int nx = current.x + d.dx;
                int ny = current.y + d.dy;
                if (board.isFreeCell(nx, ny) && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new Node(nx, ny, current.firstDirection));
                }
            }
        }
        snake.setDirection(chosenDirection);
    }
}
