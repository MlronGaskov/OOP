package ru.nsu.gaskov.graph;

import java.util.Scanner;

public class SimpleEdgeReader extends EdgeReader<SimpleVertex, SimpleEdge> {
    @Override
    public SimpleEdge readFromScanner(Scanner scanner) {
        SimpleVertex v1, v2;
        if (!scanner.hasNextInt()) {
            throw new IllegalArgumentException("Invalid input format.");
        }
        v1 = new SimpleVertex(scanner.nextInt());
        if (!scanner.hasNextInt()) {
            throw new IllegalArgumentException("Invalid input format.");
        }
        v2 = new SimpleVertex(scanner.nextInt());
        return new SimpleEdge(v1, v2);
    }
}
