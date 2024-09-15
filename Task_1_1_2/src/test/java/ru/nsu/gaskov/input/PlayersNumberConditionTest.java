package ru.nsu.gaskov.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayersNumberConditionTest {
    @Test
    public void validPlayersNumberTest() {
        PlayersNumberCondition condition = new PlayersNumberCondition();
        assertTrue(condition.isValid("2"));
    }

    @Test
    public void invalidNegativePlayersNumberTest() {
        PlayersNumberCondition condition = new PlayersNumberCondition();
        assertFalse(condition.isValid("-2"));
    }

    @Test
    public void invalidInputTest() {
        PlayersNumberCondition condition = new PlayersNumberCondition();
        assertFalse(condition.isValid("miron2005"));
    }

    @Test
    public void moreThanThreePlayersTest() {
        PlayersNumberCondition condition = new PlayersNumberCondition();
        assertFalse(condition.isValid("15"));
    }
}