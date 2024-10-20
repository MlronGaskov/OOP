package ru.nsu.gaskov.graph;

import java.util.Scanner;

public interface EdgeReader<V extends Vertex, E extends OrientedEdge<V>> {
    E readFromScanner(Scanner scanner);
}
