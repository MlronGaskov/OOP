package ru.nsu.gaskov.ui;

import org.junit.jupiter.api.Test;
import ru.nsu.gaskov.core.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

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
        InputStream inputStream = System.in;
        PrintStream outputStream = System.out;

        String input = "1\nmiron\n1\n100\nno\nno\nend";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        GameUserInterface.main(null);

        System.setIn(inputStream);
        System.setOut(outputStream);

        System.out.println(myOut.toString());
    }
}