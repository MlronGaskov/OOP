package ru.nsu.gaskov;

import java.util.List;

public class Warehouse {
    private final BoundedQueue<Pizza> warehouse;

    public Warehouse(int capacity) {
        warehouse = new BoundedQueue<>(capacity);
    }

    synchronized public void put(Pizza pizza) throws InterruptedException {
        this.warehouse.put(pizza);
    }

    synchronized public List<Pizza> get(int trunkSize) {
        List<Pizza>
    }
}
