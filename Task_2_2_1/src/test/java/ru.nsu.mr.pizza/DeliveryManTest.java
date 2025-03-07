package ru.nsu.mr.pizza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class DeliveryManTest {

    @Test
    void testDeliveryManDeliversPizzas() throws InterruptedException {
        Warehouse warehouse = new Warehouse(3);
        List<Pizza> pizzaList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pizzaList.add(new Pizza(i, 10));
            warehouse.putPizza(pizzaList.getLast());
        }
        Phone phone = new Phone(e -> {});

        DeliveryMan deliveryMan = new DeliveryMan(1, 2, warehouse, phone);
        Thread deliveryThread = new Thread(deliveryMan);
        deliveryThread.start();

        Thread.sleep(100);
        deliveryThread.interrupt();
        deliveryThread.join(1000);

        assertEquals(PizzaStatus.DELIVERED, pizzaList.get(0).getStatus());
        assertEquals(PizzaStatus.DELIVERED, pizzaList.get(1).getStatus());
        assertEquals(PizzaStatus.DELIVERED, pizzaList.get(2).getStatus());
    }
}
