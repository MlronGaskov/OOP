package ru.nsu.gaskov.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerConditionTest {
    @Test
    public void validYesAnswersTest() {
        AnswerCondition condition = new AnswerCondition();
        assertTrue(condition.isValid("Yes"));
        assertTrue(condition.isValid("yES"));
        assertTrue(condition.isValid("YES"));
        assertTrue(condition.isValid("Y"));
        assertTrue(condition.isValid("y"));
        assertTrue(condition.isAnswerYes("y"));
    }

    @Test
    public void validNoAnswersTest() {
        AnswerCondition condition = new AnswerCondition();
        assertTrue(condition.isValid("no"));
        assertTrue(condition.isValid("No"));
        assertTrue(condition.isValid("NO"));
        assertTrue(condition.isValid("N"));
        assertTrue(condition.isValid("n"));
        assertFalse(condition.isAnswerYes("No"));
    }


    @Test
    public void invalidAnswersTest() {
        AnswerCondition condition = new AnswerCondition();
        assertFalse(condition.isValid("no423"));
        assertFalse(condition.isValid("faefo"));
        assertFalse(condition.isValid("NfaefO"));
        assertFalse(condition.isValid("efafaf"));
        assertFalse(condition.isValid("49852435"));
        assertFalse(condition.isAnswerYes("5423642"));
    }
}