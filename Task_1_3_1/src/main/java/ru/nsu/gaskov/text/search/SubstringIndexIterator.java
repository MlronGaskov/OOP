package ru.nsu.gaskov.text.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator for finding all occurrences of a specified substring in a text file.
 */
public class SubstringIndexIterator implements Iterator<Long>, AutoCloseable {
    private final BufferedReader reader;
    private final String substring;
    private final int[] prefixArray;
    private Long positionInText = 0L;
    private int positionInSubstring = 0;
    private Long nextSubstringIndex;

    /**
     * Constructs a new SubstringIndexIterator.
     */
    public SubstringIndexIterator(Path filePath, String substring) throws IOException {
        if (substring.isEmpty()) {
            throw new IllegalArgumentException("Substring cannot be empty.");
        }
        this.reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
        this.substring = substring;
        this.prefixArray = new int[substring.length()];
        buildPrefixArray();
        nextSubstringIndex = findNext();
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
            while (positionInSubstring > 0 && textChar != substring.charAt(positionInSubstring)) {
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
    private void buildPrefixArray() {
        int j = 0;
        prefixArray[0] = 0;
        for (int i = 1; i < substring.length(); i++) {
            while (j > 0 && substring.charAt(i) != substring.charAt(j)) {
                j = prefixArray[j - 1];
            }
            if (substring.charAt(i) == substring.charAt(j)) {
                j++;
            }
            prefixArray[i] = j;
        }
    }

    /**
     * Closes the underlying BufferedReader.
     */
    @Override
    public void close() throws IOException {
        reader.close();
    }
}
