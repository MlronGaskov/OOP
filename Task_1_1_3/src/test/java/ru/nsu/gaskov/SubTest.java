package ru.nsu.gaskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTest {
    @Test
    public void testIsSub() {
        assertAll(
            () -> assertTrue(Sub.isSub("13-54")),
            () -> assertTrue(Sub.isSub("v-13-54")),
            () -> assertTrue(Sub.isSub("(abs-13)-(v-54)")),
            () -> assertTrue(Sub.isSub("(a/x-b*c*d)")),
            () -> assertTrue(Sub.isSub("((13-54))")),
            () -> assertTrue(Sub.isSub("343+323-123")),
            () -> assertFalse(Sub.isSub("13")),
            () -> assertFalse(Sub.isSub("(v)")),
            () -> assertFalse(Sub.isSub("abs+13+(v-54)")),
            () -> assertFalse(Sub.isSub("(a/x+b*c*d)")),
            () -> assertFalse(Sub.isSub("((13-54)+4-12-1+v)")),
            () -> assertFalse(Sub.isSub("343/323/123"))
        );
    }

    @Test
    public void testSubToString() {
        assertAll(
            () -> assertEquals(
                "(((12-13)-12)-2)",
                (new Sub("12-13-12-2")).toString()
            ),
            () -> assertEquals(
                "(2-2)",
                (new Sub(new Number(2), new Number(2))).toString()
            ),
            () -> assertThrows(IllegalArgumentException.class, () -> new Sub("kfaj")),
            () -> assertThrows(IllegalArgumentException.class, () -> new Sub("5+a43"))
        );
    }

    @Test void testSubIsEquals() {
        assertAll(
            () -> assertTrue(
                (new Sub("((3-x))")).isEquals(
                    new Sub(
                        new Number(3),
                        new Variable("x")
                    )
                )
            )
        );
    }

    @Test
    public void testSubEval() {
        assertAll(
            () -> assertEquals(14, (new Sub("21-7")).eval("kef=12")),
            () -> assertEquals(14, (new Sub("x-Y")).eval("x=2; Y = -12")),
            () -> assertEquals(14, (new Sub("7-7+x-e")).eval("x=19;e=5;kfe=3")),
            () -> assertEquals(-5, (new Sub("0-2-3+6-6")).eval(""))
        );
    }

    @Test
    public void testSubDerivative() {
        assertTrue(
            (new Sub("0-0-0-1-1-0")).isEquals(
                (new Sub("4-1-3-x-x-y")).derivative("x")
            )
        );
    }
}