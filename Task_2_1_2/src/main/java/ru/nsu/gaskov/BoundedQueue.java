package ru.nsu.gaskov;

import java.util.LinkedList;

public class BoundedQueue<T> {
    private final LinkedList<T> queue = new LinkedList<>();
    private final int capacity;

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() >= capacity) {
            wait();
        }
        queue.addLast(item);
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.removeFirst();
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }
}
