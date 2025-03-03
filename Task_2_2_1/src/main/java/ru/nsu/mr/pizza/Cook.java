package ru.nsu.mr.pizza;

import java.util.logging.Logger;

/**
 * A cook that processes pizzas from the order queue and puts them into the warehouse.
 */
class Cook implements Runnable {
    private final int id;
    private final int cookingTime;
    private final BoundedQueue<Pizza> orderQueue;
    private final Warehouse warehouse;

    /**
     * Constructs a Cook.
     *
     * @param id the cook identifier
     * @param cookingTime the time (ms) required to cook a pizza
     * @param orderQueue the queue from which orders are taken
     * @param warehouse the warehouse to store cooked pizzas
     */
    public Cook(int id, int cookingTime, BoundedQueue<Pizza> orderQueue, Warehouse warehouse) {
        this.id = id;
        this.cookingTime = cookingTime;
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
    }

    /**
     * Cooks the given pizza by updating its status and simulating cooking delay.
     *
     * @param pizza the pizza to cook
     * @throws InterruptedException if interrupted during cooking
     */
    private void cook(Pizza pizza) throws InterruptedException {
        pizza.setStatus(PizzaStatus.COOKING);
        System.out.println("Cook " + id + " started cooking pizza ["
                + pizza.getId() + "]. Status: " + pizza.getStatus());
        Thread.sleep(cookingTime);
        pizza.setStatus(PizzaStatus.COOKED);
        System.out.println("Cook " + id + " finished cooking pizza ["
                + pizza.getId() + "]. Status: " + pizza.getStatus());
    }

    /**
     * Continuously takes pizzas from the order queue, cooks them, and puts them into the warehouse.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Pizza pizza = orderQueue.take();
                cook(pizza);
                warehouse.putPizza(pizza);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Cook " + id + " interrupted.");
            }
        }
        System.out.println("Cook " + id + " stopped.");
    }
}
