package ru.nsu.gaskov.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BetConditionTest {
    @Test
    public void validBetTest() {
        BetCondition condition = new BetCondition();
        assertTrue(condition.isValid("100"));
    }

    @Test
    public void invalidNegativeBetTest() {
        BetCondition condition = new BetCondition();
        assertFalse(condition.isValid("-100"));
    }

    @Test
    public void invalidBetTest() {
        BetCondition condition = new BetCondition();
        assertFalse(condition.isValid("miron2005"));
    }
}