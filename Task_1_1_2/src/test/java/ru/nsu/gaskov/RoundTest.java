package ru.nsu.gaskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ru.nsu.gaskov.actions.*;
import ru.nsu.gaskov.card.Card;
import ru.nsu.gaskov.card.Rank;
import ru.nsu.gaskov.card.Suit;
import ru.nsu.gaskov.deck.AbstractDeck;
import ru.nsu.gaskov.deck.Deck;
import ru.nsu.gaskov.player.Player;

import java.util.Arrays;

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
        Action action;

        action = round.step();
        assertEquals(new PlayerAction(0, PlayerAct.BET), action);
        assertEquals(round.getHands()[0].getBet(), 50);

        action = round.step();
        assertInstanceOf(GameAction.class, action);
        assertEquals(GameAction.INITIAL_CARDS_DEALS, action);
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TEN)}).toList());
        assertEquals(round.getDealerOpenCards(),
            Arrays.stream(new Card[]{new Card(Suit.HEARTS, Rank.JACK)}).toList());
        assertEquals(round.getDealerClosedCard(), new Card(Suit.HEARTS, Rank.FIVE));

        action = round.step();
        assertEquals(new PlayerAction(0, PlayerAct.STAND), action);

        action = round.step();
        assertEquals(GameAction.OUTCOMES_DETERMINED, action);

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
        }
        ;
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
        assertEquals(new PlayerAction(0, PlayerAct.BET), round.step());
        assertEquals(GameAction.INITIAL_CARDS_DEALS, round.step());

        player.response = true;
        assertEquals(new PlayerAction(0, PlayerAct.INSURANCE), round.step());
        assertTrue(round.hands[0].isInsured());

        assertEquals(new PlayerAction(0, PlayerAct.SPLIT), round.step());
        assertTrue(round.hands[0].isSplat());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO)}).toList());
        assertIterableEquals(round.getHands()[0].getCards2(),
            Arrays.stream(new Card[]{
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.THREE)}).toList());

        assertEquals(new PlayerAction(0, PlayerAct.HIT), round.step());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.FOUR)}).toList());

        assertEquals(new PlayerAction(0, PlayerAct.HIT), round.step());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.FOUR),
                new Card(Suit.DIAMONDS, Rank.FIVE)}).toList());

        player.response = false;
        assertEquals(new PlayerAction(0, PlayerAct.STAND), round.step());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.FOUR),
                new Card(Suit.DIAMONDS, Rank.FIVE)}).toList());

        player.response = true;
        assertEquals(new PlayerAction(0, PlayerAct.HIT), round.step());
        assertIterableEquals(round.getHands()[0].getCards2(),
            Arrays.stream(new Card[]{
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.SPADES, Rank.FIVE)}).toList());

        player.response = false;
        assertEquals(new PlayerAction(0, PlayerAct.STAND), round.step());
        assertIterableEquals(round.getHands()[0].getCards2(),
            Arrays.stream(new Card[]{
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.SPADES, Rank.FIVE)}).toList());


        assertEquals(GameAction.OUTCOMES_DETERMINED, round.step());
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
            public boolean doubleBet() {
                return true;
            }
        }
        ;
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
        assertEquals(new PlayerAction(0, PlayerAct.BET), round.step());
        assertEquals(GameAction.INITIAL_CARDS_DEALS, round.step());

        assertEquals(new PlayerAction(0, PlayerAct.DOUBLE), round.step());
        assertTrue(round.hands[0].isDoubled());
        assertIterableEquals(round.getHands()[0].getCards1(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.TWO)}).toList());

        assertEquals(GameAction.OUTCOMES_DETERMINED, round.step());
        assertIterableEquals(round.getDealerOpenCards(),
            Arrays.stream(new Card[]{
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.DIAMONDS, Rank.THREE)}).toList());
        assertEquals(round.getOutcomes()[0], -100);
    }
}