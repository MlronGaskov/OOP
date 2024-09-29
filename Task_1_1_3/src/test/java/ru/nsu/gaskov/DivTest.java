package ru.nsu.gaskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test.
 */
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
        assertTrue(new Number(4).isEquals(new Div("4/x").getDividend()));
    }

    @Test
    void testGetDivisor() {
        assertTrue(new Variable("x").isEquals(new Div("4/x").getDivisor()));
    }

    @Test
    void testDivDerivative() {
        assertTrue(new Div("(0*x-4*1)/(x*x)").isEquals(new Div("4/x").derivative("x")));
    }

    @Test
    void testDivEval() {
        assertAll(
            () -> assertEquals(14, (new Div("98/7")).eval("krg=4")),
            () -> assertEquals(14, (new Div("x/Y")).eval("x=98; Y = 7")),
            () -> assertEquals(2, (new Div("16/2/2/x")).eval("x=2;e=5;kfe=5")),
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
    void testDivIsEquals() {
        assertAll(
            () -> assertTrue(
                new Div("((3/x))").isEquals(
                    new Div(
                        new Number(3),
                        new Variable("x")
                    )
                )
            )
        );
    }

    @Test
    public void testSimplify() {
        assertAll(
            () -> assertTrue(new Number(6).isEquals(new Div(new Number(12), new Number(2)).simplify())),
            () -> assertTrue(
                new Variable("x").isEquals(
                    new Div(new Variable("x"), new Number(1)).simplify()
                )
            ),
            () -> assertTrue(
                new Number(0).isEquals(
                    new Div(new Number(0), new Variable("x")).simplify()
                )
            )
        );
    }
}