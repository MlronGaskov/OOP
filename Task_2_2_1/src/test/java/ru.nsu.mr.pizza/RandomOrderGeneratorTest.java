package ru.nsu.mr.pizza;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
public class RandomOrderGeneratorTest {

    @Test
    public void testOrderGeneration() throws InterruptedException {
        List<Integer> generatedOrders = new ArrayList<>();
        RandomOrderGenerator generator = new RandomOrderGenerator(generatedOrders::add,
                100,
                50,
                200
        );

        Thread generatorThread = new Thread(generator);
        generatorThread.start();

        TimeUnit.MILLISECONDS.sleep(500);

        generator.stop();
        generatorThread.join(1000);

        assertFalse(generatedOrders.isEmpty(), "Generated orders list should not be empty");

        for (Integer deliveryTime : generatedOrders) {
            assertTrue(deliveryTime >= 50 && deliveryTime <= 200,
                    "Delivery time should be within the specified bounds");
        }
    }
}
