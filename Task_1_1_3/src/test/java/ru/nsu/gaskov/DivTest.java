package ru.nsu.gaskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DivTest {
    @Test
    void testIsDiv() {
        assertAll(
            () -> assertTrue(Div.isDiv("13/54")),
            () -> assertTrue(Div.isDiv("v*13/54")),
            () -> assertTrue(Div.isDiv("(abs-13)/(v-54)")),
            () -> assertTrue(Div.isDiv("(a/x*b*c/d)")),
            () -> assertTrue(Div.isDiv("((13/54))")),
            () -> assertTrue(Div.isDiv("343*323/123")),
            () -> assertFalse(Div.isDiv("13")),
            () -> assertFalse(Div.isDiv("(v)")),
            () -> assertFalse(Div.isDiv("abs+13-(v/54)")),
            () -> assertFalse(Div.isDiv("(a/x+b/c/d)")),
            () -> assertFalse(Div.isDiv("((13-54)+4-12-1+v)")),
            () -> assertFalse(Div.isDiv("343*323*123"))
        );
    }

    @Test
    void testGetDividend() {
        assertEquals(new Number(4), new Div("4/x").getDividend());
    }

    @Test
    void testGetDivisor() {
        assertEquals(new Variable("x"), new Div("4/x").getDivisor());
    }

    @Test
    void testDivDerivative() {
        assertEquals(new Div("(0*x-4*1)/(x*x)"), (new Div("4/x")).derivative("x"));
    }

    @Test
    void testDivEval() {
        assertAll(
            () -> assertEquals(14, (new Div("98/7")).eval("14=fkla")),
            () -> assertEquals(14, (new Div("x/Y")).eval("x=98; Y = 7")),
            () -> assertEquals(2, (new Div("16/2/2/x")).eval("x=2;e=5;kfe=fkla")),
            () -> assertEquals(0, (new Div("0/E")).eval("E=2.6"))
        );
    }

    @Test
    void testDivToString() {
        assertAll(
            () -> assertEquals(
                "(((12/13)/12)/2)",
                (new Div("12/13/12/2")).toString()
            ),
            () -> assertEquals(
                "(2/2)",
                (new Div(new Number(2), new Number(2))).toString()
            ),
            () -> assertThrows(IllegalArgumentException.class, () -> new Div("kfaj")),
            () -> assertThrows(IllegalArgumentException.class, () -> new Div("5*a43"))
        );
    }

    @Test
    void testDivEquals() {
        assertAll(
            () -> assertEquals(
                new Div("((3/x))"),
                new Div(
                    new Number(3),
                    new Variable("x")
                )
            )
        );
    }
}