package ru.nsu.mr.pizza;

import java.util.Random;
import java.util.function.Consumer;

/**
 * Generates random pizza orders by invoking a consumer with a randomly generated delivery time.
 * The time between orders is randomized around the provided average generation time.
 */
public class RandomOrderGenerator implements Runnable {
    private final Consumer<Integer> orderConsumer;
    private final int averageGenerationTime;
    private final int minDeliveryTime;
    private final int maxDeliveryTime;
    private final Random random = new Random();
    private volatile boolean running = true;

    /**
     * Constructs a RandomOrderGenerator.
     *
     * @param orderConsumer         the consumer to process generated delivery times
     * @param averageGenerationTime the average time (in ms) between order generations
     * @param minDeliveryTime       the minimum delivery time (in ms) for an order
     * @param maxDeliveryTime       the maximum delivery time (in ms) for an order
     */
    public RandomOrderGenerator(Consumer<Integer> orderConsumer,
                                int averageGenerationTime,
                                int minDeliveryTime,
                                int maxDeliveryTime) {
        this.orderConsumer = orderConsumer;
        this.averageGenerationTime = averageGenerationTime;
        this.minDeliveryTime = minDeliveryTime;
        this.maxDeliveryTime = maxDeliveryTime;
    }

    /**
     * Stops the random order generation.
     */
    public void stop() {
        running = false;
    }

    /**
     * Continuously generates random orders while running.
     * The thread sleeps for a random time
     * (based on the average generation time) between generating orders.
     */
    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            long offset = (long) ((random.nextDouble() - 0.5) * averageGenerationTime);
            long sleepTime = averageGenerationTime + offset;
            if (sleepTime < 0) {
                sleepTime = 0;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            int deliveryTime = minDeliveryTime + random.nextInt(maxDeliveryTime - minDeliveryTime);
            orderConsumer.accept(deliveryTime);
        }
    }
}
