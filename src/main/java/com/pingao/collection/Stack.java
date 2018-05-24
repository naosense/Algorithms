package com.pingao.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created by pingao on 2018/5/23.
 */
public class Stack<E> implements Iterable<E> {
    private static final Object[] EMPTY_ARRAY = {};
    private Object[] items;
    private int size;

    public Stack() {
        this.items = EMPTY_ARRAY;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(E e) {
        if (e == null) {
            throw new IllegalArgumentException("element must not be null");
        }

        growIfFull();

        items[size++] = e;
    }

    private void growIfFull() {
        if (size == items.length) {
            if (size == 0) {
                items = resize(1);
            } else {
                items = resize(items.length * 2);
            }
        }
    }

    private Object[] resize(int capacity) {
        Object[] a = new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        return a;
    }

    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        E e = get(size - 1);
        // 防止内存泄漏
        items[--size] = null;
        shrinkIfQuarter();
        return e;
    }

    private void shrinkIfQuarter() {
        if (size == 0) {
            items = EMPTY_ARRAY;
        } else if (4 * size <= items.length) {
            items = resize(items.length / 2);
        } else {
            // ignore
        }
    }

    @SuppressWarnings("unchecked")
    private E get(int index) {
        return (E) items[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<E> {
        private int p = size;

        @Override
        public boolean hasNext() {
            return p > 0;
        }

        @Override
        public E next() {
            return get(--p);
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.forEach(System.out::println);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
