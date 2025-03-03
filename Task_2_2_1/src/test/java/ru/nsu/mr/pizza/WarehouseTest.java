package ru.nsu.mr.pizza;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Tests.
 */
public class WarehouseTest {

    @Test
    public void testPutAndGetPizza() throws InterruptedException {
        Warehouse warehouse = new Warehouse(5);
        Pizza pizza = new Pizza(1, 3000);
        pizza.setStatus(PizzaStatus.COOKED);

        warehouse.putPizza(pizza);
        assertEquals(PizzaStatus.IN_WAREHOUSE, pizza.getStatus());

        List<Pizza> retrieved = warehouse.getPizzas(1);
        assertEquals(1, retrieved.size());
        assertEquals(pizza.getId(), retrieved.getFirst().getId());
    }

    @Test
    public void testMultiplePizzas() throws InterruptedException {
        Warehouse warehouse = new Warehouse(10);
        Pizza pizza1 = new Pizza(1, 3000);
        Pizza pizza2 = new Pizza(2, 2500);
        pizza1.setStatus(PizzaStatus.COOKED);
        pizza2.setStatus(PizzaStatus.COOKED);

        warehouse.putPizza(pizza1);
        warehouse.putPizza(pizza2);

        List<Pizza> retrieved = warehouse.getPizzas(4);
        assertEquals(2, retrieved.size());
        assertTrue(retrieved.stream().anyMatch(p -> p.getId() == 1));
        assertTrue(retrieved.stream().anyMatch(p -> p.getId() == 2));
    }
}
