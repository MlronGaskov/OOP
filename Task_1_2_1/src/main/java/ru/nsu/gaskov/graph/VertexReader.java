package ru.nsu.gaskov.graph;

import java.util.Scanner;

public abstract class VertexReader<V extends Vertex> {
    public abstract V readFromScanner(Scanner scanner);
}
