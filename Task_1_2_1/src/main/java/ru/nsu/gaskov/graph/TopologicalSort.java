package ru.nsu.gaskov.graph;

import java.util.ArrayList;
import java.util.List;

public class TopologicalSort {
    public static <V extends Vertex, E extends OrientedEdge<V>> List<V> sortVertices(Graph<V, E> graph) {
        Graph<V, E> graphCopy = graph.copy();
        List<V> sortedVertices = new ArrayList<>();
        List<V> withoutNeighbours = new ArrayList<>(
            graphCopy.getAllVertices().stream().filter(e -> graphCopy.getNeighbours(e).isEmpty()).toList()
        );
        while (!withoutNeighbours.isEmpty() && !graphCopy.getAllVertices().isEmpty()) {
            for (V vertex: withoutNeighbours.reversed()) {
                graphCopy.removeVertex(vertex);
                sortedVertices.add(vertex);
            }
            withoutNeighbours = new ArrayList<>(
                graphCopy.getAllVertices().stream().filter(e -> graphCopy.getNeighbours(e).isEmpty()).toList()
            );
        }
        return sortedVertices.reversed();
    }
}
