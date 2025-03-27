package ru.nsu.gaskov.snake.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {
    private final int rows;
    private final int cols;
    private final Level level;

    private final List<Obstacle> obstacles;

    public GameBoard(int rows, int cols, Level level) {
        this.rows = rows;
        this.cols = cols;
        this.level = level;
        this.obstacles = new ArrayList<>();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isNotInside(int x, int y) {
        return x < 0 || x >= cols || y < 0 || y >= rows;
    }

    public boolean isObstacle(int x, int y) {
        for (Obstacle o : obstacles) {
            if (o.x() == x && o.y() == y) {
                return true;
            }
        }
        return false;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void generateObstacles(int startX, int startY) {
        obstacles.clear();
        Random rand = new Random();

        int[][][] shapes = {
                { {0, 0} },
                { {0, 0}, {1, 0} },
                { {0, 0}, {1, 0}, {2, 0} },
                { {0, 0}, {1, 0}, {0, 1}, {1, 1} },
                { {0, 0}, {0, 1} },
                { {0, 0}, {0, 1}, {0, 2} }
        };

        for (int x = 0; x < getCols(); x++) {
            for (int y = 0; y < getRows(); y++) {
                if (rand.nextDouble() > level.getObstacleParameter()) {
                    continue;
                }

                int shapeIndex = rand.nextInt(shapes.length);
                int[][] shape = shapes[shapeIndex];

                boolean canPlace = true;

                for (int[] offset : shape) {
                    int cellX = x + offset[0];
                    int cellY = y + offset[1];

                    if (isNotInside(cellX, cellY)) {
                        canPlace = false;
                        break;
                    }
                    int dxStart = cellX - startX;
                    int dyStart = cellY - startY;
                    if (dxStart * dxStart + dyStart * dyStart < 16) {
                        canPlace = false;
                        break;
                    }
                    for (Obstacle o : obstacles) {
                        int ddx = cellX - o.x();
                        int ddy = cellY - o.y();
                        if (ddx * ddx + ddy * ddy < 16) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (!canPlace) {
                        break;
                    }
                }

                if (canPlace) {
                    for (int[] offset : shape) {
                        obstacles.add(new Obstacle(x + offset[0], y + offset[1]));
                    }
                }
            }
        }
    }
}
