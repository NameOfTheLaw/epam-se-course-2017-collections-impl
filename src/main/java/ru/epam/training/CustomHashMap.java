package ru.epam.training;

import java.util.*;

/**
 * <i>Hash table</i> implementation of the <code>Map</code> interface.
 *
 * <code>CustomHashMap</code> has inner array of buckets. Each time the map <i>load factor</i>
 * (<code>size/capacity</code>) is getting bigger than <code>0.75</code> capacity multiplies
 * by <code>3/2</code> and array of buckets is getting replaced to the bigger array.
 *
 * The performance of the <code>CustomHashMap</code> based on the hashcode realisation
 * of the keys.
 *
 * The <code>get</code>, <code>put</code>, <code>containsKey</code> operations runs in
 * constant time (exclude the situation then <code>size/capacity >= MAX_LOAD_FACTOR</code>).
 * The <code>containsValue</code> method runs throw all the keys in the map.
 *
 * <code>CustomHastMap</code> support null values and doesn't support null keys.
 *
 * @param <K> type of keys maintained by map.
 * @param <V> type of values maintained by map.
 */
public class CustomHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;

    private CustomEntry<K, V>[] buckets = new CustomEntry[DEFAULT_CAPACITY];
    private int size = 0;
    private int capacity = DEFAULT_CAPACITY;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        buckets = new CustomEntry[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    private void ensureCapacity() {
        if ((double) size / capacity > MAX_LOAD_FACTOR) {
            int newCapacity = capacity * 3 / 2 + 1;

            CustomEntry[] newBuckets = new CustomEntry[newCapacity];

            for (int i = 0; i < capacity; i++) {
                CustomEntry<K, V> currentEntry = buckets[i];
                while (currentEntry != null) {
                    putEntryInBucketsArray(newBuckets, new CustomEntry<>(currentEntry));
                    currentEntry = currentEntry.next;
                }
            }

            buckets = newBuckets;
            capacity = newCapacity;
        }
    }

    private void putEntryInBucketsArray(CustomEntry[] bucketsToPutIn, CustomEntry<K, V> entry) {
        int index = hash(entry.key, bucketsToPutIn.length);

        CustomEntry<K, V> bucket = bucketsToPutIn[index];
        if (bucket == null) {
            bucketsToPutIn[index] = entry;
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

    private class CustomEntry<K, V> {

        private final K key;
        private V value;
        private CustomEntry<K, V> next = null;

        CustomEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public CustomEntry(CustomEntry<K, V> currentEntry) {
            this.key = currentEntry.key;
            this.value = currentEntry.value;
        }

        public boolean hasNext() {
            return this.next != null;
        }
    }
}
