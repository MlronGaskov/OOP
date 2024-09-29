package ru.nsu.gaskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {
    @Test
    public void testGetLastTerm() {
        assertAll(
            () -> assertEquals("56", Expression.getLastTerm("23+56")),
            () -> assertEquals("Var12", Expression.getLastTerm("Var21+56+Var12")),
            () -> assertEquals("((var134))", Expression.getLastTerm("((var134))")),
            () -> assertEquals("onlyOneVar", Expression.getLastTerm("onlyOneVar")),
            () -> assertEquals(
                "secondVar/123",
                Expression.getLastTerm("5*firstVar*18/122-secondVar/123")
            ),
            () -> assertEquals("813", Expression.getLastTerm("4*7-138-19293+813")),
            () -> assertEquals("x*x*x", Expression.getLastTerm("x*x*x"))
        );
    }

    @Test
    public void testGetLastMultiplier() {
        assertAll(
            () -> assertEquals("56", Expression.getLastMultiplier("23+56")),
            () -> assertEquals("56", Expression.getLastMultiplier("Var21+56")),
            () -> assertEquals("((var134))", Expression.getLastMultiplier("((var134))")),
            () -> assertEquals("onlyOneVar", Expression.getLastMultiplier("onlyOneVar")),
            () -> assertEquals(
                "123", Expression.getLastMultiplier("5*firstVar*18/122-secondVar/123")
            ),
            () -> assertEquals("4", Expression.getLastMultiplier("4*7-138-19293+813*4")),
            () -> assertEquals("x", Expression.getLastMultiplier("x*x*x")),
            () -> assertEquals("x", Expression.getLastMultiplier("x/x*x"))
        );
    }

    @Test
    public void testRemoveOuterBrackets() {
        assertAll(
            () -> assertEquals("23+56", Expression.removeOuterBrackets("(23+56)")),
            () -> assertEquals("Var21+56", Expression.removeOuterBrackets("Var21+56")),
            () -> assertEquals(
                "(var134) + (14/12)", Expression.removeOuterBrackets("(((var134) + (14/12)))")
            )
        );
    }

    @Test
    public void testCreate() {
        assertAll(
            () -> assertTrue(
                (new Add(new Number(3), new Mul(new Number(2), new Variable("x")))).isEquals(
                    Expression.create("(3+(2*x))")
                )
            ),
            () -> assertTrue(
                (new Add(new Number(3), new Mul(new Number(2), new Variable("x")))).isEquals(
                    Expression.create("3+2*x")
                )
            ),
            () -> assertTrue(
                (new Div(
                    new Variable("u"),
                    new Mul(
                        new Div(new Number(2), new Variable("x")),
                        new Variable("f")
                    )
                )).isEquals(
                    Expression.create("(u/(2/x*f))")
                )
            ),
            () -> assertTrue(
                (new Div(
                    new Variable("u"),
                    new Mul(
                        new Div(new Number(2), new Variable("x")),
                        new Variable("f")
                   )
                )).isEquals(
                    Expression.create("u/((((2/x))*f))")
                )
            )
        );
    }

    @Test
    public void testDerivative() {
        assertAll(
            () -> assertTrue(
                new Add(
                    new Number(0),
                    new Add(
                        new Mul(new Number(0), new Variable("x")),
                        new Mul(new Number(2), new Number(1))
                    )
                ).isEquals(
                    Expression.create("(3+(2*x))").derivative("x")
                )
            ),
            () -> assertTrue(
                Expression.create("(1*f-u*0)/(f*f)").isEquals(
                    Expression.create("(u/f)").derivative("u")
                )
            )
        );
    }

    @Test
    public void testEval() {
        assertAll(
            () -> assertEquals(
                23,
                Expression.create("(3+(2*x))").eval("x=10")
            )
        );
    }
}