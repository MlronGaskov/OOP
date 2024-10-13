package ru.nsu.gaskov.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleVertexTest {
    @Test
    public void testEquals() {
        SimpleVertex v1 = new SimpleVertex(11);
        SimpleVertex v2 = new SimpleVertex(11);
        SimpleVertex v3 = new SimpleVertex(100);
        assertAll(
            () -> assertEquals(v1, v2),
            () -> assertNotEquals(v1, v3),
            () -> assertEquals(v1.hashCode(), v2.hashCode()),
            () -> assertNotEquals(5, v1)
        );
    }

    @Test
    public void testToString() {
        SimpleVertex v1 = new SimpleVertex(11);
        SimpleVertex v2 = new SimpleVertex(4);
        assertAll(
            () -> assertEquals("11", v1.toString()),
            () -> assertEquals("4", v2.toString())
        );
    }
}