package ru.nsu.gaskov.snake.controller;

import ru.nsu.gaskov.snake.models.Level;
import ru.nsu.gaskov.snake.models.ScoreManager;
import ru.nsu.gaskov.snake.models.Food;
import ru.nsu.gaskov.snake.models.GameBoard;
import ru.nsu.gaskov.snake.models.Snake;
import ru.nsu.gaskov.snake.models.SnakeSegment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final GameBoard board;
    private final Snake userSnake;
    private final ScoreManager scoreManager;
    private final Level level;
    private final int initialRows;
    private final int initialCols;
    private List<Food> foodList;
    private GameState state;
    private static final int FOOD_COUNT = 5;

    public Game(int rows, int cols) {
        initialRows = rows;
        initialCols = cols;
        level = new Level();
        board = new GameBoard(rows, cols, level);
        userSnake = new Snake(cols / 2, rows / 2);
        scoreManager = new ScoreManager();
        startLevel();
    }

    private void moveSnake(Snake snake) {
        SnakeSegment head = snake.getHead();
        int newX = head.x() + snake.getDirection().dx;
        int newY = head.y() + snake.getDirection().dy;
        if (board.isNotInside(newX, newY) || snake.contains(newX, newY) || board.isObstacle(newX, newY)) {
            setState(GameState.LOST);
            return;
        }
        snake.addSegmentAtHead(new SnakeSegment(newX, newY));
        boolean eaten = false;
        for (int i = 0; i < foodList.size(); i++) {
            Food f = foodList.get(i);
            if (f.x() == newX && f.y() == newY) {
                eaten = true;
                scoreManager.incrementScore(f.score());
                foodList.remove(i);
                break;
            }
        }
        if (eaten) {
            spawnFoodIfNeeded();
        }
        else {
            snake.removeTail();
        }
    }

    public void update() throws InterruptedException {
        if (state != GameState.RUNNING) {
            Thread.sleep(2000);
            return;
        }
        moveSnake(userSnake);
        if (userSnake.getLength() >= level.getTargetScore()) {
            setState(GameState.WON);
        }
    }

    public GameBoard getBoard() {
        return board;
    }

    public Snake getSnake() {
        return userSnake;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public Level getLevel() {
        return level;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public GameState getState() {
        return state;
    }

    private void setState(GameState newState) {
        state = newState;
    }

    private void startLevel() {
        setState(GameState.RUNNING);
        userSnake.reset();
        scoreManager.resetScore();
        int startX = initialCols / 2;
        int startY = initialRows / 2;
        board.generateObstacles(startX, startY);
        foodList = new ArrayList<>();
        spawnFoodIfNeeded();
    }

    public void nextLevel() {
        level.increaseDifficulty();
        startLevel();
    }

    public void restartLevel() {
        startLevel();
    }

    private void spawnFoodIfNeeded() {
        while (foodList.size() < FOOD_COUNT) {
            placeOneFood();
        }
    }

    private void placeOneFood() {
        List<Point> freeCells = getFreeCells();
        if (freeCells.isEmpty()) {
            return;
        }
        Random rand = new Random();
        int index = rand.nextInt(freeCells.size());
        Point p = freeCells.get(index);
        foodList.add(new Food(p.x, p.y, 1));
    }

    private List<Point> getFreeCells() {
        List<Point> freeCells = new ArrayList<>();
        for (int x = 0; x < board.getCols(); x++) {
            for (int y = 0; y < board.getRows(); y++) {
                if (!userSnake.contains(x, y) && !board.isObstacle(x, y) && !containsFood(x, y)) {
                    freeCells.add(new Point(x, y));
                }
            }
        }
        return freeCells;
    }

    private boolean containsFood(int x, int y) {
        for (Food f : foodList) {
            if (f.x() == x && f.y() == y) {
                return true;
            }
        }
        return false;
    }
}
