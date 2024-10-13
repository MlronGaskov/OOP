package ru.nsu.gaskov.graph;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface Graph<V extends Vertex, E extends OrientedEdge<V>> {
    void addVertex(V vertex);
    void removeVertex(V vertex);

    void addEdge(E edge);
    void removeEdge(E edge);

    Collection<V> getNeighbours(V vertex);
    Collection<V> getAllVertices();

    void readFromFile(
        String filename,
        VertexReader<V> vertexReader,
        EdgeReader<V, E> edgeReader
    ) throws FileNotFoundException;

    Graph<V, E> copy();

    @Override
    String toString();

    @Override
    boolean equals(Object object);
}
