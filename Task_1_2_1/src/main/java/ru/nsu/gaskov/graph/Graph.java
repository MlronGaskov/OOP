package ru.nsu.gaskov.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * An interface representing a graph structure.
 *
 * @param <V> the type of vertices in the graph, extending the Vertex interface.
 * @param <E> the type of edges in the graph, extending the OrientedEdge interface.
 */
public interface Graph<V extends Vertex, E extends OrientedEdge<V>> {

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to be added.
     */
    void addVertex(V vertex);

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex the vertex to be removed.
     */
    void removeVertex(V vertex);

    /**
     * Adds an edge to the graph.
     *
     * @param edge the edge to be added.
     */
    void addEdge(E edge);

    /**
     * Removes an edge from the graph.
     *
     * @param edge the edge to be removed.
     */
    void removeEdge(E edge);

    /**
     * Retrieves the neighbors of a specified vertex.
     *
     * @param vertex the vertex whose neighbors are to be retrieved.
     * @return a list of neighboring vertices.
     */
    List<V> getNeighbours(V vertex);

    /**
     * Retrieves all vertices in the graph.
     *
     * @return an unmodifiable list of all vertices in the graph.
     * @throws UnsupportedOperationException if the returned list is modified.
     */
    List<V> getAllVertices();

    /**
     * Reads graph data from a file and populates the graph.
     * The file format should start with the number of vertices,
     * followed by vertex data, then the number of edges,
     * and finally edge data.
     *
     * @param filename      the path to the input file containing graph data.
     * @param vertexReader  the reader used to create vertex instances.
     * @param edgeReader    the reader used to create edge instances.
     * @throws FileNotFoundException if the specified file does not exist.
     * @throws IllegalArgumentException if the input format is invalid.
     */
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

    /**
     * Creates and returns a copy of the graph.
     *
     * @return a copy of the current graph instance.
     */
    Graph<V, E> copy();

    @Override
    String toString();

    @Override
    boolean equals(Object object);
}
