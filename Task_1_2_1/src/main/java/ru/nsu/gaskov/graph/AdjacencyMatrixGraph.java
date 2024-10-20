package ru.nsu.gaskov.graph;

import java.util.*;

public class AdjacencyMatrixGraph<V extends Vertex, E extends OrientedEdge<V>> implements Graph<V, E> {
    private int verticesCount;
    private final List<V> vertices;
    private final List<List<E>> adjacencyMatrix;

    public AdjacencyMatrixGraph() {
        verticesCount = 0;
        vertices = new ArrayList<>();
        adjacencyMatrix = new ArrayList<>();
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
        List<E> newVertexEdges = new ArrayList<>();
        for (int i = 0; i < verticesCount; ++i) {
            if (i != verticesCount - 1) {
                adjacencyMatrix.get(i).add(null);
            }
            newVertexEdges.add(null);
        }
        adjacencyMatrix.add(newVertexEdges);
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
        adjacencyMatrix.remove(vertexIndex);
        vertices.remove(vertexIndex);
        verticesCount -= 1;
        adjacencyMatrix.forEach(e -> e.remove(vertexIndex));
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
        int vertexToIndex = vertices.indexOf(edge.getToVertex());
        if (adjacencyMatrix.get(vertexFromIndex).get(vertexToIndex) != null) {
            throw new IllegalArgumentException("The edge between the vertices is already exists.");
        }
        adjacencyMatrix.get(vertexFromIndex).set(vertexToIndex, edge);
    }

    @Override
    public void removeEdge(E edge) {
        if (edge == null) {
            return;
        }
        if (!vertices.contains(edge.getFromVertex()) || !vertices.contains(edge.getToVertex())) {
            return;
        }
        int vertexFromIndex = vertices.indexOf(edge.getFromVertex());
        int vertexToIndex = vertices.indexOf(edge.getToVertex());
        if (adjacencyMatrix.get(vertexFromIndex).get(vertexToIndex).equals(edge)) {
            adjacencyMatrix.get(vertexFromIndex).set(vertexToIndex, null);
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
        List<V> neighbours = new ArrayList<>();
        for (int i = 0; i < verticesCount; ++i) {
            if (adjacencyMatrix.get(vertexFromIndex).get(i) != null) {
                neighbours.add(vertices.get(i));
            }
        }
        return neighbours;
    }

    @Override
    public List<V> getAllVertices() {
        return vertices;
    }

    public Graph<V, E> copy() {
        Graph<V, E> graphCopy = new AdjacencyMatrixGraph<>();
        for (int i = 0; i < verticesCount; ++i) {
            graphCopy.addVertex(vertices.get(i));
        }
        for (int i = 0; i < verticesCount; ++i) {
            for (int j = 0; j < verticesCount; ++j) {
                if (adjacencyMatrix.get(i).get(j) != null) {
                    graphCopy.addEdge(adjacencyMatrix.get(i).get(j));
                }
            }
        }
        return graphCopy;
    }

    @Override
    public String toString() {
        return vertices.toString() + "\n" + adjacencyMatrix.toString();
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
        AdjacencyMatrixGraph<V, E> graphObject = (AdjacencyMatrixGraph<V, E>) object;
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
        return Objects.hash(verticesCount, vertices, adjacencyMatrix);
    }
}
