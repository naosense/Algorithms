package com.pingao.assignment.week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created by pingao on 2018/5/22.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final Object[] EMPTY_ARRAY = {};
    private Object[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = EMPTY_ARRAY;
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

        // 当数组满时将数组加倍
        growIfFull();

        items[size++] = item;
    }

    private void growIfFull() {
        if (size == items.length) {
            // size为0要特殊处理因为0的多少倍都为0
            if (size == 0) {
                items = resize(1);
            } else {
                items = resize(size * 2);
            }
        }
    }

    private Object[] resize(int capacity) {
        Object[] a = new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        return a;
    }

    // remove and return a random item
    // how to make dequeue in constant time?
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(0, size);
        Item item = get(index);
        // 因为是随机返回，所以这里不用依次挪动index到size-1所有这些元素，只需要用下标size-1的元素
        // 填到下标index就可以了
        items[index] = items[size - 1];
        items[--size] = null;

        // 当size为1/4 * length时将数组缩小
        shrinkIfQuarter();
        return item;
    }

    private void shrinkIfQuarter() {
        if (size == 0) {
            items = EMPTY_ARRAY;
        } else if (4 * size <= items.length) {
            items = resize(size * 2);
        } else {
            // ignore
        }
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
        private final int[] index = StdRandom.permutation(size);
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
