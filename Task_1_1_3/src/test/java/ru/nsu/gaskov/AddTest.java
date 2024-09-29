package ru.nsu.gaskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test.
 */
class AddTest {
    @Test
    public void testIsAdd() {
        assertAll(
            () -> assertTrue(Add.isAdd("13+54")),
            () -> assertTrue(Add.isAdd("v-13+54")),
            () -> assertTrue(Add.isAdd("(abs-13)+(v-54)")),
            () -> assertTrue(Add.isAdd("(a/x+b*c*d)")),
            () -> assertTrue(Add.isAdd("((13+54))")),
            () -> assertTrue(Add.isAdd("343+323+123")),
            () -> assertTrue(Add.isAdd("abs-13+(v-54)")),
            () -> assertFalse(Add.isAdd("13")),
            () -> assertFalse(Add.isAdd("(v)")),
            () -> assertFalse(Add.isAdd("(a/x-b*c*d)")),
            () -> assertFalse(Add.isAdd("((13+54)+4+12+1-v)")),
            () -> assertFalse(Add.isAdd("343-323-123"))
        );
    }

    @Test
    public void testAddToString() {
        assertAll(
            () -> assertEquals("(((12+13)+12)+2)", (new Add("12+13+12+2")).toString()),
            () -> assertEquals(
                "(2+2)",
                (new Add(new Number(2), new Number(2))).toString()
            ),
            () -> assertThrows(IllegalArgumentException.class, () -> new Add("kfaj")),
            () -> assertThrows(IllegalArgumentException.class, () -> new Add("5-57a"))
        );
    }

    @Test void testAddIsEquals() {
        assertAll(
            () -> assertTrue(
                new Add("((3+x))").isEquals(
                    new Add(
                        new Number(3),
                        new Variable("x")
                    )
                )
            ),
            () -> assertFalse(
                new Add("((5+x))").isEquals(
                    new Add(
                        new Number(3),
                        new Variable("x")
                    )
                )
            )
        );
    }

    @Test
    public void testAddEval() {
        assertAll(
            () -> assertEquals(14, (new Add("7+7")).eval("kg=3")),
            () -> assertEquals(14, (new Add("x+Y")).eval("x=2; Y = 12")),
            () -> assertEquals(14, (new Add("7+7+x+e")).eval("x=9;e=-9;kfe=12")),
            () -> assertEquals(17, (new Add("0+2+3+6+6")).eval(""))
        );
    }

    @Test
    public void testAddDerivative() {
        assertTrue(
            (new Add("0+0+0+1+1+0")).isEquals(
                (new Add("4+1+3+x+x+y")).derivative("x")
            )
        );
    }

    @Test
    public void testSimplify() {
        assertAll(
            () -> assertTrue(new Number(6).isEquals(new Add(new Number(2), new Number(4)).simplify())),
            () -> assertTrue(
                new Variable("x").isEquals(
                    new Add(new Variable("x"), new Number(0)).simplify()
                )
            )
        );
    }
}