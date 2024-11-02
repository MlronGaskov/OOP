package ru.nsu.gaskov.hashtable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A simple implementation of a hash table with open addressing and linear probing.
 */
public class HashTable<K, V> {
    private static final float LOAD_FACTOR = 0.75f;
    private final Entry<K, V> removed = new Entry<>(null, null);

    private long size = 0;
    private long modCount = 0;
    private List<Entry<K, V>> table;

    /**
     * Constructs an empty hash table with the specified initial capacity.
     */
    public HashTable(int initCapacity) {
        table = new ArrayList<>(initCapacity);
        for (int i = 0; i < initCapacity; i++) {
            table.add(null);
        }
    }

    /**
     * Computes the hash value for the specified key.
     */
    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % table.size());
    }

    /**
     * Resizes the hash table if the load factor exceeds the threshold.
     */
    private void resize() {
        if (size < LOAD_FACTOR * table.size()) {
            return;
        }
        List<Entry<K, V>> oldTable = table;
        table = new ArrayList<>(2 * oldTable.size());
        for (int i = 0; i < 2 * oldTable.size(); i++) {
            table.add(null);
        }
        size = 0;
        for (Entry<K, V> entry : oldTable) {
            if (entry != null && entry != removed) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Associates the specified value with the specified key in this hash table.
     * If the hash table already contains a mapping for the key, the old value
     * is replaced by the specified value.
     */
    public void put(K key, V value) {
        int index = hash(key);
        while (table.get(index) != null && table.get(index) != removed) {
            if (table.get(index).getKey().equals(key)) {
                table.set(index, new Entry<>(key, value));
                modCount++;
                return;
            }
            index = (index + 1) % table.size();
        }
        table.set(index, new Entry<>(key, value));
        size++;
        modCount++;
        resize();
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * hash table contains no mapping for the key.
     */
    public V get(K key) {
        int index = hash(key);
        while (table.get(index) != null) {
            if (table.get(index) != removed && table.get(index).getKey().equals(key)) {
                return table.get(index).getValue();
            }
            index = (index + 1) % table.size();
        }
        return null;
    }

    /**
     * Removes the mapping for the specified key from this hash table if present.
     */
    public boolean remove(K key) {
        int index = hash(key);
        while (table.get(index) != null) {
            if (table.get(index) != removed && table.get(index).getKey().equals(key)) {
                table.set(index, removed);
                size--;
                modCount++;
                return true;
            }
            index = (index + 1) % table.size();
        }
        return false;
    }

    /**
     * Updates the value for the specified key. If the key does not exist, it
     * adds the key-value pair to the hash table.
     */
    public void update(K key, V value) {
        put(key, value);
    }

    /**
     * Returns true if this hash table contains a mapping for the specified key.
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the number of key-value pairs in this hash table.
     * Removed key-value pairs can be included.
     */
    public long size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object.getClass() != getClass()) {
            return false;
        }
        HashTable<K, V> other = (HashTable<K, V>) object;
        if (this.size != other.size) {
            return false;
        }
        for (Entry<K, V> entry : this.table) {
            if (entry != null && entry != removed) {
                V otherValue = other.get(entry.getKey());
                if (otherValue == null || !otherValue.equals(entry.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        List<Entry<K, V>> sortedEntries = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null && entry != removed) {
                sortedEntries.add(entry);
            }
        }
        sortedEntries.sort(Comparator.comparing(a -> a.getKey().toString()));
        return Objects.hash(size, sortedEntries);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Entry<K, V> entry : table) {
            if (entry != null && entry != removed) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Returns an iterator over the entries in this hash table.
     */
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private final long expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                while (index < table.size() && (table.get(index) == null || table.get(index) == removed)) {
                    index++;
                }
                return index < table.size();
            }

            @Override
            public Entry<K, V> next() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table.get(index++);
            }
        };
    }
}
