package ru.nsu.gaskov.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OutcomeCalculatorTest {
    @Test
    public void testWin() {
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.HEARTS, Rank.TEN)}).toList();
        Hand playerHand = new Hand();
        playerHand.take(new Card(Suit.HEARTS, Rank.ACE));
        playerHand.take(new Card(Suit.HEARTS, Rank.EIGHT));
        playerHand.setBet(50);
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), 50);
    }

    @Test
    public void testLose() {
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.THREE),
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.EIGHT)}).toList();
        Hand playerHand = new Hand();
        playerHand.take(new Card(Suit.HEARTS, Rank.ACE));
        playerHand.take(new Card(Suit.HEARTS, Rank.NINE));
        playerHand.setBet(50);
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), -50);
    }

    @Test
    public void testDraw() {
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.EIGHT)}).toList();
        Hand playerHand = new Hand();
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.take(new Card(Suit.HEARTS, Rank.TEN));
        playerHand.setBet(50);
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), 0);
    }

    @Test
    public void testSplat() {
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.EIGHT)}).toList();
        Hand playerHand = new Hand();
        playerHand.insure();
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.split(new Card(Suit.HEARTS, Rank.JACK), new Card(Suit.HEARTS, Rank.TWO));
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.setBet(50);
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), -125);
    }

    @Test
    public void testInsure() {
        List<Card> dealerCards = Arrays.stream(new Card[]{
            new Card(Suit.HEARTS, Rank.ACE),
            new Card(Suit.HEARTS, Rank.TEN)}).toList();
        Hand playerHand = new Hand();
        playerHand.insure();
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.take(new Card(Suit.HEARTS, Rank.KING));
        playerHand.setBet(50);
        assertEquals(OutcomeCalculator.calculate(playerHand, dealerCards), 0);
    }
}