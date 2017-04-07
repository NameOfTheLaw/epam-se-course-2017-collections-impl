package ru.epam.training;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * <i>Singly linked list</i> implementation of the <code>List</code> interface.
 *
 * <code>head</code> element of the list is pseudo element. It has no value in it and doesn't
 * count in list size. It only has link to the first element of the <i>linked list</i>.
 *
 * Due to the nature of the linked list most operation runs in linear time.
 *
 * <code>CustomArrayList</code> supports <code>null</code> values.
 *
 * @param <T> type of values maintained by list.
 */
public class CustomLinkedList<T> implements List<T> {

    private Node<T> head = new Node<>(null);
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return !head.hasNext();
    }

    @Override
    public boolean contains(Object o) {
        Node<T> node = head;
        while (node.hasNext()) {
            node = node.next;
            if (node.value == null) {
                if (o == null) {
                    return true;
                }
            } else if (node.value.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        Node<T> iterator = head;
        while (iterator.hasNext()) {
            iterator = iterator.next;
        }
        iterator.next = new Node<>(t);
        size++;
        return false;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> current = head.next;
        Node<T> prev = head;
        while (current != null) {
            if (o.equals(current.value)) {
                prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        head = new Node<>(null);
        size = 0;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(int index, T element) {
        checkIfIndexIsAppropriateToGet(index);

        Node<T> NodeByIndex = getNodeByIndex(index);

        T oldValue = NodeByIndex.value;
        NodeByIndex.value = element;

        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        checkIfIndexIsAppropriateToAdd(index);

        Node<T> currentNode = head;
        if (index != 0) {
            currentNode = getNodeByIndex(index-1);
        }

        if (currentNode.hasNext()) {
            Node<T> newNode = new Node<>(element);
            newNode.next = currentNode.next;
            currentNode.next = newNode;
        } else {
            currentNode.next = new Node<>(element);
        }

        size++;
    }

    @Override
    public T remove(int index) {
        checkIfIndexIsAppropriateToGet(index);

        Node<T> current = getNodeByIndex(index - 1);
        size--;
        T value = current.next.value;
        current.next = current.next.next;
        return value;
    }

    @Override
    public int indexOf(Object o) {
        Node<T> currentNode;
        int i;
        for (i = 0, currentNode = head.next; currentNode != null; i++, currentNode = currentNode.next) {
            if (currentNode.value.equals(o)) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<T> currentNode;
        int lastIndexOf = -1;
        int i;
        for (i = 0, currentNode = head.next; currentNode != null; i++, currentNode = currentNode.next) {
            if (currentNode.value.equals(o)) lastIndexOf = i;
        }
        return lastIndexOf;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private Node<T> getNodeByIndex(int index) {
        checkIfIndexIsAppropriateToGet(index);

        Node<T> current = head.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIfIndexIsAppropriateToGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIfIndexIsAppropriateToAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class Node<T> {

        private Node<T> next;
        private T value;

        public Node(T value) {
            this.value = value;
        }

        public boolean hasNext() {
            return next != null;
        }

    }
}
