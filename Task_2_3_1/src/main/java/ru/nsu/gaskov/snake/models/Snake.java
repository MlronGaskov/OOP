package ru.nsu.gaskov.snake.models;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final LinkedList<SnakeSegment> segments;
    private Direction direction;
    private boolean isDirectionChanged = false;
    private final int startX;
    private final int startY;

    public Snake(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        segments = new LinkedList<>();
        direction = Direction.RIGHT;
        segments.add(new SnakeSegment(startX, startY));
    }

    public void reset() {
        segments.clear();
        segments.add(new SnakeSegment(startX, startY));
        direction = Direction.RIGHT;
    }

    public void addSegmentAtHead(SnakeSegment segment) {
        segments.addFirst(segment);
        isDirectionChanged = false;
    }

    public void removeTail() {
        segments.removeLast();
    }

    public SnakeSegment getHead() {
        return segments.getFirst();
    }

    public int getLength() {
        return segments.size();
    }

    public boolean contains(int x, int y) {
        for (SnakeSegment segment : segments) {
            if (segment.x() == x && segment.y() == y) {
                return true;
            }
        }
        return false;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (this.direction.opposite() != direction && !isDirectionChanged) {
            this.direction = direction;
            isDirectionChanged = true;
        }
    }

    public List<SnakeSegment> getSegments() {
        return segments;
    }
}
