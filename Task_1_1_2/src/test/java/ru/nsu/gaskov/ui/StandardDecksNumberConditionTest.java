package ru.nsu.gaskov.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardDecksNumberConditionTest {
    @Test
    public void validDecksNumberTest() {
        StandardDecksNumberCondition condition = new StandardDecksNumberCondition();
        assertTrue(condition.isValid("2"));
        assertTrue(condition.isValid("4"));
        assertTrue(condition.isValid("8"));
        assertTrue(condition.isValid("1"));
    }

    @Test
    public void invalidDecksNumberTest() {
        StandardDecksNumberCondition condition = new StandardDecksNumberCondition();
        assertFalse(condition.isValid("-2"));
        assertFalse(condition.isValid("3412"));
        assertFalse(condition.isValid("miron2005"));
    }
}