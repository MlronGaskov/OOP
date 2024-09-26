package ru.nsu.gaskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MulTest {
    @Test
    void testIsMul() {
        assertAll(
            () -> assertTrue(Mul.isMul("13*54")),
            () -> assertTrue(Mul.isMul("v/13*54")),
            () -> assertTrue(Mul.isMul("(abs-13)*(v-54)")),
            () -> assertTrue(Mul.isMul("(a/x*b*c*d)")),
            () -> assertTrue(Mul.isMul("((13*54))")),
            () -> assertTrue(Mul.isMul("343*323*123")),
            () -> assertFalse(Mul.isMul("13")),
            () -> assertFalse(Mul.isMul("(v)")),
            () -> assertFalse(Mul.isMul("abs+13-(v*54)")),
            () -> assertFalse(Mul.isMul("(a/x+b*c*d)")),
            () -> assertFalse(Mul.isMul("((13-54)+4-12-1+v)")),
            () -> assertFalse(Mul.isMul("343/323/123"))
        );
    }

    @Test
    void testGetLeftMultiplier() {
        assertEquals(new Number(4), new Mul("4*x").getLeftMultiplier());
    }

    @Test
    void testGetRightMultiplier() {
        assertEquals(new Variable("x"), new Mul("4*x").getRightMultiplier());
    }

    @Test
    void testMulDerivative() {
        assertEquals(new Add("0*x+4*1"), (new Mul("4*x")).derivative("x"));
    }

    @Test
    void testMulEval() {
        assertAll(
            () -> assertEquals(14, (new Mul("2*7")).eval("14=fkla")),
            () -> assertEquals(14, (new Mul("x*Y")).eval("x=2; Y = 7")),
            () -> assertEquals(16, (new Mul("2*2*2*x")).eval("x=2;e=5;kfe=fkla")),
            () -> assertEquals(0, (new Mul("0*E")).eval("E=2.6"))
        );
    }

    @Test
    void testMulToString() {
        assertAll(
            () -> assertEquals(
                "(((12*13)*12)*2)",
                (new Mul("12*13*12*2")).toString()
            ),
            () -> assertEquals(
                "(2*2)",
                (new Mul(new Number(2), new Number(2))).toString()
            ),
            () -> assertThrows(IllegalArgumentException.class, () -> new Mul("kfaj")),
            () -> assertThrows(IllegalArgumentException.class, () -> new Mul("5/a43"))
        );
    }

    @Test
    void testMulEquals() {
        assertAll(
            () -> assertEquals(
                new Mul("((3*x))"),
                new Mul(
                    new Number(3),
                    new Variable("x")
                )
            )
        );
    }
}