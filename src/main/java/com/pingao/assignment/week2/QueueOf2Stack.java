package com.pingao.assignment.week2;

import com.pingao.collection.Stack;

import java.util.NoSuchElementException;


/**
 * Queue with two stacks.
 * Implement a queue with two stacks so that each queue operations takes a constant amortized number of stack operations.
 * <p>
 * Created by pingao on 2018/5/23.
 */
public class QueueOf2Stack<E> {
    private final Stack<E> main;
    private final Stack<E> temp;
    private int size;

    public QueueOf2Stack() {
        main = new Stack<>();
        temp = new Stack<>();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(E e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        while (!main.isEmpty()) {
            temp.push(main.pop());
        }
        main.push(e);
        size++;
        while (!temp.isEmpty()) {
            main.push(temp.pop());
        }
    }

    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return main.pop();
    }

    public static void main(String[] args) {
        QueueOf2Stack<Integer> queue = new QueueOf2Stack<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }
}
