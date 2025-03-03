package ru.nsu.mr.pizza;

import java.util.List;

/**
 * A delivery man that retrieves pizzas from the warehouse and delivers them.
 */
class DeliveryMan implements Runnable {
    private final int id;
    private final int trunkSize;
    private final Warehouse warehouse;
    private final Phone phone;

    /**
     * Constructs a DeliveryMan.
     *
     * @param id the delivery man identifier
     * @param trunkSize the maximum number of pizzas to deliver in one trip
     * @param warehouse the warehouse to retrieve pizzas from
     * @param phone the phone used to report delivery status
     */
    public DeliveryMan(int id, int trunkSize, Warehouse warehouse, Phone phone) {
        this.id = id;
        this.trunkSize = trunkSize;
        this.warehouse = warehouse;
        this.phone = phone;
    }

    /**
     * Delivers a single pizza by simulating the delivery delay.
     *
     * @param pizza the pizza to deliver
     * @throws InterruptedException if interrupted during delivery
     */
    private void deliver(Pizza pizza) throws InterruptedException {
        pizza.setStatus(PizzaStatus.DELIVERING);
        System.out.println("DeliveryMan " + id + " started delivering pizza ["
                + pizza.getId() + "]. Status: " + pizza.getStatus());
        Thread.sleep(pizza.getDeliveryTime());
        pizza.setStatus(PizzaStatus.DELIVERED);
        System.out.println("DeliveryMan " + id + " finished delivering pizza ["
                + pizza.getId() + "]. Status: " + pizza.getStatus());
    }

    /**
     * Continuously retrieves pizzas from the warehouse and delivers them.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                List<Pizza> pizzas = warehouse.getPizzas(trunkSize);
                for (Pizza pizza : pizzas) {
                    deliver(pizza);
                    phone.call("DeliveryMan " + id + ": Pizza [" + pizza.getId() + "] delivered successfully.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("DeliveryMan " + id + " interrupted.");
            }
        }
        System.out.println("DeliveryMan " + id + " stopped.");
    }
}
