package ru.nsu.gaskov.graph;

import java.util.Objects;

/**
 * Represents a directed edge in a graph, connecting two vertices.
 */
public class SimpleEdge implements OrientedEdge<SimpleVertex> {
    private final SimpleVertex from;
    private final SimpleVertex to;

    /**
     * Constructs a new directed edge from the specified vertex to another.
     *
     * @param from the vertex where the edge starts (source vertex).
     * @param to   the vertex where the edge ends (target vertex).
     */
    public SimpleEdge(SimpleVertex from, SimpleVertex to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the vertex where this edge starts.
     *
     * @return the source vertex of the edge.
     */
    @Override
    public SimpleVertex getFromVertex() {
        return from;
    }

    /**
     * Returns the vertex where this edge ends.
     *
     * @return the target vertex of the edge.
     */
    @Override
    public SimpleVertex getToVertex() {
        return to;
    }

    /**
     * Returns a string representation of the edge in the format "(from -> to)".
     *
     * @return a string representation of the edge.
     */
    @Override
    public String toString() {
        return "(" + from.toString() + " -> " + to.toString() + ")";
    }

    /**
     * Compares this edge to the specified object for equality.
     *
     * @param object the object to compare this edge against.
     * @return true if the specified object is equal to this edge; false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        return ((SimpleEdge) object).getFromVertex().equals(from)
            && ((SimpleEdge) object).getToVertex().equals(to);
    }

    /**
     * Returns a hash code for this edge.
     *
     * @return a hash code value for this edge.
     */
    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
