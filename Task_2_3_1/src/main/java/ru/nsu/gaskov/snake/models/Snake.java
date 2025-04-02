package ru.nsu.gaskov.snake.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the snake in the game.
 */
public class Snake {
    private final LinkedList<SnakeSegment> segments = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private boolean isDirectionChanged = false;
    private boolean isAlive = true;
    private final GameBoard gameBoard;
    private Color color = Color.BLACK;

    /**
     * Constructs a snake with the specified starting position.
     */
    public Snake(GameBoard gameBoard, int startX, int startY) {
        this.gameBoard = gameBoard;
        segments.add(new SnakeSegment(startX, startY));
    }

    /**
     * Returns a copy of the snake's segments.
     */
    public List<SnakeSegment> getSegments() {
        return new ArrayList<>(segments);
    }

    /**
     * Returns the current movement direction of the snake.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Returns whether the snake is alive.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the snake's color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Updates the snake's direction if allowed.
     */
    public void setDirection(Direction direction) {
        if (this.direction == null || this.direction.opposite() != direction && !isDirectionChanged) {
            this.direction = direction;
            isDirectionChanged = true;
        }
    }

    /**
     * Moves the snake one step forward.
     */
    public void move() {
        isDirectionChanged = false;
        if (!isAlive) {
            throw new IllegalStateException("Dead snakes can not move.");
        }
        int newHeadX = segments.getFirst().x() + direction.dx;
        int newHeadY = segments.getFirst().y() + direction.dy;
        if (!gameBoard.isFreeCell(newHeadX, newHeadY)) {
            isAlive = false;
            segments.clear();
            return;
        }
        segments.addFirst(new SnakeSegment(newHeadX, newHeadY));
        if (gameBoard.isFood(newHeadX, newHeadY)) {
            gameBoard.eatFood(newHeadX, newHeadY);
        } else {
            segments.removeLast();
        }
    }

    /**
     * Draws the snake on the provided graphics context.
     */
    public void draw(GraphicsContext gc, double cellWidth, double cellHeight) {
        for (SnakeSegment segment : segments) {
            segment.draw(gc, color, cellWidth, cellHeight);
        }
    }
}
