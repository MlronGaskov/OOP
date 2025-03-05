package ru.nsu.mr.pizza;

/**
 * Represents a pizza order with an identifier, delivery time, and current status.
 */
class Pizza {
    private final int id;
    private final int deliveryTime;
    private PizzaStatus status;

    /**
     * Constructs a Pizza with the specified id and delivery time.
     *
     * @param id the unique identifier of the pizza
     * @param deliveryTime the time (in milliseconds) required for delivery
     */
    public Pizza(int id, int deliveryTime) {
        this.id = id;
        this.deliveryTime = deliveryTime;
    }

    /**
     * Returns the unique identifier of this pizza.
     *
     * @return the pizza id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the delivery time of this pizza.
     *
     * @return the delivery time in milliseconds
     */
    public int getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * Returns the current status of the pizza.
     *
     * @return the pizza status
     */
    public PizzaStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the pizza.
     *
     * @param status the new status to set
     */
    public void setStatus(PizzaStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "pizza [" + this.id + "]. Status: " + status;
    }
}
