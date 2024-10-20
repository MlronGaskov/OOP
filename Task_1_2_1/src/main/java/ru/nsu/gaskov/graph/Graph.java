package ru.nsu.gaskov.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public interface Graph<V extends Vertex, E extends OrientedEdge<V>> {
    void addVertex(V vertex);
    void removeVertex(V vertex);

    void addEdge(E edge);
    void removeEdge(E edge);

    List<V> getNeighbours(V vertex);
    List<V> getAllVertices();

    default void readFromFile(
        String filename,
        VertexReader<V> vertexReader,
        EdgeReader<V, E> edgeReader
    ) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (scanner.hasNextInt()) {
                int numVertices = scanner.nextInt();
                for (int i = 0; i < numVertices; i++) {
                    V vertex = vertexReader.readFromScanner(scanner);
                    addVertex(vertex);
                }
            } else {
                throw new IllegalArgumentException("Invalid input format.");
            }
            if (scanner.hasNextInt()) {
                int numEdges = scanner.nextInt();
                for (int i = 0; i < numEdges; i++) {
                    E edge = edgeReader.readFromScanner(scanner);
                    addEdge(edge);
                }
            } else {
                throw new IllegalArgumentException("Invalid input format.");
            }
        }
    }

    Graph<V, E> copy();

    @Override
    String toString();

    @Override
    boolean equals(Object object);
}
