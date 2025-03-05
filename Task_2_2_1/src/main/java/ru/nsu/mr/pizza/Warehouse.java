package ru.nsu.mr.pizza;

import java.util.List;

/**
 * Represents a warehouse that stores cooked pizzas.
 * Pizzas are stored in a bounded queue and can be retrieved in batches.
 */
class Warehouse {
    private final BoundedQueue<Pizza> storage;

    /**
     * Constructs a Warehouse with the specified capacity.
     *
     * @param capacity the maximum number of pizzas that can be stored
     */
    public Warehouse(int capacity) {
        storage = new BoundedQueue<>(capacity);
    }

    /**
     * Stores the given pizza in the warehouse.
     * After storing, the pizza status is set to IN_WAREHOUSE.
     *
     * @param pizza the pizza to store
     * @throws InterruptedException if the thread is interrupted
     * while waiting to store the pizza
     */
    public void putPizza(Pizza pizza) throws InterruptedException {
        storage.put(pizza);
        pizza.setStatus(PizzaStatus.IN_WAREHOUSE);
        System.out.println(pizza);
    }

    /**
     * Retrieves up to {@code maxCount} pizzas from the warehouse.
     *
     * @param maxCount the maximum number of pizzas to retrieve
     * @return a list of retrieved pizzas
     * (maybe fewer than {@code maxCount} if not enough pizzas are available)
     * @throws InterruptedException if the thread is interrupted
     * while waiting for pizzas to become available
     */
    public List<Pizza> getPizzas(int maxCount) throws InterruptedException {
        return storage.takeUpTo(maxCount);
    }
}
