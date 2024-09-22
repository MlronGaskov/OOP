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
                @Override
                public boolean insurance() {
                    return false;
                }

                @Override
                public boolean split() {
                    return false;
                }

                @Override
                public boolean doubleBet() {
                    return false;
                }

                @Override
                public boolean hit() {
                    return false;
                }

                @Override
                public int makeStartingBet() {
                    return 50;
                }

                @Override
                public String getName() {
                    return "";
                }

                @Override
                public void setRound(Round round) {
                }
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
        assertEquals(round.getHands()[0].getBet(), 50);

        action = round.step();
        assertEquals(StepType.INITIAL_CARDS_DEAL, action.act());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TEN)}).toList());
        assertEquals(round.getDealerOpenCards(),
            Arrays.stream(new Card[]{new Card(Suit.HEARTS, Rank.JACK)}).toList());
        assertEquals(round.getDealerClosedCard(), new Card(Suit.HEARTS, Rank.FIVE));

        action = round.step();
        assertEquals(new Step(StepType.PLAYER_STANDS, 0), action);

        action = round.step();
        assertEquals(StepType.OUTCOME_CALCULATION, action.act());

        assertIterableEquals(round.getDealerOpenCards(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.HEARTS, Rank.TWO)}).toList());
        assertArrayEquals(round.getOutcomes(), new int[]{50});
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

            @Override
            public int makeStartingBet() {
                return 50;
            }

            @Override
            public String getName() {
                return "";
            }

            @Override
            public void setRound(Round round) {
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
        assertTrue(round.getHands()[0].isInsured());

        assertEquals(new Step(StepType.PLAYER_MAKES_SPLIT_DECISION, 0), round.step());
        assertTrue(round.getHands()[0].isSplit());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO)}).toList());
        assertIterableEquals(round.getHands()[0].getCards2(),
            Arrays.stream(new Card[]{
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.THREE)}).toList());

        assertEquals(new Step(StepType.PLAYER_HITS, 0), round.step());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.FOUR)}).toList());

        assertEquals(new Step(StepType.PLAYER_HITS, 0), round.step());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.FOUR),
                new Card(Suit.DIAMONDS, Rank.FIVE)}).toList());

        player.response = false;
        assertEquals(new Step(StepType.PLAYER_STANDS, 0), round.step());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.FOUR),
                new Card(Suit.DIAMONDS, Rank.FIVE)}).toList());

        player.response = true;
        assertEquals(new Step(StepType.PLAYER_HITS, 0), round.step());
        assertIterableEquals(round.getHands()[0].getCards2(),
            Arrays.stream(new Card[]{
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.SPADES, Rank.FIVE)}).toList());

        player.response = false;
        assertEquals(new Step(StepType.PLAYER_STANDS, 0), round.step());
        assertIterableEquals(round.getHands()[0].getCards2(),
            Arrays.stream(new Card[]{
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.SPADES, Rank.FIVE)}).toList());


        assertEquals(StepType.OUTCOME_CALCULATION, round.step().act());
        assertIterableEquals(round.getDealerOpenCards(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.HEARTS, Rank.KING)}).toList());
        assertEquals(round.getOutcomes()[0], 50 + 50 - 25);
    }

    @Test
    public void testOnePlayerWithDoubleAction() {
        class DoublerPlayer implements Player {
            @Override
            public boolean insurance() {
                return false;
            }

            @Override
            public boolean split() {
                return false;
            }

            @Override
            public boolean doubleBet() {
                return true;
            }

            @Override
            public boolean hit() {
                return false;
            }

            @Override
            public int makeStartingBet() {
                return 50;
            }

            @Override
            public String getName() {
                return "";
            }

            @Override
            public void setRound(Round round) {
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
        assertTrue(round.getHands()[0].isDoubled());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO)}).toList());

        assertEquals(StepType.OUTCOME_CALCULATION, round.step().act());
        assertIterableEquals(round.getDealerOpenCards(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.DIAMONDS, Rank.THREE)}).toList());
        assertEquals(round.getOutcomes()[0], -100);
    }
}