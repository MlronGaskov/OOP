package ru.nsu.gaskov.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleEdgeTest {
    @Test
    public void testEquals() {
        SimpleEdge edge1 = new SimpleEdge(new SimpleVertex(5), new SimpleVertex(10));
        SimpleEdge edge2 = new SimpleEdge(new SimpleVertex(5), new SimpleVertex(10));
        SimpleEdge edge3 = new SimpleEdge(new SimpleVertex(17), new SimpleVertex(1));
        SimpleEdge edge4 = new SimpleEdge(new SimpleVertex(10), new SimpleVertex(5));

        assertAll(
            () -> assertEquals(edge1, edge2),
            () -> assertEquals(edge1.getFromVertex(), new SimpleVertex(5)),
            () -> assertEquals(edge1.getToVertex(), new SimpleVertex(10)),
            () -> assertEquals(edge1.hashCode(), edge2.hashCode()),
            () -> assertNotEquals(edge1, edge3),
            () -> assertNotEquals(edge1, edge4)
        );
    }

    @Test
    public void testToString() {
        SimpleEdge edge1 = new SimpleEdge(new SimpleVertex(5), new SimpleVertex(10));
        SimpleEdge edge2 = new SimpleEdge(new SimpleVertex(17), new SimpleVertex(1));
        SimpleEdge edge3 = new SimpleEdge(new SimpleVertex(10), new SimpleVertex(5));

        assertAll(
            () -> assertEquals("(5 -> 10)", edge1.toString()),
            () -> assertEquals("(17 -> 1)", edge2.toString()),
            () -> assertEquals("(10 -> 5)", edge3.toString())
        );
    }
}