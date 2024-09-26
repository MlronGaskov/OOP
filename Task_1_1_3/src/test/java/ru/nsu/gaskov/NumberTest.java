package ru.nsu.gaskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {
    @Test
    public void testIsNumber() {
        assertAll(
            () -> assertTrue(Number.isNumber("13")),
            () -> assertTrue(Number.isNumber("13.534")),
            () -> assertTrue(Number.isNumber("(((-3)))")),
            () -> assertTrue(Number.isNumber("-44513.54754")),
            () -> assertFalse(Number.isNumber("(x/y)")),
            () -> assertFalse(Number.isNumber("13ejr39")),
            () -> assertFalse(Number.isNumber("jksrjgr")),
            () -> assertFalse(Number.isNumber("(2"))
        );
    }

    @Test
    public void testNumberToString() {
        assertAll(
            () -> assertEquals("14", (new Number(14)).toString()),
            () -> assertEquals("-1544.4859234", (new Number("((-1544.4859234))")).toString()),
            () -> assertThrows(IllegalArgumentException.class, () -> new Number("kfaj")),
            () -> assertThrows(IllegalArgumentException.class, () -> new Number("557a"))
        );
    }

    @Test void testNumberEquals() {
        assertAll(
            () -> assertEquals(new Number("((15.54))"), new Number(15.54)),
            () -> assertNotEquals(new Number("2.565"), new Number(18)),
            () -> assertNotEquals(new Number("14"), new Object())
        );
    }

    @Test
    public void testNumberEval() {
        assertAll(
            () -> assertEquals(14, (new Number(14)).eval("14=fkla")),
            () -> assertEquals(-1544.4859234, (new Number(-1544.4859234)).eval(""))
        );
    }

    @Test
    public void testNumberDerivative() {
        assertEquals("0", (new Number(14)).derivative("x").toString());
    }
}