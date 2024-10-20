package ru.nsu.gaskov.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a directed graph using an adjacency matrix.
 *
 * @param <V> the type of vertices in the graph, extending the Vertex interface.
 * @param <E> the type of edges in the graph, extending the OrientedEdge interface.
 */
public class AdjacencyMatrixGraph<V extends Vertex, E extends OrientedEdge<V>>
    implements Graph<V, E> {
    private int verticesCount;
    private final List<V> vertices;
    private final List<List<E>> adjacencyMatrix;

    /**
     * Initializes a new empty graph.
     */
    public AdjacencyMatrixGraph() {
        verticesCount = 0;
        vertices = new ArrayList<>();
        adjacencyMatrix = new ArrayList<>();
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to be added.
     * @throws IllegalArgumentException if the vertex already exists.
     */
    @Override
    public void addVertex(V vertex) {
        if (vertex == null) {
            return;
        }
        if (vertices.contains(vertex)) {
            throw new IllegalArgumentException("The vertex already exists.");
        }
        verticesCount++;
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

    /**
     * Removes a vertex from the graph along with its associated edges.
     *
     * @param vertex the vertex to be removed.
     */
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
        verticesCount--;
        adjacencyMatrix.forEach(e -> e.remove(vertexIndex));
    }

    /**
     * Adds an edge to the graph.
     *
     * @param edge the edge to be added.
     * @throws IllegalArgumentException if the edge already exists.
     */
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
            throw new IllegalArgumentException("The edge between the vertices already exists.");
        }
        adjacencyMatrix.get(vertexFromIndex).set(vertexToIndex, edge);
    }

    /**
     * Removes an edge from the graph.
     *
     * @param edge the edge to be removed.
     */
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

    /**
     * Retrieves the neighbors of a specified vertex.
     *
     * @param vertex the vertex whose neighbors are to be retrieved.
     * @return a list of neighboring vertices.
     * @throws IllegalArgumentException if the vertex does not exist.
     */
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

    /**
     * Retrieves all vertices in the graph.
     *
     * @return a list of all vertices in the graph.
     */
    @Override
    public List<V> getAllVertices() {
        return vertices;
    }

    /**
     * Creates and returns a copy of the graph.
     *
     * @return a copy of the current graph instance.
     */
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

    /**
     * Returns a string representation of the graph.
     *
     * @return a string representing the vertices and adjacency matrix.
     */
    @Override
    public String toString() {
        return vertices.toString() + "\n" + adjacencyMatrix.toString();
    }

    /**
     * Compares this graph to another object for equality.
     *
     * @param object the object to compare this graph against.
     * @return true if the specified object is equal to this graph; false otherwise.
     */
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
            List<V> neighbours = getNeighbours(from);
            List<V> objectNeighbours = graphObject.getNeighbours(from);
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

    /**
     * Returns a hash code for this graph.
     *
     * @return a hash code value for this graph.
     */
    @Override
    public int hashCode() {
        return Objects.hash(verticesCount, vertices, adjacencyMatrix);
    }
}
