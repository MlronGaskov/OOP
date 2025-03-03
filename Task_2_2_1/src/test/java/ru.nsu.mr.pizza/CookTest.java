package ru.nsu.mr.pizza;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Tests.
 */
class CookTest {
    @Test
    void testCookProcessesPizza() throws InterruptedException {
        BoundedQueue<Pizza> orderQueue = new BoundedQueue<>(1);
        Warehouse warehouse = new Warehouse(10);
        int cookId = 0;
        int cookingTime = 50;
        Cook cook = new Cook(cookId, cookingTime, orderQueue, warehouse);
        Thread cookThread = new Thread(cook);
        cookThread.start();

        Pizza pizza = new Pizza(1, -1);
        pizza.setStatus(PizzaStatus.ORDER_RECEIVED);
        orderQueue.put(pizza);

        Thread.sleep(200);

        cookThread.interrupt();
        cookThread.join(1000);

        List<Pizza> cookedPizzas = warehouse.getPizzas(1);
        assertEquals(1, cookedPizzas.size(), "Warehouse should contain one pizza");
        Pizza cookedPizza = cookedPizzas.getFirst();
        assertEquals(PizzaStatus.IN_WAREHOUSE, cookedPizza.getStatus(), "Pizza status should be COOKED");
    }
}
