package ru.nsu.gaskov.graph;

import java.util.*;

public class IncidenceMatrixGraph <V extends Vertex, E extends OrientedEdge<V>> implements Graph<V, E> {
    private int verticesCount;
    private final List<V> vertices;
    private final List<E> edges;
    private final List<List<Boolean>> incidenceMatrix;

    public IncidenceMatrixGraph() {
        verticesCount = 0;
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        incidenceMatrix = new ArrayList<>();
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
        for (int i = 0; i < edges.size(); ++i) {
            incidenceMatrix.get(i).add(false);
        }
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
        List<Integer> edgesIdsToRemove = new ArrayList<>();
        for (int i = 0; i < edges.size(); ++i) {
            if (incidenceMatrix.get(i).get(vertexIndex)) {
                edgesIdsToRemove.add(i);
            }
            incidenceMatrix.get(i).remove(vertexIndex);
        }
        for (int edgeIdToRemove: edgesIdsToRemove.reversed()) {
            removeEdge(edges.get(edgeIdToRemove));
        }
        vertices.remove(vertexIndex);
        verticesCount -= 1;
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
        edges.add(edge);
        List<Boolean> incidenceList = new ArrayList<>();
        for (int i = 0; i < verticesCount; ++i) {
            if (edge.getToVertex().equals(vertices.get(i))
                || edge.getFromVertex().equals(vertices.get(i))){
                incidenceList.add(true);
            } else {
                incidenceList.add(false);
            }
        }
        incidenceMatrix.add(incidenceList);
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
        int edgeIndex = edges.indexOf(edge);
        if (edgeIndex == -1) {
            return;
        }
        incidenceMatrix.remove(edgeIndex);
        edges.remove(edgeIndex);
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
        for (int i = 0; i < edges.size(); ++i) {
            if (incidenceMatrix.get(i).get(vertexFromIndex) && edges.get(i).getFromVertex().equals(vertex)) {
                neighbours.add(edges.get(i).getToVertex());
            }
        }
        return neighbours;
    }

    @Override
    public List<V> getAllVertices() {
        return vertices;
    }

    @Override
    public Graph<V, E> copy() {
        Graph<V, E> graphCopy = new IncidenceMatrixGraph<>();
        for (int i = 0; i < verticesCount; ++i) {
            graphCopy.addVertex(vertices.get(i));
        }
        for (E edge: edges) {
            graphCopy.addEdge(edge);
        }
        return graphCopy;
    }

    @Override
    public String toString() {
        return vertices.toString() + "\n" + edges.toString() + "\n" + incidenceMatrix.toString();
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
        IncidenceMatrixGraph<V, E> graphObject = (IncidenceMatrixGraph<V, E>) object;
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
        return Objects.hash(verticesCount, vertices, edges, incidenceMatrix);
    }
}
