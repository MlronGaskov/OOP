package ru.nsu.gaskov.graph;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {
    @Test void testAdditionAndRemoving() {
        AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> graph = new AdjacencyMatrixGraph<>();
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

    @Test
    public void testGraphEquals() {
        AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> graph = new AdjacencyMatrixGraph<>();
        AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> sameGraph = new AdjacencyMatrixGraph<>();
        AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> notSameGraph = new AdjacencyMatrixGraph<>();

        graph.addEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(2)));
        graph.addEdge(new SimpleEdge(new SimpleVertex(2), new SimpleVertex(3)));
        graph.addEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(3)));

        sameGraph.addEdge(new SimpleEdge(new SimpleVertex(2), new SimpleVertex(3)));
        sameGraph.addEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(2)));
        sameGraph.addEdge(new SimpleEdge(new SimpleVertex(1), new SimpleVertex(3)));

        notSameGraph.addEdge(new SimpleEdge(new SimpleVertex(2), new SimpleVertex(3)));
        notSameGraph.addEdge(new SimpleEdge(new SimpleVertex(2), new SimpleVertex(1)));
        notSameGraph.addEdge(new SimpleEdge(new SimpleVertex(2), new SimpleVertex(2)));

        assertAll(
            () -> assertEquals(graph, sameGraph),
            () -> assertNotEquals(graph, notSameGraph),
            () -> assertEquals(graph, graph.copy())
        );
    }

    @Test
    public void testGraphRead() throws IOException {
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

        AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> graph = new AdjacencyMatrixGraph<>();
        graph.readFromFile(inputFile.getAbsolutePath(), new SimpleVertexReader(), new SimpleEdgeReader());

        AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> expectedGraph = new AdjacencyMatrixGraph<>();
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