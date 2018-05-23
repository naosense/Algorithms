package com.pingao.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created by pingao on 2018/5/23.
 */
public class Stack<E> implements Iterable<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object[] items;
    private int size;

    public Stack() {
        this.items = new Object[DEFAULT_CAPACITY];
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

        if (size == items.length) {
            items = resize(items.length * 2);
        }

        items[size++] = e;
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

        if (4 * size <= items.length && items.length >= 2 * DEFAULT_CAPACITY) {
            items = resize(items.length / 2);
        }
        return e;
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
        stack.forEach(System.out::println);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

}
