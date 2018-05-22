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
        if (last == null) {
            last = node;
            first = last;
        } else {
            first.pre = node;
            first = first.pre;
        }
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
        if (first == null) {
            first = node;
            last = first;
        } else {
            last.next = node;
            last = last.next;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        Node<Item> next = first.next;
        if (next == null) {
            last = null;  // 只有一个元素
            first = null;
        } else {
            next.pre = null;  // 原来这里指向first，现在first删除了，因此要设为null
            first = next;
        }
        size--;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        Node<Item> pre = last.pre;
        if (pre == null) {
            first = null;  // 只有一个元素
            last = null;
        } else {
            pre.next = null;  // 原来指向last，last现在被删除了，所以应该设为null
            last = pre;
        }
        size--;
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
        deque.addLast(1);
        deque.removeLast();
        deque.addLast(3);
        deque.addFirst(4);
        deque.addLast(5);
        deque.removeFirst();
        deque.addFirst(7);
        deque.removeLast();
        deque.forEach(System.out::println);
    }
}
