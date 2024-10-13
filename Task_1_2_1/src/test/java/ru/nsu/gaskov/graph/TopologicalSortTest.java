package ru.nsu.gaskov.graph;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortTest {
    @Test
    public void AdjacencyMatrixGraphTest() throws IOException {
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

        AdjacencyMatrixGraph<SimpleVertex, SimpleEdge> graph = new AdjacencyMatrixGraph<>();
        graph.readFromFile(inputFile.getAbsolutePath(), new SimpleVertexReader(), new SimpleEdgeReader());

        System.out.println(TopologicalSort.sortVertices(graph));

    }

    @Test
    public void AdjacencyListGraphTest() throws IOException {
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

        AdjacencyListGraph<SimpleVertex, SimpleEdge> graph = new AdjacencyListGraph<>();
        graph.readFromFile(inputFile.getAbsolutePath(), new SimpleVertexReader(), new SimpleEdgeReader());

        System.out.println(TopologicalSort.sortVertices(graph));

    }

    @Test
    public void incidenceMatrixGraphTest() throws IOException {
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

        IncidenceMatrixGraph<SimpleVertex, SimpleEdge> graph = new IncidenceMatrixGraph<>();
        graph.readFromFile(inputFile.getAbsolutePath(), new SimpleVertexReader(), new SimpleEdgeReader());

        System.out.println(TopologicalSort.sortVertices(graph));

    }
}