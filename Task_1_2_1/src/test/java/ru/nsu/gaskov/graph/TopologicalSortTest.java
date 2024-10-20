package ru.nsu.gaskov.graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TopologicalSortTest {

    static Stream<Graph<SimpleVertex, SimpleEdge>> graphProvider() {
        AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> graph1 = new AdjacencyMatrixGraph<>();
        AdjacencyListGraph<SimpleVertex, SimpleEdge> graph2 = new AdjacencyListGraph<>();
        IncidenceMatrixGraph<SimpleVertex, SimpleEdge> graph3 = new IncidenceMatrixGraph<>();
        return Stream.of(graph1, graph2, graph3);
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    public void SortTest(Graph<SimpleVertex, SimpleEdge> graph) throws IOException {
        File inputFile = File.createTempFile("inputFile", ".txt");
        inputFile.deleteOnExit();

        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
        writer.write("8");
        writer.newLine();
        writer.write("7 4 3 5 6 2 1 8");
        writer.newLine();
        writer.write("9");
        writer.newLine();
        writer.write("1 4 2 4 2 5 3 5 4 6 4 7 4 8 5 7 3 8");
        writer.newLine();
        writer.close();
        graph.readFromFile(inputFile.getAbsolutePath(), new SimpleVertexReader(), new SimpleEdgeReader());
        assertArrayEquals(
            new String[]{"3", "2", "1", "4", "5", "7", "6", "8"},
            TopologicalSort
                .sortVertices(graph)
                .stream().map(SimpleVertex::toString).toArray()
        );
    }
}