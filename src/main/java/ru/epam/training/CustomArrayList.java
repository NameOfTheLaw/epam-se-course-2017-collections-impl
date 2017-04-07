package ru.epam.training;

import java.util.*;

/**
 * <i>Array</i> implementation of the <code>List</code> interface.
 *
 * Inner array is resizable. Each time list has his <code>capacity</code> and <code>size</code>.
 * Every time list size is getting equals to capacity it multiplies by <code>3/2</code>.
 *
 * The <code>get</code>, <code>set</code> methods run in constant time (exclude the situation
 * then <code></code>size == capacity</code>). The <code>add</code>, <code>remove</code> methods
 * create new array. So this operations time based on <code>System.arrayCopy</code> native implementation.
 *
 * <code>CustomArrayList</code> supports <code>null</code> values.
 *
 * @param <T> type of values maintained by list.
 */
public class CustomArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] data = new Object[DEFAULT_CAPACITY];
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i] == null) {
                if (o == null) {
                    return true;
                }
            } else if (data[i].equals(o)) {
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
        ensureCapacity();

        data[size++] = t;
        return false;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                remove(i);
                return true;
            }
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
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIfIndexIsAppropriateToGet(index);

        return (T) data[index];
    }

    @Override
    public T set(int index, T element) {
        checkIfIndexIsAppropriateToGet(index);

        T oldValue = (T) data[index];
        data[index] = element;

        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        ensureCapacity();
        checkIfIndexIsAppropriateToAdd(index);

        int arrayTailLength = data.length - index;
        System.arraycopy(data, index, data, index + 1, arrayTailLength-1);
        data[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        checkIfIndexIsAppropriateToGet(index);

        T oldValue = (T) data[index];

        int arrayTailLength = data.length - index;
        System.arraycopy(data, index + 1, data, index, arrayTailLength-1);
        size--;

        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size-1; i >= 0; i--) {
            if (data[i].equals(o)) return i;
        }
        return -1;
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

    private void checkIfIndexIsAppropriateToAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIfIndexIsAppropriateToGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int newLength = data.length * 3 / 2 + 1;
            data = Arrays.copyOf(data, newLength);
        }
    }
}
