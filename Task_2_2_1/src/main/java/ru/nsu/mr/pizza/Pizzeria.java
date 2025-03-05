package ru.nsu.mr.pizza;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pizzeria that processes pizza orders by using a queue,
 * cooks them using a pool of cooks, and delivers them using a pool of delivery men.
 */
public class Pizzeria {
    private final BoundedQueue<Pizza> orderQueue;
    private final List<Thread> cookThreads = new ArrayList<>();
    private final List<Thread> deliveryThreads = new ArrayList<>();
    private volatile int ordersCount = 0;
    private volatile int deliveredOrdersCount = 0;
    private volatile boolean acceptingOrders = true;
    private final int workingTime;

    /**
     * Constructs a new Pizzeria.
     *
     * @param cookingTimes      list of cooking times for each cook (in milliseconds)
     * @param truckSizes        list of trunk sizes for each delivery man
     * @param workingTime       duration (in milliseconds) during which orders are accepted
     * @param orderQueueCapacity capacity of the order queue
     * @param warehouseCapacity capacity of the warehouse
     */
    public Pizzeria(List<Integer> cookingTimes,
                    List<Integer> truckSizes,
                    int workingTime,
                    int orderQueueCapacity,
                    int warehouseCapacity) {
        this.orderQueue = new BoundedQueue<>(orderQueueCapacity);
        Warehouse warehouse = new Warehouse(warehouseCapacity);
        this.workingTime = workingTime;

        for (int i = 0; i < cookingTimes.size(); ++i) {
            Cook cook = new Cook(i, cookingTimes.get(i), orderQueue, warehouse);
            Thread t = new Thread(cook, "Cook-" + i);
            cookThreads.add(t);
        }

        for (int i = 0; i < truckSizes.size(); ++i) {
            Phone phone = new Phone(e -> acceptOrderDelivered());
            DeliveryMan dm = new DeliveryMan(i, truckSizes.get(i), warehouse, phone);
            Thread t = new Thread(dm, "DeliveryMan-" + i);
            deliveryThreads.add(t);
        }
    }

    /**
     * Starts the pizzeria by launching cook and delivery threads,
     * stops accepting orders after the working time, and waits until all orders are delivered.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void start() throws InterruptedException {
        System.out.println("Starting pizzeria. Launching cook and delivery threads.");
        for (Thread t : cookThreads) {
            t.start();
        }
        for (Thread t : deliveryThreads) {
            t.start();
        }

        Thread.sleep(workingTime);
        acceptingOrders = false;
        System.out.println("Pizzeria stopped accepting new orders.");

        synchronized(this) {
            while (ordersCount > deliveredOrdersCount) {
                wait();
            }
        }

        System.out.println("All orders delivered. Stopping cook and delivery threads.");
        for (Thread t : cookThreads) {
            t.interrupt();
        }
        for (Thread t : deliveryThreads) {
            t.interrupt();
        }
    }

    /**
     * Registers a new pizza order with the given delivery time.
     *
     * @param deliveryTime the delivery time (in milliseconds) for the order
     */
    public synchronized void orderPizza(int deliveryTime) {
        if (!acceptingOrders) {
            System.out.println("Orders are no longer accepted. Order rejected.");
            return;
        }
        int id = ordersCount;
        Pizza pizza = new Pizza(id, deliveryTime);
        pizza.setStatus(PizzaStatus.ORDER_RECEIVED);
        System.out.printf("Order accepted: " + pizza);
        try {
            orderQueue.put(pizza);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        ordersCount++;
    }

    /**
     * Increases the count of delivered orders and notifies waiting threads if all orders are delivered.
     */
    private synchronized void acceptOrderDelivered() {
        deliveredOrdersCount++;
        System.out.println("Order delivered. Total delivered: " + deliveredOrdersCount);
        if (deliveredOrdersCount >= ordersCount) {
            notifyAll();
        }
    }
}
