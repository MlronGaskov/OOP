package ru.nsu.gaskov.hashtable;

import java.util.Objects;

/**
 * Represents a key-value pair entry in a hash table.
 */
public class Entry<K, V> {
    private final K key;
    private final V value;

    /**
     * Constructs an entry with the specified key and value.
     */
    Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of this entry.
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns the value associated with this entry.
     */
    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return key.equals(((Entry<?, ?>) obj).key)
            && value.equals(((Entry<?, ?>) obj).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
