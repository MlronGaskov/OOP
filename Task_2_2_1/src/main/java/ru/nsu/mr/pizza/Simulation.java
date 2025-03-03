package ru.nsu.mr.pizza;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Simulation {
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

    public static void main(String[] args) {
        InputStream inputStream = Simulation.class.getClassLoader().getResourceAsStream("config.json");
        if (inputStream == null) {
            System.err.println("No config.json file.");
            return;
        }

        Gson gson = new Gson();
        Config config = gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), Config.class);

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
