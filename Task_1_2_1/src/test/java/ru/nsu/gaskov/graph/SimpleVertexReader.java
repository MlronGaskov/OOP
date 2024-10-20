package ru.nsu.gaskov.graph;

import java.util.Scanner;

/**
 * A class for reading vertices from a given input using a Scanner.
 *
 * This class implements the VertexReader interface specifically for
 * creating SimpleVertex instances based on integer input.
 */
public class SimpleVertexReader implements VertexReader<SimpleVertex> {

    /**
     * Reads a vertex from the provided Scanner.
     * The method expects an integer from the input, which
     * represents the ID of the vertex.
     *
     * @param scanner the Scanner object used to read input.
     * @return a SimpleVertex instance created from the input integer.
     * @throws IllegalArgumentException if the input format is invalid.
     */
    @Override
    public SimpleVertex readFromScanner(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return new SimpleVertex(scanner.nextInt());
        } else {
            throw new IllegalArgumentException("Invalid input format.");
        }
    }
}
