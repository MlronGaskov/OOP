package ru.nsu.gaskov.graph;

/**
 * Represents a vertex in a graph.
 */
public interface Vertex {

    /**
     * Returns a string representation of the vertex.
     *
     * @return a string describing the vertex.
     */
    @Override
    String toString();

    /**
     * Compares this vertex to the specified object for equality.
     *
     * @param object the object to compare this vertex against.
     * @return true if the specified object is equal to this vertex; false otherwise.
     */
    @Override
    boolean equals(Object object);
}
