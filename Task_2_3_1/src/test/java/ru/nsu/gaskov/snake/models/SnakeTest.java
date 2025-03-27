package ru.nsu.gaskov.snake.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {

    @Test
    public void testInitialization() {
        int startX = 5;
        int startY = 5;
        Snake snake = new Snake(startX, startY);
        assertEquals(1, snake.getLength());
        SnakeSegment head = snake.getHead();
        assertEquals(startX, head.x());
        assertEquals(startY, head.y());
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    public void testAddSegmentAtHead() {
        int startX = 5;
        int startY = 5;
        Snake snake = new Snake(startX, startY);
        SnakeSegment newHead = new SnakeSegment(startX + 1, startY);
        snake.addSegmentAtHead(newHead);
        assertEquals(newHead, snake.getHead());
        assertEquals(2, snake.getLength());
    }

    @Test
    public void testRemoveTail() {
        int startX = 5;
        int startY = 5;
        Snake snake = new Snake(startX, startY);
        SnakeSegment segment2 = new SnakeSegment(startX + 1, startY);
        SnakeSegment segment3 = new SnakeSegment(startX + 2, startY);
        snake.addSegmentAtHead(segment2);
        snake.addSegmentAtHead(segment3);
        assertEquals(3, snake.getLength());
        snake.removeTail();
        assertEquals(2, snake.getLength());
        assertFalse(snake.contains(startX, startY));
    }

    @Test
    public void testReset() {
        int startX = 5;
        int startY = 5;
        Snake snake = new Snake(startX, startY);
        snake.addSegmentAtHead(new SnakeSegment(startX + 1, startY));
        snake.setDirection(Direction.UP);
        assertEquals(2, snake.getLength());
        snake.reset();
        assertEquals(1, snake.getLength());
        SnakeSegment head = snake.getHead();
        assertEquals(startX, head.x());
        assertEquals(startY, head.y());
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    public void testContains() {
        int startX = 5;
        int startY = 5;
        Snake snake = new Snake(startX, startY);
        assertTrue(snake.contains(startX, startY));
        assertFalse(snake.contains(startX + 1, startY));
        SnakeSegment newSegment = new SnakeSegment(startX + 1, startY);
        snake.addSegmentAtHead(newSegment);
        assertTrue(snake.contains(startX + 1, startY));
    }

    @Test
    public void testSetDirectionNotOpposite() {
        int startX = 5;
        int startY = 5;
        Snake snake = new Snake(startX, startY);
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection());
    }

    @Test
    public void testSetDirectionOpposite() {
        int startX = 5;
        int startY = 5;
        Snake snake = new Snake(startX, startY);
        snake.setDirection(Direction.LEFT);
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    public void testDirectionChangeFlag() {
        int startX = 5;
        int startY = 5;
        Snake snake = new Snake(startX, startY);
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection());
        snake.setDirection(Direction.DOWN);
        assertEquals(Direction.UP, snake.getDirection());
        snake.addSegmentAtHead(new SnakeSegment(startX, startY - 1));
        snake.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, snake.getDirection());
    }
}
