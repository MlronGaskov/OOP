package ru.nsu.gaskov.graph;

import java.util.Scanner;

/**
 * An interface for reading vertex data from an input source.
 *
 * @param <V> the type of vertex this reader will process, which extends the Vertex interface.
 */
public interface VertexReader<V extends Vertex> {

    /**
     * Reads a vertex from the provided Scanner.
     *
     * @param scanner the Scanner object used to read input.
     * @return an instance of the vertex type V created from the input.
     */
    V readFromScanner(Scanner scanner);
}
