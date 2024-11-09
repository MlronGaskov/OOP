package ru.nsu.gaskov.text.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An iterator for finding all occurrences of a specified substring in a text file.
 */
class SubstringIndexIteratorTest {
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("test-file", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testFindSingleSubstringIndex() throws IOException {
        Files.writeString(tempFile, "ababcd");
        String substring = "abcd";
        List<Long> expectedIndices = List.of(2L);

        try (SubstringIndexIterator iterator = new SubstringIndexIterator(tempFile, substring)) {
            List<Long> actualIndices = new ArrayList<>();
            while (iterator.hasNext()) {
                actualIndices.add(iterator.next());
            }
            assertEquals(expectedIndices, actualIndices);
        }
    }

    @Test
    void testNoOccurrences() throws IOException {
        Files.writeString(tempFile, "abcdefg");
        String substring = "xyz";
        List<Long> expectedIndices = List.of();

        try (SubstringIndexIterator iterator = new SubstringIndexIterator(tempFile, substring)) {
            List<Long> actualIndices = new ArrayList<>();
            while (iterator.hasNext()) {
                actualIndices.add(iterator.next());
            }
            assertEquals(expectedIndices, actualIndices);
        }
    }

    @Test
    void testMultipleOccurrences() throws IOException {
        Files.writeString(tempFile, "ababcabcabc");
        String substring = "abc";
        List<Long> expectedIndices = List.of(2L, 5L, 8L);

        try (SubstringIndexIterator iterator = new SubstringIndexIterator(tempFile, substring)) {
            List<Long> actualIndices = new ArrayList<>();
            while (iterator.hasNext()) {
                actualIndices.add(iterator.next());
            }
            assertEquals(expectedIndices, actualIndices);
        }
    }

    @Test
    void testBackSlash() throws IOException {
        Files.writeString(tempFile, "aba\nbc\nabcabc\n\n");
        String substring = "\n";
        List<Long> expectedIndices = List.of(3L, 6L, 13L, 14L);

        try (SubstringIndexIterator iterator = new SubstringIndexIterator(tempFile, substring)) {
            List<Long> actualIndices = new ArrayList<>();
            while (iterator.hasNext()) {
                actualIndices.add(iterator.next());
            }
            assertEquals(expectedIndices, actualIndices);
        }
    }

    @Test
    void testSubstringAtBeginning() throws IOException {
        Files.writeString(tempFile, "testabc");
        String substring = "test";
        List<Long> expectedIndices = List.of(0L);

        try (SubstringIndexIterator iterator = new SubstringIndexIterator(tempFile, substring)) {
            List<Long> actualIndices = new ArrayList<>();
            while (iterator.hasNext()) {
                actualIndices.add(iterator.next());
            }
            assertEquals(expectedIndices, actualIndices);
        }
    }

    @Test
    void testSubstringAtEnd() throws IOException {
        Files.writeString(tempFile, "abcdtest");
        String substring = "test";
        List<Long> expectedIndices = List.of(4L);

        try (SubstringIndexIterator iterator = new SubstringIndexIterator(tempFile, substring)) {
            List<Long> actualIndices = new ArrayList<>();
            while (iterator.hasNext()) {
                actualIndices.add(iterator.next());
            }
            assertEquals(expectedIndices, actualIndices);
        }
    }

    @Test
    void testSingleCharacterSubstring() throws IOException {
        Files.writeString(tempFile, "aaaa");
        String substring = "a";
        List<Long> expectedIndices = List.of(0L, 1L, 2L, 3L);

        try (SubstringIndexIterator iterator = new SubstringIndexIterator(tempFile, substring)) {
            List<Long> actualIndices = new ArrayList<>();
            while (iterator.hasNext()) {
                actualIndices.add(iterator.next());
            }
            assertEquals(expectedIndices, actualIndices);
        }
    }

    @Test
    void testWholeTextAsSubstring() throws IOException {
        Files.writeString(tempFile, "hello");
        String substring = "hello";
        List<Long> expectedIndices = List.of(0L);

        try (SubstringIndexIterator iterator = new SubstringIndexIterator(tempFile, substring)) {
            List<Long> actualIndices = new ArrayList<>();
            while (iterator.hasNext()) {
                actualIndices.add(iterator.next());
            }
            assertEquals(expectedIndices, actualIndices);
        }
    }

    @Test
    void testFindSubstringInLargeFile() throws IOException {
        int fileSize = 100 * 1024 * 1024;

        int blockSize = 1024;
        char[] buffer = new char[blockSize];
        Arrays.fill(buffer, 'a');

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            long written = 0;
            while (written < fileSize) {
                int bytesToWrite = (int) Math.min(blockSize, fileSize - written);
                writer.write(buffer, 0, bytesToWrite);
                written += bytesToWrite;
            }
        }

        String substring = "aaa";
        try (SubstringIndexIterator iterator = new SubstringIndexIterator(tempFile, substring)) {
            for (int i = 0; i < fileSize - 2; ++i) {
                assertTrue(iterator.hasNext());
                assertEquals(i, iterator.next());
            }
            assertFalse(iterator.hasNext());
        }
    }
}
