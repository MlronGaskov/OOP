package ru.nsu.gaskov.graph;

/**
 * Represents a directed edge in a graph.
 *
 * @param <V> the type of vertex that this edge connects, extending the Vertex interface.
 */
public interface OrientedEdge<V extends Vertex> {

    /**
     * Returns the vertex where this edge starts.
     *
     * @return the source vertex of the edge.
     */
    V getFromVertex();

    /**
     * Returns the vertex where this edge ends.
     *
     * @return the target vertex of the edge.
     */
    V getToVertex();

    /**
     * Returns a string representation of the edge in a readable format.
     *
     * @return a string describing the edge.
     */
    @Override
    String toString();

    /**
     * Compares this edge to the specified object for equality.
     *
     * @param object the object to compare this edge against.
     * @return true if the specified object is equal to this edge; false otherwise.
     */
    @Override
    boolean equals(Object object);
}
