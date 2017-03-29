package ru.epam.training;

import java.util.*;

public class CustomHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 16;

    private CustomEntry<K, V>[] buckets = new CustomEntry[DEFAULT_CAPACITY];

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean containsKey(Object key) {
        Objects.requireNonNull(key);

        CustomEntry<K, V> bucket = buckets[0];
        if (bucket != null) {
            while (bucket!=null) {
                if (bucket.key.equals(key)) {
                    return true;
                }
                bucket = bucket.next;
            }
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return buckets[0].value.equals(value);
    }

    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);

        CustomEntry<K, V> bucket = buckets[0];
        if (bucket != null) {
            while (bucket!=null) {
                System.out.println(bucket.key);
                if (bucket.key.equals(key)) {
                    return bucket.value;
                }
                bucket = bucket.next;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);

        V returnedValue = null;
        if (buckets[0] == null) {
            buckets[0] = new CustomEntry<>(key, value);
        } else {
            CustomEntry<K, V> bucket = buckets[0];
            while (bucket.hasNext()) {
                bucket = bucket.next;
            }
            if (bucket.key.equals(key)) {
                returnedValue = bucket.value;
                bucket.value = value;
            } else {
                bucket.next = new CustomEntry<>(key, value);
            }
        }
        return returnedValue;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        buckets = new CustomEntry[DEFAULT_CAPACITY];
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

        void setNext(CustomEntry<K, V> next) {
            this.next = next;
        }
    }
}
