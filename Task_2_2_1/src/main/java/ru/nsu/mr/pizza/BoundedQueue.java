package ru.nsu.mr.pizza;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A thread-safe bounded queue.
 *
 * @param <T> the type of elements held in this queue
 */
public class BoundedQueue<T> {
    private final Queue<T> queue;
    private final int capacity;
    private final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;

    /**
     * Constructs a new BoundedQueue with the given capacity.
     *
     * @param capacity the maximum number of elements the queue can hold
     */
    public BoundedQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    /**
     * Inserts an item into the queue, waiting if necessary.
     *
     * @param item the element to add
     * @throws InterruptedException if interrupted while waiting
     */
    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() >= capacity) {
                notFull.await();
            }
            queue.add(item);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves and removes an item from the queue, waiting if necessary.
     *
     * @return the head of the queue
     * @throws InterruptedException if interrupted while waiting
     */
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            T item = queue.poll();
            notFull.signalAll();
            return item;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves and removes up to n items from the queue, waiting if necessary.
     *
     * @param n the maximum number of items to retrieve
     * @return a list of items retrieved
     * @throws InterruptedException if interrupted while waiting
     */
    public List<T> takeUpTo(int n) throws InterruptedException {
        lock.lock();
        try {
            List<T> items = new ArrayList<>();
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            while (!queue.isEmpty() && items.size() < n) {
                items.add(queue.poll());
            }
            notFull.signalAll();
            return items;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        lock.lock();
        try {
            return queue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks if the queue is full.
     *
     * @return true if full, false otherwise
     */
    public boolean isFull() {
        lock.lock();
        try {
            return queue.size() >= capacity;
        } finally {
            lock.unlock();
        }
    }
}
