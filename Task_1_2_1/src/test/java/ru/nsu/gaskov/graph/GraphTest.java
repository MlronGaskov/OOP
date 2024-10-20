package ru.nsu.gaskov.graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    static Stream<Graph<SimpleVertex, SimpleEdge>> graphProvider() {
        AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> graph1 = new AdjacencyMatrixGraph<>();
        AdjacencyListGraph<SimpleVertex, SimpleEdge> graph2 = new AdjacencyListGraph<>();
        IncidenceMatrixGraph<SimpleVertex, SimpleEdge> graph3 = new IncidenceMatrixGraph<>();
        return Stream.of(graph1, graph2, graph3);
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testAdditionAndRemoving(Graph<SimpleVertex, SimpleEdge> graph) {
        graph.addVertex(new SimpleVertex(1));
        graph.addVertex(new SimpleVertex(2));
        graph.addVertex(new SimpleVertex(3));
        List<SimpleVertex> verticesExpected = List.of(
            new SimpleVertex(1),
            new SimpleVertex(2),
            new SimpleVertex(3)
        );
        assertArrayEquals(verticesExpected.toArray(), graph.getAllVertices().toArray());

        graph.addEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(2)));
        graph.removeVertex(new SimpleVertex(3));
        verticesExpected = List.of(
            new SimpleVertex(1),
            new SimpleVertex(2)
        );
        assertArrayEquals(verticesExpected.toArray(), graph.getAllVertices().toArray());
        assertArrayEquals(
            List.of(new SimpleVertex(2)).toArray(),
            graph.getNeighbours(new SimpleVertex(1)).toArray()
        );

        graph.removeEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(2)));
        assertArrayEquals(
            List.of().toArray(),
            graph.getNeighbours(new SimpleVertex(1)).toArray()
        );
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    public void testGraphEquals(Graph<SimpleVertex, SimpleEdge> graph) {

        graph.addEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(2)));
        graph.addEdge(new SimpleEdge(new SimpleVertex(2), new SimpleVertex(3)));
        graph.addEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(3)));

        Graph<SimpleVertex, SimpleEdge> notSameGraph = graph.copy();
        notSameGraph.addEdge(new SimpleEdge(new SimpleVertex(6), new SimpleVertex(8)));

        assertAll(
            () -> assertNotEquals(graph, notSameGraph),
            () -> assertEquals(graph, graph.copy())
        );
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    public void testGraphRead(Graph<SimpleVertex, SimpleEdge> graph) throws IOException {
        File inputFile = File.createTempFile("inputFile", ".txt");
        inputFile.deleteOnExit();

        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
        writer.write("5");
        writer.newLine();
        writer.write("1 2 3 4 5");
        writer.newLine();
        writer.write("4");
        writer.newLine();
        writer.write("1 2 1 3 4 5 3 4");
        writer.newLine();
        writer.close();

        graph.readFromFile(inputFile.getAbsolutePath(), new SimpleVertexReader(), new SimpleEdgeReader());

        Graph<SimpleVertex, SimpleEdge> expectedGraph = switch (graph) {
            case AdjacencyListGraph<SimpleVertex, SimpleEdge> adjacencyListGraph ->
                new AdjacencyListGraph<>();
            case AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> adjacencyMatrixGraph ->
                new AdjacencyMatrixGraph<>();
            case IncidenceMatrixGraph<SimpleVertex, SimpleEdge> incidenceMatrixGraph ->
                new IncidenceMatrixGraph<>();
            default -> throw new InvalidPropertiesFormatException("");
        };

        expectedGraph.addVertex(new SimpleVertex(1));
        expectedGraph.addVertex(new SimpleVertex(2));
        expectedGraph.addVertex(new SimpleVertex(3));
        expectedGraph.addVertex(new SimpleVertex(4));
        expectedGraph.addVertex(new SimpleVertex(5));
        expectedGraph.addEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(2)));
        expectedGraph.addEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(3)));
        expectedGraph.addEdge(new SimpleEdge(new SimpleVertex(4), new SimpleVertex(5)));
        expectedGraph.addEdge(new SimpleEdge(new SimpleVertex(3), new SimpleVertex(4)));

        assertEquals(expectedGraph, graph);
    }
}