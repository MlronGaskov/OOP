package ru.nsu.gaskov.graph;

import java.util.Scanner;

/**
 * A class for reading edges from a given input using a Scanner.
 */
public class SimpleEdgeReader implements EdgeReader<SimpleVertex, SimpleEdge> {

    /**
     * Reads an edge from the provided Scanner.
     * The method expects two integers from the input, which
     * represent the vertices of the edge.
     *
     * @param scanner the Scanner object used to read input.
     * @return a SimpleEdge connecting two SimpleVertex instances.
     * @throws IllegalArgumentException if the input format is invalid.
     */
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
