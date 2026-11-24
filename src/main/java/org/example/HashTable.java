package org.example;

import java.io.Serializable;

public class HashTable<V> implements Serializable {

    private static class HashNode<V> implements Serializable {
        String key;
        V value;
        HashNode<V> next;

        HashNode(String key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashNode<V>[] table;
    private int size;

    public HashTable() {
        this(11);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        table = new HashNode[capacity];
        size = 0;
    }

    public int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) % table.length;
        }
        return hash;
    }

    public void put(String key, V value) {
        int index = hash(key);
        HashNode<V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        HashNode<V> newNode = new HashNode<>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;

        if (size > table.length * 0.75) {
            resize();
        }
    }

    public V get(String key) {
        int index = hash(key);
        HashNode<V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public V remove(String key) {
        int index = hash(key);
        HashNode<V> current = table[index];
        HashNode<V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public CustomLinkedList<V> values() {
        CustomLinkedList<V> list = new CustomLinkedList<>();
        for (int i = 0; i < table.length; i++) {
            HashNode<V> current = table[i];
            while (current != null) {
                list.add(current.value);
                current = current.next;
            }
        }
        return list;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return table.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        HashNode<V>[] oldTable = table;
        table = new HashNode[oldTable.length * 2 + 1];
        size = 0;

        for (int i = 0; i < oldTable.length; i++) {
            HashNode<V> current = oldTable[i];
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    public void displayTable() {
        for (int i = 0; i < table.length; i++) {
            System.out.print(i + ": ");
            HashNode<V> current = table[i];
            while (current != null) {
                System.out.print(current.key + " -> ");
                current = current.next;
            }
            System.out.println("null");
        }
    }
}
