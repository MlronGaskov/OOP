package ru.nsu.gaskov.text.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class KmpSubstringSearcherTest {

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
    void testIndexOfInString() throws IOException {
        String text = "hello world";
        String substring = "world";
        long expectedIndex = 6;
        assertEquals(expectedIndex, KmpSubstringSearcher.indexOf(text, substring));

        assertEquals(-1, KmpSubstringSearcher.indexOf(text, "notfound"));
    }

    @Test
    void testIndexesOfInResourcesFile() throws IOException {
        Path filePath = Paths.get("src/test/resources/testfile.txt");
        assertTrue(Files.exists(filePath));
        BufferedReader reader = Files.newBufferedReader(filePath);
        assertEquals(10, KmpSubstringSearcher.indexOf(reader, "holaa"));
        reader.close();
    }

    @Test
    void testIndexOfInFile() throws IOException {
        String text = "file content with search term";
        Files.writeString(tempFile, text);

        long expectedIndex = 18;
        assertEquals(expectedIndex, KmpSubstringSearcher.indexOf(tempFile, "search"));
        assertEquals(-1, KmpSubstringSearcher.indexOf(tempFile, "missing"));
    }

    @Test
    void testIndexOfInBufferedReader() throws IOException {
        String text = "sample text for testing";
        BufferedReader reader = new BufferedReader(new StringReader(text));
        long expectedIndex = 7;
        assertEquals(expectedIndex, KmpSubstringSearcher.indexOf(reader, "text"));
        reader.close();

        BufferedReader missingReader = new BufferedReader(new StringReader(text));
        assertEquals(-1, KmpSubstringSearcher.indexOf(missingReader, "notfound"));
        missingReader.close();
    }

    @Test
    void testIndexesOfInString() throws IOException {
        String text = "abracadabra";
        String substring = "abra";
        List<Long> expectedIndices = List.of(0L, 7L);
        assertEquals(expectedIndices, KmpSubstringSearcher.indexesOf(text, substring));

        assertTrue(KmpSubstringSearcher.indexesOf(text, "notfound").isEmpty());
    }

    @Test
    void testIndexesOfInFile() throws IOException {
        String text = "repeat pattern repeat pattern repeat";
        Files.writeString(tempFile, text);

        String substring = "repeat";
        List<Long> expectedIndices = List.of(0L, 15L, 30L);
        assertEquals(expectedIndices, KmpSubstringSearcher.indexesOf(tempFile, substring));

        assertTrue(KmpSubstringSearcher.indexesOf(tempFile, "missing").isEmpty());
    }

    @Test
    void testIndexesOfInBufferedReader() throws IOException {
        String text = "find this, find that, find everything";
        BufferedReader reader = new BufferedReader(new StringReader(text));

        String substring = "find";
        List<Long> expectedIndices = List.of(0L, 11L, 22L);
        assertEquals(expectedIndices, KmpSubstringSearcher.indexesOf(reader, substring));
        reader.close();

        BufferedReader missingReader = new BufferedReader(new StringReader(text));
        assertTrue(KmpSubstringSearcher.indexesOf(missingReader, "missing").isEmpty());
        missingReader.close();
    }

    @Test
    void testIndexesStreamInBufferedReader() {
        String text = "search in stream search in stream search";
        BufferedReader reader = new BufferedReader(new StringReader(text));

        String substring = "search";
        List<Long> expectedIndices = List.of(0L, 17L, 34L);

        List<Long> actualIndices = KmpSubstringSearcher.indexesStream(reader, substring)
            .collect(Collectors.toList());
        assertEquals(expectedIndices, actualIndices);

        BufferedReader missingReader = new BufferedReader(new StringReader(text));
        assertTrue(
            KmpSubstringSearcher.indexesStream(missingReader, "missing").findAny().isEmpty()
        );
    }

    @Test
    void testIterateInBufferedReader() throws IOException {
        String text = "iterate through this iterate through this";
        BufferedReader reader = new BufferedReader(new StringReader(text));

        String substring = "iterate";
        List<Long> expectedIndices = List.of(0L, 21L);

        List<Long> actualIndices = new ArrayList<>();
        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);

        while (iterator.hasNext()) {
            actualIndices.add(iterator.next());
        }

        assertEquals(expectedIndices, actualIndices);
        reader.close();

        BufferedReader missingReader = new BufferedReader(new StringReader(text));
        Iterator<Long> missingIterator = KmpSubstringSearcher
            .iterate(missingReader, "missing");
        
        assertFalse(missingIterator.hasNext());
        missingReader.close();
    }

    @Test
    void testFindSingleSubstringIndex() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("ababcd"));
        String substring = "abcd";
        List<Long> expectedIndices = List.of(2L);

        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);
        List<Long> actualIndices = new ArrayList<>();
        while (iterator.hasNext()) {
            actualIndices.add(iterator.next());
        }
        assertEquals(expectedIndices, actualIndices);
        reader.close();
    }

    @Test
    void testNoOccurrences() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("abcdefg"));
        String substring = "xyz";
        List<Long> expectedIndices = List.of();

        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);
        List<Long> actualIndices = new ArrayList<>();
        while (iterator.hasNext()) {
            actualIndices.add(iterator.next());
        }
        assertEquals(expectedIndices, actualIndices);
        reader.close();
    }

    @Test
    void testMultipleOccurrences() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("ababcabcabc"));
        String substring = "abc";
        List<Long> expectedIndices = List.of(2L, 5L, 8L);

        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);
        List<Long> actualIndices = new ArrayList<>();
        while (iterator.hasNext()) {
            actualIndices.add(iterator.next());
        }
        assertEquals(expectedIndices, actualIndices);
        reader.close();
    }

    @Test
    void testBackSlash() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("aba\nbc\nabcabc\n\n"));
        String substring = "\n";
        List<Long> expectedIndices = List.of(3L, 6L, 13L, 14L);

        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);
        List<Long> actualIndices = new ArrayList<>();
        while (iterator.hasNext()) {
            actualIndices.add(iterator.next());
        }
        assertEquals(expectedIndices, actualIndices);
        reader.close();
    }

    @Test
    void testSubstringAtBeginning() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("testabc"));
        String substring = "test";
        List<Long> expectedIndices = List.of(0L);

        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);
        List<Long> actualIndices = new ArrayList<>();
        while (iterator.hasNext()) {
            actualIndices.add(iterator.next());
        }
        assertEquals(expectedIndices, actualIndices);
        reader.close();
    }

    @Test
    void testSubstringAtEnd() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("abcdtest"));
        String substring = "test";
        List<Long> expectedIndices = List.of(4L);

        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);
        List<Long> actualIndices = new ArrayList<>();
        while (iterator.hasNext()) {
            actualIndices.add(iterator.next());
        }
        assertEquals(expectedIndices, actualIndices);
        reader.close();
    }

    @Test
    void testSingleCharacterSubstring() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("aaaa"));
        String substring = "a";
        List<Long> expectedIndices = List.of(0L, 1L, 2L, 3L);

        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);
        List<Long> actualIndices = new ArrayList<>();
        while (iterator.hasNext()) {
            actualIndices.add(iterator.next());
        }
        assertEquals(expectedIndices, actualIndices);
        reader.close();
    }

    @Test
    void testWholeTextAsSubstring() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("hello"));
        String substring = "hello";
        List<Long> expectedIndices = List.of(0L);

        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);
        List<Long> actualIndices = new ArrayList<>();
        while (iterator.hasNext()) {
            actualIndices.add(iterator.next());
        }
        assertEquals(expectedIndices, actualIndices);
        reader.close();
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

        BufferedReader reader = Files.newBufferedReader(tempFile);
        String substring = "aaa";

        Iterator<Long> iterator = KmpSubstringSearcher.iterate(reader, substring);
        for (int i = 0; i < fileSize - 2; ++i) {
            assertTrue(iterator.hasNext());
            assertEquals(i, iterator.next());
        }
        reader.close();
    }
}
