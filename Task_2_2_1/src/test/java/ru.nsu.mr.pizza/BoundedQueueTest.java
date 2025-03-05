package ru.nsu.mr.pizza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Tests.
 */
class BoundedQueueTest {

    @Test
    void testPutAndTake() throws InterruptedException {
        BoundedQueue<Integer> queue = new BoundedQueue<>(2);
        queue.put(1);
        queue.put(2);
        assertTrue(queue.isFull());
        int first = queue.take();
        assertEquals(1, first);
        int second = queue.take();
        assertEquals(2, second);
        assertTrue(queue.isEmpty());
    }

    @Test
    void testTakeUpTo() throws InterruptedException {
        BoundedQueue<Integer> queue = new BoundedQueue<>(5);
        for (int i = 0; i < 5; i++) {
            queue.put(i);
        }
        List<Integer> items = queue.takeUpTo(3);
        assertEquals(3, items.size());
        assertFalse(queue.isEmpty());
        items = queue.takeUpTo(5);
        assertEquals(2, items.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testBlockingBehavior() throws InterruptedException, ExecutionException, TimeoutException {
        BoundedQueue<Integer> queue = new BoundedQueue<>(1);
        queue.put(10);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                queue.put(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread.sleep(100);
        assertFalse(future.isDone());
        int taken = queue.take();
        future.get(1, TimeUnit.SECONDS);
        int newTaken = queue.take();
        assertEquals(20, newTaken);
        executor.shutdown();
    }
}
