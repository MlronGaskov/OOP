package ru.nsu.gaskov.hashTable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTable<K, V> {
    private static final float LOAD_FACTOR = 0.75f;
    private final Entry<K, V> REMOVED = new Entry<>(null, null);

    private int size = 0;
    private int modCount = 0;
    private Entry<K, V>[] table;

    @SuppressWarnings("unchecked")
    public HashTable(int initCapacity) {
        table = new Entry[initCapacity];
    }

    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % table.length);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        if (size < LOAD_FACTOR * table.length) {
            return;
        }
        Entry<K, V>[] oldTable = table;
        table = new Entry[2 * oldTable.length];
        size = 0;
        for (Entry<K, V> entry : oldTable) {
            if (entry != null && entry != REMOVED) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void put(K key, V value) {
        int index = hash(key);
        while (table[index] != null && table[index] != REMOVED) {
            if (table[index].getKey().equals(key)) {
                table[index] = new Entry<>(key, value);
                modCount++;
                return;
            }
            index = (index + 1) % table.length;
        }
        table[index] = new Entry<>(key, value);
        size++;
        modCount++;
        resize();
    }

    public V get(K key) {
        int index = hash(key);
        while (table[index] != null) {
            if (table[index] != REMOVED && table[index].getKey().equals(key)) {
                return table[index].getValue();
            }
            index = (index + 1) % table.length;
        }
        return null;
    }

    public boolean remove(K key) {
        int index = hash(key);
        while (table[index] != null) {
            if (table[index] != REMOVED && table[index].getKey().equals(key)) {
                table[index] = REMOVED;
                size--;
                modCount++;
                return true;
            }
            index = (index + 1) % table.length;
        }
        return false;
    }

    public void update(K key, V value) {
        put(key, value);
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean equals(HashTable<K, V> other) {
        if (this.size != other.size) {
            return false;
        }
        for (Entry<K, V> entry : this.table) {
            if (entry != null && entry != REMOVED) {
                V otherValue = other.get(entry.getKey());
                if (otherValue == null || !otherValue.equals(entry.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Entry<K, V> entry : table) {
            if (entry != null && entry != REMOVED) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        return sb.toString();
    }

    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                while (index < table.length && (table[index] == null || table[index] == REMOVED)) {
                    index++;
                }
                return index < table.length;
            }

            @Override
            public Entry<K, V> next() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++];
            }
        };
    }
}
