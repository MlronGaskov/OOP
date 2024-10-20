package ru.nsu.gaskov.graph;

import java.util.Objects;

/**
 * Represents a vertex in a graph identified by an integer ID.
 */
public class SimpleVertex implements Vertex {
    private final int id;

    /**
     * Constructs a SimpleVertex with the specified ID.
     *
     * @param id the unique identifier for this vertex.
     */
    public SimpleVertex(int id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the vertex, which is its ID.
     *
     * @return the string representation of this vertex.
     */
    @Override
    public String toString() {
        return String.valueOf(id);
    }

    /**
     * Compares this vertex to the specified object for equality.
     *
     * @param object the object to compare this vertex against.
     * @return true if the specified object is equal to this vertex; false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        return ((SimpleVertex) object).id == id;
    }

    /**
     * Returns a hash code for this vertex.
     *
     * @return a hash code value for this vertex.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
