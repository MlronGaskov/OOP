package ru.nsu.gaskov.hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>(2);
    }

    @Test
    void testPutAndGet() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);
        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
    }

    @Test
    void testUpdate() {
        hashTable.put("one", 1);
        hashTable.update("one", 10);
        assertEquals(10, hashTable.get("one"));
    }

    @Test
    void testRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        assertTrue(hashTable.remove("one"));
        assertNull(hashTable.get("one"));
        assertFalse(hashTable.containsKey("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(1, hashTable.size());
    }

    @Test
    void testContainsKey() {
        hashTable.put("one", 1);
        assertTrue(hashTable.containsKey("one"));
        assertFalse(hashTable.containsKey("two"));
    }

    @Test
    void testResize() {
        for (int i = 0; i < 20; i++) {
            hashTable.put("key" + i, i);
        }
        assertEquals(20, hashTable.size());
        for (int i = 0; i < 20; i++) {
            assertEquals(i, hashTable.get("key" + i));
        }
    }

    @Test
    void testToString() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);
        String result = hashTable.toString();
        assertTrue(result.contains("one=1"));
        assertTrue(result.contains("two=2"));
        assertTrue(result.contains("three=3"));
    }

    @Test
    void testIterator() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        Iterator<Entry<String, Integer>> iterator = hashTable.iterator();
        assertTrue(iterator.hasNext());

        Entry<String, Integer> entry1 = iterator.next();
        assertNotNull(entry1);
        Entry<String, Integer> entry2 = iterator.next();
        assertNotNull(entry2);
        Entry<String, Integer> entry3 = iterator.next();
        assertNotNull(entry3);
        assertFalse(iterator.hasNext());

        assertTrue(List.of(entry1, entry2, entry3).contains(new Entry<>("one", 1)));
        assertTrue(List.of(entry1, entry2, entry3).contains(new Entry<>("two", 2)));
        assertTrue(List.of(entry1, entry2, entry3).contains(new Entry<>("three", 3)));
    }

    @Test
    void testConcurrentModificationException() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Iterator<Entry<String, Integer>> iterator = hashTable.iterator();
        hashTable.put("three", 3);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> otherTable = new HashTable<>(1);
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        otherTable.put("one", 1);
        otherTable.put("two", 2);

        assertEquals(hashTable, otherTable);
        assertEquals(hashTable.hashCode(), otherTable.hashCode());

        otherTable.put("three", 3);
        assertNotEquals(hashTable, otherTable);
        assertNotEquals(hashTable.hashCode(), otherTable.hashCode());
    }
}