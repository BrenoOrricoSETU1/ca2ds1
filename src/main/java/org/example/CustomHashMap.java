package org.example;

import java.io.Serializable;

public class CustomHashMap<V> implements Serializable {

    private static class Entry<V> implements Serializable {
        private final String key;
        private V value;
        private Entry<V> next;

        Entry(String key, V value, Entry<V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private static final int DEFAULT_CAPACITY = 31;
    private static final double MAX_LOAD_FACTOR = 0.75;

    private Entry<V>[] buckets;
    private int size;

    public CustomHashMap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public CustomHashMap(int capacity) {
        buckets = (Entry<V>[]) new Entry[Math.max(capacity, 1)];
    }

    public int hash(String key) {
        long hash = 7;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) % buckets.length;
        }
        return (int) hash;
    }

    public void put(String key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = hash(key);
        for (Entry<V> entry = buckets[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        buckets[index] = new Entry<>(key, value, buckets[index]);
        size++;
        if ((double) size / buckets.length > MAX_LOAD_FACTOR) {
            resize();
        }
    }

    public V get(String key) {
        if (key == null) {
            return null;
        }
        for (Entry<V> entry = buckets[hash(key)]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public V remove(String key) {
        if (key == null) {
            return null;
        }
        int index = hash(key);
        Entry<V> previous = null;
        for (Entry<V> entry = buckets[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = entry.next;
                } else {
                    previous.next = entry.next;
                }
                size--;
                return entry.value;
            }
            previous = entry;
        }
        return null;
    }

    public CustomLinkedList<V> values() {
        CustomLinkedList<V> values = new CustomLinkedList<>();
        for (Entry<V> bucket : buckets) {
            for (Entry<V> entry = bucket; entry != null; entry = entry.next) {
                values.add(entry.value);
            }
        }
        return values;
    }

    public CustomLinkedList<String> keys() {
        CustomLinkedList<String> keys = new CustomLinkedList<>();
        for (Entry<V> bucket : buckets) {
            for (Entry<V> entry = bucket; entry != null; entry = entry.next) {
                keys.add(entry.key);
            }
        }
        return keys;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<V>[] oldBuckets = buckets;
        buckets = (Entry<V>[]) new Entry[oldBuckets.length * 2 + 1];
        size = 0;
        for (Entry<V> bucket : oldBuckets) {
            for (Entry<V> entry = bucket; entry != null; entry = entry.next) {
                put(entry.key, entry.value);
            }
        }
    }
}
