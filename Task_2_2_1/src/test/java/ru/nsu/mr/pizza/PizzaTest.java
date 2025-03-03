package ru.nsu.mr.pizza;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests.
 */
public class PizzaTest {

    @Test
    public void testPizzaCreationAndAccessors() {
        int id = 1;
        int deliveryTime = 3000;
        Pizza pizza = new Pizza(id, deliveryTime);

        assertEquals(id, pizza.getId(), "Pizza id should match");
        assertEquals(deliveryTime, pizza.getDeliveryTime(), "Delivery time should match");
        assertNull(pizza.getStatus(), "Initial status should be null");

        pizza.setStatus(PizzaStatus.ORDER_RECEIVED);
        assertEquals(PizzaStatus.ORDER_RECEIVED, pizza.getStatus());
    }
}
