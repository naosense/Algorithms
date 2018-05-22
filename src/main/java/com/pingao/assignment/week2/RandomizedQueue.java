package com.pingao.assignment.week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created by pingao on 2018/5/22.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int DEFAULT_CAPACITY = 4;
    private Object[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = new Object[DEFAULT_CAPACITY];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // double item array
        if (size == items.length) {
            items = resize(items.length * 2);
        }

        items[size++] = item;
    }

    private Object[] resize(int capacity) {
        Object[] a = new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        return a;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(0, size);
        Item item = get(index);
        for (int i = index; i < size; i++) {
            if (i == size - 1) {
                items[i] = null;
            } else {
                items[i] = items[i + 1];
            }
        }
        size--;
        // items.length >= 2 * DEFAULT_CAPACITY确保items的最小容量为DEFAULT_CAPACITY
        // 不会缩小为0
        if (4 * size <= items.length && items.length >= 2 * DEFAULT_CAPACITY) {
            items = resize(items.length / 2);
        }
        return item;
    }

    private Item get(int index) {
        return (Item) items[index];
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(0, size);
        return get(index);
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] index = StdRandom.permutation(size);
        private int p;

        @Override
        public boolean hasNext() {
            return p < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return get(index[p++]);
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 1000; i++) {
            if (rq.isEmpty()) {
                rq.enqueue(StdRandom.uniform(10));
            } else {
                double p = StdRandom.uniform(0.0, 1.0);
                if (p > 0.8) {
                    rq.enqueue(StdRandom.uniform(10));
                } else {
                    rq.dequeue();
                }
            }
        }
    }
}
