package ru.nsu.mr.pizza;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Tests.
 */
public class PizzeriaTest {
    private int getPrivateIntField(Pizzeria pizzeria, String fieldName) throws Exception {
        Field field = Pizzeria.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.getInt(pizzeria);
    }

    @Test
    public void testAllOrdersDelivered() throws Exception {
        int workingTime = 1000;
        Pizzeria pizzeria = new Pizzeria(
                Arrays.asList(50, 50),
                Arrays.asList(1, 1),
                workingTime,
                10,
                10
        );

        Thread simulationThread = new Thread(() -> {
            try {
                pizzeria.start();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "PizzeriaSimulation");
        simulationThread.start();

        Random random = new Random();
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < workingTime - 100) {
            int deliveryTime = 100 + random.nextInt(200);
            pizzeria.orderPizza(deliveryTime);
            TimeUnit.MILLISECONDS.sleep(50);
        }

        simulationThread.join(3000);
        int ordersCount = getPrivateIntField(pizzeria, "ordersCount");
        int deliveredOrdersCount = getPrivateIntField(pizzeria, "deliveredOrdersCount");

        assertEquals(ordersCount, deliveredOrdersCount, "All orders should be delivered.");
    }

    @Test
    public void testOrderRejectionAfterStop() throws Exception {
        int workingTime = 500;
        Pizzeria pizzeria = new Pizzeria(
                List.of(50),
                List.of(1),
                workingTime,
                5,
                5
        );

        Thread simulationThread = new Thread(() -> {
            try {
                pizzeria.start();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "PizzeriaSimulation");
        simulationThread.start();
        simulationThread.join(2000);

        int ordersBefore = getPrivateIntField(pizzeria, "ordersCount");
        pizzeria.orderPizza(150);
        int ordersAfter = getPrivateIntField(pizzeria, "ordersCount");
        assertEquals(ordersBefore, ordersAfter);
    }
}
