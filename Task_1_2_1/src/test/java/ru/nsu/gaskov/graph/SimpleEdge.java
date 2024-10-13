package ru.nsu.gaskov.graph;

import java.util.Objects;

public class SimpleEdge implements OrientedEdge<SimpleVertex> {
    private final SimpleVertex from, to;

    public SimpleEdge(SimpleVertex from, SimpleVertex to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public SimpleVertex getFromVertex() {
        return from;
    }

    @Override
    public SimpleVertex getToVertex() {
        return to;
    }

    @Override
    public String toString() {
        return "(" + from.toString() + " -> " + to.toString() + ")";
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
