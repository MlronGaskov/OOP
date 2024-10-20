package ru.nsu.gaskov.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides utility methods for topological sorting of directed graphs.
 */
public class TopologicalSort {

    /**
     * Performs a topological sort on the specified directed graph.
     *
     * @param graph the directed graph to be sorted.
     * @param <V> the type of vertices in the graph, extending the Vertex interface.
     * @param <E> the type of edges in the graph, extending the OrientedEdge interface.
     * @return a list containing the vertices in topologically sorted order.
     */
    public static <V extends Vertex, E extends OrientedEdge<V>> List<V> sort(Graph<V, E> graph) {
        Graph<V, E> graphCopy = graph.copy();
        List<V> sortedVertices = new ArrayList<>();
        List<V> withoutNeighbours = new ArrayList<>(
            graphCopy
                .getAllVertices()
                .stream()
                .filter(e -> graphCopy.getNeighbours(e).isEmpty())
                .toList()
        );
        while (!withoutNeighbours.isEmpty() && !graphCopy.getAllVertices().isEmpty()) {
            for (V vertex : withoutNeighbours.reversed()) {
                graphCopy.removeVertex(vertex);
                sortedVertices.add(vertex);
            }
            withoutNeighbours = new ArrayList<>(
                graphCopy
                    .getAllVertices()
                    .stream()
                    .filter(e -> graphCopy.getNeighbours(e).isEmpty())
                    .toList()
            );
        }
        return sortedVertices.reversed();
    }
}
