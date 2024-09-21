package ru.nsu.gaskov.core;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class RoundTest {
    static class FixedDeck extends AbstractDeck {
        public FixedDeck(Card[] cards) {
            init(Arrays.stream(cards).toList());
        }
    }

    @Test
    public void testOnePlayerRound() {
        Player[] players = new Player[]{
            new Player() {
            }
        };
        Deck deck = new FixedDeck(new Card[]{
            new Card(Suit.HEARTS, Rank.ACE),
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.JACK),
            new Card(Suit.HEARTS, Rank.FIVE),
            new Card(Suit.HEARTS, Rank.TWO),
        });
        Round round = new Round(deck, players);
        Step action;

        action = round.step();
        assertEquals(new Step(StepType.PLAYER_BETS, 0), action);
        assertEquals(round.hands[0].getBet(), 50);

        action = round.step();
        assertEquals(StepType.INITIAL_CARDS_DEAL, action.act());
        assertIterableEquals(round.hands[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TEN)}).toList());
        assertEquals(round.dealerOpenCards,
            Arrays.stream(new Card[]{new Card(Suit.HEARTS, Rank.JACK)}).toList());
        assertEquals(round.dealerClosedCard, new Card(Suit.HEARTS, Rank.FIVE));

        action = round.step();
        assertEquals(new Step(StepType.PLAYER_STANDS, 0), action);

        action = round.step();
        assertEquals(StepType.OUTCOME_CALCULATION, action.act());

        assertIterableEquals(round.dealerOpenCards,
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.HEARTS, Rank.TWO)}).toList());
        assertArrayEquals(round.outcomes, new int[]{50});
    }

    @Test
    public void testOnePlayerWithSplitAction() {
        class CurrentPlayer implements Player {
            public boolean response;

            @Override
            public boolean insurance() {
                return response;
            }

            @Override
            public boolean split() {
                return response;
            }

            @Override
            public boolean doubleBet() {
                return response;
            }

            @Override
            public boolean hit() {
                return response;
            }
        }

        CurrentPlayer player = new CurrentPlayer();
        Deck deck = new FixedDeck(new Card[]{
            new Card(Suit.HEARTS, Rank.ACE),
            new Card(Suit.DIAMONDS, Rank.ACE),
            new Card(Suit.HEARTS, Rank.JACK),
            new Card(Suit.HEARTS, Rank.FIVE),
            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.DIAMONDS, Rank.THREE),
            new Card(Suit.HEARTS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.FIVE),
            new Card(Suit.SPADES, Rank.FIVE),
            new Card(Suit.HEARTS, Rank.KING),
        });
        Round round = new Round(deck, new Player[]{player});
        assertEquals(new Step(StepType.PLAYER_BETS, 0), round.step());
        assertEquals(StepType.INITIAL_CARDS_DEAL, round.step().act());

        player.response = true;
        assertEquals(new Step(StepType.PLAYER_MAKES_INSURANCE_DECISION, 0), round.step());
        assertTrue(round.hands[0].isInsured());

        assertEquals(new Step(StepType.PLAYER_MAKES_SPLIT_DECISION, 0), round.step());
        assertTrue(round.hands[0].isSplit());
        assertIterableEquals(round.hands[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO)}).toList());
        assertIterableEquals(round.hands[0].getCards2(),
            Arrays.stream(new Card[]{
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.THREE)}).toList());

        assertEquals(new Step(StepType.PLAYER_HITS, 0), round.step());
        assertIterableEquals(round.hands[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.FOUR)}).toList());

        assertEquals(new Step(StepType.PLAYER_HITS, 0), round.step());
        assertIterableEquals(round.hands[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.FOUR),
                new Card(Suit.DIAMONDS, Rank.FIVE)}).toList());

        player.response = false;
        assertEquals(new Step(StepType.PLAYER_STANDS, 0), round.step());
        assertIterableEquals(round.hands[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.FOUR),
                new Card(Suit.DIAMONDS, Rank.FIVE)}).toList());

        player.response = true;
        assertEquals(new Step(StepType.PLAYER_HITS, 0), round.step());
        assertIterableEquals(round.hands[0].getCards2(),
            Arrays.stream(new Card[]{
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.SPADES, Rank.FIVE)}).toList());

        player.response = false;
        assertEquals(new Step(StepType.PLAYER_STANDS, 0), round.step());
        assertIterableEquals(round.hands[0].getCards2(),
            Arrays.stream(new Card[]{
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.SPADES, Rank.FIVE)}).toList());


        assertEquals(StepType.OUTCOME_CALCULATION, round.step().act());
        assertIterableEquals(round.dealerOpenCards,
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.HEARTS, Rank.KING)}).toList());
        assertEquals(round.outcomes[0], 50 + 50 - 25);
    }

    @Test
    public void testOnePlayerWithDoubleAction() {
        class DoublerPlayer implements Player {
            @Override
            public boolean doubleBet() {
                return true;
            }
        }

        DoublerPlayer player = new DoublerPlayer();
        Deck deck = new FixedDeck(new Card[]{
            new Card(Suit.HEARTS, Rank.ACE),
            new Card(Suit.DIAMONDS, Rank.ACE),
            new Card(Suit.HEARTS, Rank.JACK),
            new Card(Suit.HEARTS, Rank.FIVE),
            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.DIAMONDS, Rank.THREE),
        });
        Round round = new Round(deck, new Player[]{player});
        assertEquals(new Step(StepType.PLAYER_BETS, 0), round.step());
        assertEquals(StepType.INITIAL_CARDS_DEAL, round.step().act());

        assertEquals(new Step(StepType.PLAYER_MAKES_DOUBLE_DECISION, 0), round.step());
        assertTrue(round.hands[0].isDoubled());
        assertIterableEquals(round.hands[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO)}).toList());

        assertEquals(StepType.OUTCOME_CALCULATION, round.step().act());
        assertIterableEquals(round.dealerOpenCards,
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.DIAMONDS, Rank.THREE)}).toList());
        assertEquals(round.outcomes[0], -100);
    }
}