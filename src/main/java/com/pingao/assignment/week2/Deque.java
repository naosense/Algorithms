package com.pingao.assignment.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created by pingao on 2018/5/22.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> node = new Node<>();
        node.item = item;
        node.pre = null;
        node.next = first;
        if (first != null) {
            first.pre = node;
        }
        if (last == null) {
            last = node;
        }
        first = node;
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> node = new Node<>();
        node.item = item;
        node.pre = last;
        node.next = null;
        if (last != null) {
            last.next = node;
        }
        if (first == null) {
            first = node;
        }
        last = node;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.pre;
        size--;
        if (isEmpty()) {
            first = null;
        }
        return item;
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> node = Deque.this.first;

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = node.item;
            node = node.next;
            return item;
        }
    }


    private static class Node<E> {
        private E item;
        private Node<E> pre;
        private Node<E> next;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeLast();
        deque.forEach(System.out::println);
    }
}
