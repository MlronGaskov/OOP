package ru.nsu.gaskov.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.nsu.gaskov.core.AbstractDeck;
import ru.nsu.gaskov.core.Card;
import ru.nsu.gaskov.core.Rank;
import ru.nsu.gaskov.core.Suit;

/**
 * Tests.
 */
class GameUserInterfaceTest {
    static class FixedDeck extends AbstractDeck {
        public FixedDeck(Card[] cards) {
            init(Arrays.stream(cards).toList());
        }
    }

    @Test
    public void testPrintCard() {
        Card card = new Card(Suit.HEARTS, Rank.ACE);
        String expectedOutput = "Ace of Hearts";
        String result = GameUserInterface.printCard(card);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testGameEnd() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        String input = "1\nmiron\ntest|4 D|5 S|J S|J D|5 S|5 D\n100\nno\nno\nno\nend";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        System.setOut(new PrintStream(myOut));
        GameUserInterface.main(null);
        String[] outStrings = myOut.toString().split("\n");
        String outcome = outStrings[outStrings.length - 2];
        assertEquals("miron: -100", outcome.substring(0, outcome.length() - 2));
    }
}