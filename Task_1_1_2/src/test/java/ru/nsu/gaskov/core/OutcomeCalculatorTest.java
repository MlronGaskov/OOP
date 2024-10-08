package ru.nsu.gaskov.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class OutcomeCalculatorTest {
    @Test
    public void testWin() {
        Hand playerHand = new Hand();
        playerHand.take(new Card(Suit.HEARTS, Rank.ACE));
        playerHand.take(new Card(Suit.HEARTS, Rank.EIGHT));
        playerHand.setBet(50);
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.HEARTS, Rank.TEN)}).toList();
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), 50);
    }

    @Test
    public void testLose() {
        Hand playerHand = new Hand();
        playerHand.take(new Card(Suit.HEARTS, Rank.ACE));
        playerHand.take(new Card(Suit.HEARTS, Rank.NINE));
        playerHand.setBet(50);
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.THREE),
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.EIGHT)}).toList();
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), -50);
    }

    @Test
    public void testDraw() {
        Hand playerHand = new Hand();
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.take(new Card(Suit.HEARTS, Rank.TEN));
        playerHand.setBet(50);
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.EIGHT)}).toList();
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), 0);
    }

    @Test
    public void testSplat() {
        Hand playerHand = new Hand();
        playerHand.insure();
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.split(new Card(Suit.HEARTS, Rank.JACK), new Card(Suit.HEARTS, Rank.TWO));
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.setBet(50);
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.EIGHT)}).toList();
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), -125);
    }

    @Test
    public void testInsure() {
        Hand playerHand = new Hand();
        playerHand.insure();
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.setBet(50);
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.ACE),
            new Card(Suit.HEARTS, Rank.TEN)}).toList();
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), 0);
    }
}