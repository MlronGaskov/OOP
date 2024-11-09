package ru.nsu.gaskov.text.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * KmpSubstringSearcher provides methods for finding occurrences
 * of a specified substring within various types of text sources.
 * It utilizes the Knuth-Morris-Pratt (KMP) algorithm for efficient substring search.
 * This class offers methods to find a single occurrence, all occurrences as
 * a list, or as a Stream or Iterator of indices.
 */
public class KmpSubstringSearcher {

    /**
     * An iterator for finding all occurrences of a specified substring in a BufferedReader.
     * This iterator searches the file using the Knuth-Morris-Pratt pattern matching algorithm,
     * returning the starting index of each occurrence of the substring in the text file.
     */
    private static class SubstringIndexIterator implements Iterator<Long> {
        private final BufferedReader reader;
        private final String substring;
        private final int[] prefixArray;
        private Long positionInText = 0L;
        private int positionInSubstring = 0;
        private Long nextSubstringIndex = null;

        /**
         * Constructs a new SubstringIndexIterator.
         */
        public SubstringIndexIterator(BufferedReader reader, String substring) {
            if (substring.isEmpty()) {
                throw new IllegalArgumentException("Substring cannot be empty.");
            }
            this.reader = reader;
            this.substring = substring;
            prefixArray = buildPrefixArray(substring);
        }

        /**
         * Checks if there is a next occurrence of the substring in the file.
         */
        @Override
        public boolean hasNext() {
            try {
                if (nextSubstringIndex == null) {
                    nextSubstringIndex = findNext();
                }
                return nextSubstringIndex != -1;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Returns the starting index of the next occurrence of the substring in the file.
         */
        @Override
        public Long next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Long result = nextSubstringIndex;
            nextSubstringIndex = null;
            return result;
        }

        /**
         * Finds the next occurrence of the substring in the text.
         */
        private Long findNext() throws IOException {
            int readChar;
            while ((readChar = reader.read()) != -1) {
                char textChar = (char) readChar;
                while (positionInSubstring > 0
                    && textChar != substring.charAt(positionInSubstring)) {
                    positionInSubstring = prefixArray[positionInSubstring - 1];
                }
                if (textChar == substring.charAt(positionInSubstring)) {
                    positionInSubstring++;
                }
                if (positionInSubstring == substring.length()) {
                    positionInSubstring = prefixArray[positionInSubstring - 1];
                    positionInText++;
                    return positionInText - substring.length();
                }
                positionInText++;
            }
            return -1L;
        }

        /**
         * Builds the prefix array for the KMP algorithm.
         */
        private static int[] buildPrefixArray(String string) {
            int[] prefixArray = new int[string.length()];
            int j = 0;
            prefixArray[0] = 0;
            for (int i = 1; i < string.length(); i++) {
                while (j > 0 && string.charAt(i) != string.charAt(j)) {
                    j = prefixArray[j - 1];
                }
                if (string.charAt(i) == string.charAt(j)) {
                    j++;
                }
                prefixArray[i] = j;
            }
            return prefixArray;
        }
    }

    /**
     * Finds the first occurrence of a substring in the BufferedReader.
     */
    public static long indexOf(BufferedReader reader, String substring) {
        Iterator<Long> iterator = iterate(reader, substring);
        return iterator.hasNext() ? iterator.next() : -1;
    }

    /**
     * Finds the first occurrence of a substring in the given String.
     */
    public static long indexOf(String text, String substring) throws IOException {
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            return indexOf(reader, substring);
        }
    }

    /**
     * Finds the first occurrence of a substring in the file.
     */
    public static long indexOf(Path filePath, String substring) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            return indexOf(reader, substring);
        }
    }

    /**
     * Finds all occurrences of a substring in the given BufferedReader.
     */
    public static List<Long> indexesOf(BufferedReader reader, String substring) {
        return indexesStream(reader, substring).toList();
    }

    /**
     * Finds all occurrences of a substring in the given text.
     */
    public static List<Long> indexesOf(String text, String substring) throws IOException {
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            return indexesOf(reader, substring);
        }
    }

    /**
     * Finds all occurrences of a substring in the given file.
     */
    public static List<Long> indexesOf(Path filePath, String substring) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            return indexesOf(reader, substring);
        }
    }

    /**
     * Returns an iterator for finding all occurrences of the substring.
     */
    public static Iterator<Long> iterate(BufferedReader reader, String substring) {
        if (substring.isEmpty()) {
            return List.of(0L).iterator();
        }
        return new SubstringIndexIterator(reader, substring);
    }

    /**
     * Returns a Stream of indices representing each occurrence of the substring.
     */
    public static Stream<Long> indexesStream(BufferedReader reader, String substring) {
        Iterable<Long> iterable = () -> iterate(reader, substring);
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
