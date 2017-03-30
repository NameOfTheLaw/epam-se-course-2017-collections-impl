package ru.epam.training;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CustomTreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    private Node<K, V> root;
    private int size;

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

        if (root == null) return false;
        return find(root, (K) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, value);
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        ValueContainer valueContainer = new ValueContainer(value);
        root = put(root, key, valueContainer);
        return valueContainer.oldValue;
    }

    @Override
    public V remove(Object key) {
        ValueContainer valueContainer = new ValueContainer();
        root = remove(root, (K) key, valueContainer);
        return valueContainer.oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        root = null;
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

    private boolean containsValue(Node<K,V> node, Object value) {
        if (node==null) return false;
        if (node.value == null) {
            if (value == null) {
                return true;
            }
        } else {
            if (node.value.equals(value)) {
                return true;
            }
        }
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    private Node<K, V> put(Node<K, V> node, K key, ValueContainer value) {
        if (node == null) {
            size++;
            return new Node<>(key, value.newValue);
        }
        if (node.key.equals(key)) {
            value.oldValue = node.value;
            node.value = value.newValue;
        } else if (node.key.compareTo(key) > 0) {
            node.left = put(node.left, key, value);
        } else {
            node.right = put(node.right, key, value);
        }
        return node;
    }

    private Node<K, V> find(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        if (node.key.equals(key)) {
            return node;
        } else if (node.key.compareTo(key) > 0) {
            return find(node.left, key);
        } else {
            return find(node.right, key);
        }
    }

    private Node<K,V> remove(Node<K,V> node, K key, ValueContainer valueContainer) {
        if (node == null) {
            return null;
        }

        if (node.key.equals(key)) {
            valueContainer.oldValue = node.value;
            size--;
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;

            Node<K,V> nodeToDelete = node;

            node = min(nodeToDelete.right);
            node.right = deleteMin(nodeToDelete.right);
            node.left = nodeToDelete.left;
        } else if (node.key.compareTo(key) > 0) {
            node.left = remove(node.left, key, valueContainer);
        } else {
            node.right = remove(node.right, key, valueContainer);
        }
        return node;
    }

    private Node<K, V> deleteMin(Node<K, V> node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    private Node<K, V> min(Node<K, V> node) {
        if (node.left !=null) {
            return min(node.left);
        }
        return node;
    }

    private class Node<K extends Comparable<K>, V> {
        private final K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    private class ValueContainer {
        private V newValue;
        private V oldValue;

        public ValueContainer(V newValue) {
            this.newValue = newValue;
        }

        public ValueContainer() {}
    }
}
