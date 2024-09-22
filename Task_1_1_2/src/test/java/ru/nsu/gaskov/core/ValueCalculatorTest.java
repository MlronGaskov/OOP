package ru.nsu.gaskov.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class ValueCalculatorTest {

    @Test
    void calculateWithoutAcesTest() {
        Collection<Card> cards = new ArrayList<>();
        assertEquals(0, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.CLUBS, Rank.FIVE));
        assertEquals(5, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        assertEquals(15, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.HEARTS, Rank.TWO));
        assertEquals(17, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.CLUBS, Rank.TEN));
        assertEquals(27, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        assertEquals(37, ValueCalculator.calculate(cards));
    }

    @Test
    void calculateWithAcesTest() {
        Collection<Card> cards = new ArrayList<>();
        assertEquals(0, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.CLUBS, Rank.FIVE));
        assertEquals(5, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        assertEquals(16, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        assertEquals(17, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        assertEquals(18, ValueCalculator.calculate(cards));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        assertEquals(13, ValueCalculator.calculate(cards));
    }
}