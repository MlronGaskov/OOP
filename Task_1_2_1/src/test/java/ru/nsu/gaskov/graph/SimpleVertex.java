package ru.nsu.gaskov.graph;

import java.util.Objects;

public class SimpleVertex implements Vertex {
    private final int id;

    public SimpleVertex(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

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

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}