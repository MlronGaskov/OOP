package ru.nsu.gaskov.graph;

import java.util.Scanner;

/**
 * An interface for reading edge data from an input source.
 *
 * @param <V> the type of vertex the edge is associated with, extending the Vertex interface.
 * @param <E> the type of edge that this reader will process, extending the OrientedEdge interface.
 */
public interface EdgeReader<V extends Vertex, E extends OrientedEdge<V>> {

    /**
     * Reads an edge from the provided Scanner.
     *
     * @param scanner the Scanner object used to read input.
     * @return an instance of the edge type E created from the input.
     * @throws IllegalArgumentException if the input format is invalid.
     */
    E readFromScanner(Scanner scanner);
}
