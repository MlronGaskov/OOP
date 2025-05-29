package ru.nsu.mr.pizza;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Simulation class for running the pizzeria simulation.
 * This class reads configuration parameters from a JSON file and starts the simulation.
 */
public class Simulation {

    /**
     * Holds configuration parameters for the simulation.
     */
    static class Config {
        List<Integer> cookingTimes;
        List<Integer> truckSizes;
        int workingTime;
        int orderQueueCapacity;
        int warehouseCapacity;
        int orderGenerationAverage;
        int minDeliveryTime;
        int maxDeliveryTime;
    }

    /**
     * The entry point of the simulation.
     *
     * @param args the command line arguments (not used)
     * @throws IOException if an I/O error occurs while reading the configuration
     */
    public static void main(String[] args) throws IOException {
        try (InputStream inputStream = Simulation.class.getClassLoader()
                .getResourceAsStream("config.json")) {
            if (inputStream == null) {
                System.err.println("No config.json file.");
                return;
            }
            try (InputStreamReader isr = new InputStreamReader(inputStream);
                 JsonReader jsonReader = new JsonReader(isr)) {
                Gson gson = new Gson();
                Config config = gson.fromJson(jsonReader, Config.class);

                Pizzeria pizzeria = new Pizzeria(
                        config.cookingTimes,
                        config.truckSizes,
                        config.workingTime,
                        config.orderQueueCapacity,
                        config.warehouseCapacity
                );

                RandomOrderGenerator orderGenerator = new RandomOrderGenerator(
                        pizzeria::orderPizza,
                        config.orderGenerationAverage,
                        config.minDeliveryTime,
                        config.maxDeliveryTime
                );

                Thread pizzeriaThread = new Thread(() -> {
                    try {
                        pizzeria.start();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "PizzeriaThread");

                Thread generatorThread = new Thread(orderGenerator, "OrderGeneratorThread");

                pizzeriaThread.start();
                generatorThread.start();

                try {
                    pizzeriaThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                orderGenerator.stop();
                try {
                    generatorThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
