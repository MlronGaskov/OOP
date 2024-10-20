package ru.nsu.gaskov.graph;

import java.util.Scanner;

public interface VertexReader<V extends Vertex> {
    V readFromScanner(Scanner scanner);
}
