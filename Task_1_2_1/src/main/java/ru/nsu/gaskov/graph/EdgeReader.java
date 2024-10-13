package ru.nsu.gaskov.graph;

import java.util.Scanner;

public abstract class EdgeReader<V extends Vertex, E extends OrientedEdge<V>> {
    public abstract E readFromScanner(Scanner scanner);
}
