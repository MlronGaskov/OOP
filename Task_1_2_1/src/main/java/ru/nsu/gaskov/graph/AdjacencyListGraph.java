package ru.nsu.gaskov.graph;

import java.util.*;

public class AdjacencyListGraph <V extends Vertex, E extends OrientedEdge<V>> implements Graph<V, E> {
    private int verticesCount;
    private final List<V> vertices;
    private final List<List<E>> adjacencyList;

    public AdjacencyListGraph() {
        verticesCount = 0;
        vertices = new ArrayList<>();
        adjacencyList = new ArrayList<>();
    }

    @Override
    public void addVertex(V vertex) {
        if (vertex == null) {
            return;
        }
        if (vertices.contains(vertex)) {
            throw new IllegalArgumentException("The vertex is already exists.");
        }
        verticesCount += 1;
        vertices.add(vertex);
        adjacencyList.add(new ArrayList<>());
    }

    @Override
    public void removeVertex(V vertex) {
        if (vertex == null) {
            return;
        }
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) {
            return;
        }
        vertices.remove(vertexIndex);
        adjacencyList.remove(vertexIndex);
        verticesCount -= 1;
        for (int i = 0; i < verticesCount; ++i) {
            for (int j = 0; j < adjacencyList.get(i).size(); ++j) {
                if (adjacencyList.get(i).get(j).getToVertex().equals(vertex)) {
                    adjacencyList.get(i).remove(j);
                    break;
                }
            }
        }
    }

    @Override
    public void addEdge(E edge) {
        if (edge == null) {
            return;
        }
        if (!vertices.contains(edge.getFromVertex())) {
            addVertex(edge.getFromVertex());
        }
        if (!vertices.contains(edge.getToVertex())) {
            addVertex(edge.getToVertex());
        }
        int vertexFromIndex = vertices.indexOf(edge.getFromVertex());
        if (adjacencyList.get(vertexFromIndex).stream().anyMatch(
            e -> e.getToVertex().equals(edge.getToVertex()))
        ) {
            throw new IllegalArgumentException("The edge is already exists.");
        }
        adjacencyList.get(vertexFromIndex).add(edge);
    }

    @Override
    public void removeEdge(E edge) {
        if (edge == null) {
            return;
        }
        if (!vertices.contains(edge.getFromVertex())) {
            return;
        }
        if (!vertices.contains(edge.getToVertex())) {
            return;
        }
        int vertexFromIndex = vertices.indexOf(edge.getFromVertex());
        for (int i = 0; i < adjacencyList.get(vertexFromIndex).size(); ++i) {
            if (adjacencyList.get(vertexFromIndex).get(i).getToVertex().equals(edge.getToVertex())) {
                adjacencyList.get((vertexFromIndex)).remove(i);
                break;
            }
        }
    }

    @Override
    public List<V> getNeighbours(V vertex) {
        if (vertex == null) {
            return new ArrayList<>();
        }
        int vertexFromIndex = vertices.indexOf(vertex);
        if (vertexFromIndex == -1) {
            throw new IllegalArgumentException("No such vertex.");
        }
        return adjacencyList.get(vertexFromIndex).stream().map(OrientedEdge::getToVertex).toList();
    }

    @Override
    public List<V> getAllVertices() {
        return vertices;
    }

    @Override
    public Graph<V, E> copy() {
        Graph<V, E> graphCopy = new AdjacencyListGraph<>();
        for (int i = 0; i < verticesCount; ++i) {
            graphCopy.addVertex(vertices.get(i));
        }
        for (int i = 0; i < verticesCount; ++i) {
            for (E edge: adjacencyList.get(i)) {
                graphCopy.addEdge(edge);
            }
        }
        return graphCopy;
    }

    @Override
    public String toString() {
        return vertices.toString() + "\n" + adjacencyList.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AdjacencyListGraph<V, E> graphObject = (AdjacencyListGraph<V, E>) object;
        if (this.verticesCount != graphObject.verticesCount) {
            return false;
        }
        for (int i = 0; i < verticesCount; ++i) {
            if (!graphObject.getAllVertices().contains(vertices.get(i))) {
                return false;
            }
        }
        for (int i = 0; i < verticesCount; ++i) {
            V from = vertices.get(i);
            List<V> neighbours = getNeighbours(from).stream().toList();
            List<V> objectNeighbours = graphObject.getNeighbours(from).stream().toList();
            if (objectNeighbours.size() != neighbours.size()) {
                return false;
            }
            for (V neighbour : neighbours) {
                if (!objectNeighbours.contains(neighbour)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(verticesCount, vertices, adjacencyList);
    }
}
