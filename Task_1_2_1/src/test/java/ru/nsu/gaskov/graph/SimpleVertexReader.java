package ru.nsu.gaskov.graph;

import java.util.Scanner;

public class SimpleVertexReader implements VertexReader<SimpleVertex> {

    @Override
    public SimpleVertex readFromScanner(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return new SimpleVertex(scanner.nextInt());
        } else {
            throw new IllegalArgumentException("Invalid input format.");
        }
    }
}
