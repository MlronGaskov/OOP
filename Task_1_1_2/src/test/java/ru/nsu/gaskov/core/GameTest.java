package ru.nsu.gaskov.core;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
public class GameTest {

    private Game game;

    /**
     * 2 players 1 standard deck.
     */
    @BeforeEach
    public void setUp() {
        String input = "2\ntest1\ntest2\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        game = new Game(scanner);
    }

    @Test
    public void testGameInitialization() {
        assertEquals(2, game.getPlayersNumber());
        assertEquals(1000, game.getMoney()[0]);
        assertEquals(1000, game.getMoney()[1]);
        assertInstanceOf(SmallDeck.class, game.getDeck());
        assertEquals(2, game.getPlayers().length);
    }

    @Test
    public void testCountMoney() {
        game.countMoney(new int[]{100, -50});
        assertEquals(1100, game.getMoney()[0]);
        assertEquals(950, game.getMoney()[1]);
        game.countMoney(new int[]{200});
        assertEquals(1100, game.getMoney()[0]);
        assertEquals(950, game.getMoney()[1]);
    }

    @Test
    public void testPlayRound() {
        Round round = game.playRound();
        assertNotNull(round);
        assertArrayEquals(game.getPlayers(), round.getPlayers());
        assertEquals(game.getDeck(), round.getDeck());
    }
}
