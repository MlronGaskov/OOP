package ru.nsu.gaskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableTest {
    @Test
    public void testIsVariable() {
        assertAll(
            () -> assertTrue(Variable.isVariable("x")),
            () -> assertTrue(Variable.isVariable("X1")),
            () -> assertTrue(Variable.isVariable("Vari_able")),
            () -> assertTrue(Variable.isVariable("Va_r_4_83_82")),
            () -> assertTrue(Variable.isVariable("(Var48382)")),
            () -> assertTrue(Variable.isVariable("(((ejr39)))")),
            () -> assertFalse(Variable.isVariable("545")),
            () -> assertFalse(Variable.isVariable("fkaej95fj")),
            () -> assertFalse(Variable.isVariable("(2+2)"))
        );
    }

    @Test
    public void testVariableToString() {
        assertAll(
            () -> assertEquals("x", (new Variable("x")).toString()),
            () -> assertEquals("Var194", (new Variable("((Var194))")).toString()),
            () -> assertThrows(IllegalArgumentException.class, () -> new Variable("((k)+(faj))")),
            () -> assertThrows(IllegalArgumentException.class, () -> new Variable("557a"))
        );
    }

    @Test
    public void testVariableEval() {
        assertAll(
            () -> assertEquals(14, (new Variable("X")).eval("X = 14")),
            () -> assertEquals(
                -1544.4859234,
                (new Variable("Var93")).eval("Var123 = 12; Var93 = -1544.4859234")
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> (new Variable("x")).eval("Var123 = -1544.4859234; Var93 = 12")
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> (new Variable("Var123")).eval("Var123 = kjjef; Var93 = 12")
            )
        );
    }

    @Test
    public void testVariableDerivative() {
        assertAll(
            () -> assertEquals("0", (new Variable("x")).derivative("y").toString()),
            () -> assertEquals("1", (new Variable("Var123")).derivative("Var123").toString())
        );
    }

    @Test void testVariableEquals() {
        assertAll(
            () -> assertEquals(new Variable("((Var15))"), new Variable("Var15")),
            () -> assertNotEquals(new Variable("v2"), new Number(18)),
            () -> assertNotEquals(new Variable("Var"), new Variable("x"))
        );
    }
}