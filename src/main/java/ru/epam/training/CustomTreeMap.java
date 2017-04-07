package ru.epam.training;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <i>Red-black tree</i> implementation of the <code>Map</code> interface.
 *
 * <code>CustomTreeMap</code> is sorted according to the <code>compareTo</code> methods of the keys.
 *
 * Due to the nature of the <i>red-black tree</i> most operation (<code>get</code>, <code>put</code>,
 * <code>remove</code>, <code>containsKey</code>) runs in <code>log(n)</code> time.
 *
 * <code>CustomHastMap</code> support null values and doesn't support null keys.
 *
 * @param <K> type of keys maintained by map.
 * @param <V> type of values maintained by map.
 */
public class CustomTreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    private Node<K, V> root;
    private int size;

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

        return find(root, (K) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, value);
    }

    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);

        Node<K, V> findResult = find(root, (K) key);

        return findResult == null ? null : findResult.value;
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);

        ValuesPair valuesPair = new ValuesPair(value);
        root = put(root, key, valuesPair);
        return valuesPair.oldValue;
    }

    @Override
    public V remove(Object key) {
        Objects.requireNonNull(key);

        ValuesPair valuesPair = new ValuesPair();
        root = remove(root, (K) key, valuesPair);
        return valuesPair.oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        root = null;
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

    private boolean containsValue(Node<K, V> node, Object value) {
        if (node == null) return false;

        if (node.value == null) {
            if (value == null) return true;
        } else if (node.value.equals(value)) {
            return true;
        }
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    private Node<K, V> put(Node<K, V> node, K key, ValuesPair value) {
        if (node == null) {
            size++;
            return new Node<>(key, value.newValue, Node.RED);
        }

        if (node.key.equals(key)) {
            value.oldValue = node.value;
            node.value = value.newValue;
        } else if (node.key.compareTo(key) > 0) {
            node.left = put(node.left, key, value);
        } else {
            node.right = put(node.right, key, value);
        }

        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        return node;
    }

    private Node<K, V> find(Node<K, V> node, K key) {
        if (node == null) return null;

        if (node.key.equals(key)) {
            return node;
        } else if (node.key.compareTo(key) > 0) {
            return find(node.left, key);
        } else {
            return find(node.right, key);
        }
    }

    private Node<K, V> remove(Node<K, V> node, K key, ValuesPair valuesPair) {
        if (node == null) return null;

        if (node.key.equals(key)) {
            size--;
            valuesPair.oldValue = node.value;

            if (node.right == null) return node.left;
            if (node.left == null) return node.right;

            Node<K, V> nodeToDelete = node;

            node = min(nodeToDelete.right);
            node.right = deleteMin(nodeToDelete.right);
            node.left = nodeToDelete.left;
        } else if (node.key.compareTo(key) > 0) {
            node.left = remove(node.left, key, valuesPair);
        } else {
            node.right = remove(node.right, key, valuesPair);
        }
        return node;
    }

    private Node<K, V> deleteMin(Node<K, V> node) {
        if (node.left == null) return node.right;

        node.left = deleteMin(node.left);
        return node;
    }

    private Node<K, V> min(Node<K, V> node) {
        return node.left != null ? min(node.left) : node;
    }

    private boolean isRed(Node node) {
        return node != null && node.color == Node.RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;

        x.color = h.color;
        h.color = Node.RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;

        x.color = h.color;
        h.color = Node.RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = Node.RED;
        h.left.color = Node.BLACK;
        h.right.color = Node.BLACK;
    }

    private class Node<K extends Comparable<K>, V> {
        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private final K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;
        private boolean color;

        public Node(K key, V value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }

    }

    private class ValuesPair {
        private V newValue;
        private V oldValue;

        public ValuesPair(V newValue) {
            this.newValue = newValue;
        }

        public ValuesPair() {
        }
    }
}
