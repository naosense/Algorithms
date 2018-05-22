package com.pingao.assignment.week2;

import edu.princeton.cs.algs4.StdIn;


/**
 * Created by pingao on 2018/5/22.
 */
public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
