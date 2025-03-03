package ru.nsu.gaskov;

public class Pizza {
    private final int id;
    private final int deliveryTime;
    volatile PizzaStatus status;

    public Pizza(int id, int deliveryTime) {
        this.id = id;
        this.deliveryTime = deliveryTime;
        status = PizzaStatus.ORDER_RECEIVED;
    }

    public int getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    synchronized public void setStatus(PizzaStatus newStatus) {
        this.status = newStatus;
    }
}
