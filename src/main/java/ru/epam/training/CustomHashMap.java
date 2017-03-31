package ru.epam.training;

import java.util.*;

public class CustomHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private CustomEntry<K, V>[] buckets = new CustomEntry[DEFAULT_CAPACITY];
    private int size = 0;
    private int capacity = DEFAULT_CAPACITY;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean containsKey(Object key) {
        Objects.requireNonNull(key);

        int index = hash(key);

        CustomEntry<K, V> currentEntry = buckets[index];
        while (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                return true;
            }
            currentEntry = currentEntry.next;
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < capacity; i++) {
            CustomEntry<K, V> currentEntry = buckets[i];
            while (currentEntry != null) {
                if (currentEntry.value == null) {
                    if (value == null) {
                        return true;
                    }
                } else if (currentEntry.value.equals(value)) {
                    return true;
                }
                currentEntry = currentEntry.next;
            }
        }

        return false;
    }

    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);

        int index = hash(key);

        CustomEntry<K, V> currentEntry = buckets[index];
        while (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                return currentEntry.value;
            }
            currentEntry = currentEntry.next;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);

        ensureCapacity();

        int index = hash(key);
        CustomEntry<K, V> bucket = buckets[index];

        if (buckets[index] == null) {
            buckets[index] = new CustomEntry<>(key, value);
            size++;
        } else {
            while (bucket != null) {
                if (bucket.key.equals(key)) {
                    V returnedValue = bucket.value;
                    bucket.value = value;
                    return returnedValue;
                }
                if (bucket.next == null) {
                    bucket.next = new CustomEntry<>(key, value);
                    size++;
                }
                bucket = bucket.next;
            }
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int index = hash(key);

        CustomEntry<K, V> currentEntry = buckets[index];

        if (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                size--;

                V returnedValue = currentEntry.value;
                buckets[index] = currentEntry.next;

                return returnedValue;
            }
            while (currentEntry.next != null) {
                if (currentEntry.next.key.equals(key)) {
                    size--;

                    V returnedValue = currentEntry.next.value;
                    currentEntry.next = currentEntry.next.next;

                    return returnedValue;
                }
                currentEntry = currentEntry.next;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        buckets = new CustomEntry[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    private void ensureCapacity() {
        if ((double) size / capacity > LOAD_FACTOR) {
            int newCapacity = capacity * 3 / 2 + 1;

            CustomEntry[] newBuckets = new CustomEntry[newCapacity];

            for (int i = 0; i < capacity; i++) {
                CustomEntry<K, V> currentEntry = buckets[i];
                while (currentEntry != null) {
                    putEntryInBucketsArray(newBuckets, currentEntry);
                    currentEntry = currentEntry.next;
                }
            }

            buckets = newBuckets;
            capacity = newCapacity;
        }
    }

    private void putEntryInBucketsArray(CustomEntry[] buckets, CustomEntry<K, V> entry) {
        entry.next = null;

        int index = hash(entry.key);

        CustomEntry<K, V> bucket = buckets[index];
        if (bucket == null) {
            buckets[index] = entry;
        } else {
            while (bucket.hasNext()) {
                bucket = bucket.next;
            }
            bucket.next = entry;
        }
    }

    private int hash(Object key) {
        return hash(key, capacity);
    }

    private int hash(Object key, int capacity) {
        return key.hashCode() % capacity;
    }

    private class CustomEntry<K, V> implements Iterator<CustomEntry<K, V>> {

        private final K key;
        private V value;
        private CustomEntry<K, V> next = null;

        CustomEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public boolean hasNext() {
            return this.next != null;
        }

        public CustomEntry<K, V> next() {
            return this.next;
        }
    }
}
