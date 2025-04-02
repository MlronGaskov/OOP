package ru.nsu.gaskov.snake.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;

/**
 * Game board for the snake game.
 */
public class GameBoard {
    private final int rows;
    private final int cols;
    private final List<Obstacle> obstacles;
    private final Snake userSnake;
    private final List<Enemy> enemies;
    private final List<Food> food;
    private final Level level;

    /**
     * Constructs the game board.
     */
    public GameBoard(int cols, int rows, Level level) {
        this.rows = rows;
        this.cols = cols;
        this.obstacles = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.food = new ArrayList<>();
        this.userSnake = new Snake(this, cols / 2, rows / 2);
        this.level = level;
        generateObstacles();
        generateEnemies();
        generateFood();
    }

    /**
     * Returns the number of columns.
     */
    public int getCols() {
        return cols;
    }

    /**
     * Returns the number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns a copy list of food.
     */
    public List<Food> getFood() {
        return new ArrayList<>(food);
    }

    /**
     * Returns a copy list of obstacles.
     */
    public List<Obstacle> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    /**
     * Returns the user-controlled snake.
     */
    public Snake getUserSnake() {
        return userSnake;
    }

    /**
     * Returns a list of all snakes.
     */
    public List<Snake> getSnakes() {
        List<Snake> snakes = new ArrayList<>(enemies.stream().map(Enemy::getSnake).toList());
        snakes.add(userSnake);
        return snakes;
    }

    /**
     * Updates the game board state.
     */
    public void update() {
        userSnake.move();
        for (Enemy enemy : enemies) {
            if (enemy.getSnake().isAlive()) {
                enemy.choseDirection();
                enemy.getSnake().move();
            }
        }
    }

    /**
     * Renders the game board.
     */
    public void draw(GraphicsContext gc) {
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();
        gc.clearRect(0, 0, width, height);
        double cellWidth = width / cols;
        double cellHeight = height / rows;
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(gc, cellWidth, cellHeight);
        }
        userSnake.draw(gc, cellWidth, cellHeight);
        for (Enemy enemy : enemies) {
            enemy.getSnake().draw(gc, cellWidth, cellHeight);
        }
        for (Food food : food) {
            food.draw(gc, cellWidth, cellHeight);
        }
    }

    /**
     * Removes food from the specified cell and generates new food.
     */
    public void eatFood(int x, int y) {
        food.removeIf(f -> f.x() == x && f.y() == y);
        generateFood();
    }

    /**
     * Checks if the coordinates are outside the board.
     */
    public boolean isNotInside(int x, int y) {
        return x < 0 || x >= cols || y < 0 || y >= rows;
    }

    /**
     * Checks if the cell is not occupied by an obstacle.
     */
    public boolean isNotObstacle(int x, int y) {
        for (Obstacle o : obstacles) {
            if (o.x() == x && o.y() == y) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the cell is not occupied by any snake.
     */
    public boolean isNotSnake(int x, int y) {
        for (Enemy enemy : enemies) {
            for (SnakeSegment segment : enemy.getSnake().getSegments()) {
                if (segment.x() == x && segment.y() == y) {
                    return false;
                }
            }
        }
        for (SnakeSegment segment : userSnake.getSegments()) {
            if (segment.x() == x && segment.y() == y) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the cell is free.
     */
    public boolean isFreeCell(int x, int y) {
        return !isNotInside(x, y) && isNotObstacle(x, y) && isNotSnake(x, y);
    }

    /**
     * Checks if the cell has food.
     */
    public boolean isFood(int x, int y) {
        for (Food f : food) {
            if (f.x() == x && f.y() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates obstacles on the board.
     */
    public void generateObstacles() {
        obstacles.clear();
        Random rand = new Random();
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (!isFreeCell(x, y) && !isFood(x, y)) {
                    continue;
                }
                if (rand.nextDouble() < level.getObstacleParameter()) {
                    obstacles.add(new Obstacle(x, y));
                }
            }
        }
    }

    /**
     * Generates enemies on the board.
     */
    public void generateEnemies() {
        enemies.clear();
        Random rand = new Random();
        for (int i = 0; i < level.getEnemiesCount(); i++) {
            int x, y;
            do {
                x = rand.nextInt(cols);
                y = rand.nextInt(rows);
            } while (!isFreeCell(x, y) || isFood(x, y));
            int enemyType = rand.nextInt(4);
            switch (enemyType) {
                case 1:
                    enemies.add(new SatiatedEnemy(this, x, y));
                    break;
                case 2:
                    enemies.add(new GreedyEnemy(this, x, y));
                    break;
                case 3:
                    enemies.add(new SmartEnemy(this, x, y));
                    break;
                default:
                    enemies.add(new StupidEnemy(this, x, y));
                    break;
            }
        }
    }

    /**
     * Generates food on the board.
     */
    public void generateFood() {
        Random rand = new Random();
        while (food.size() < level.getFoodCount()) {
            int x = rand.nextInt(cols);
            int y = rand.nextInt(rows);
            if (isFreeCell(x, y) && !isFood(x, y)) {
                food.add(new Food(x, y));
            }
        }
    }
}
