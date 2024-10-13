package ru.nsu.gaskov.graph;

public interface OrientedEdge<V extends Vertex> {
    V getFromVertex();
    V getToVertex();

    @Override
    String toString();

    @Override
    boolean equals(Object object);
}
