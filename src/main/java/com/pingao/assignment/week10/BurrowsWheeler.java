package com.pingao.assignment.week10;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


/**
 * Created by pingao on 2018/12/10.
 */
public class BurrowsWheeler {
    private static final int R = 256;

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < csa.length(); i++) {
            BinaryStdOut.write(s.charAt((csa.index(i) + s.length() - 1) % s.length()));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int n = s.length();
        int[] count = new int[R + 1];
        int[] next = new int[n];
        for (int i = 0; i < n; i++) {
            count[s.charAt(i) + 1]++;
        }

        for (int r = 0; r < R; r++) {
            count[r + 1] += count[r];
        }

        for (int i = 0; i < n; i++) {
            next[count[s.charAt(i)]++] = i;
        }

        for (int i = first, size = 0; size < n; i = next[i], size++) {
            BinaryStdOut.write(s.charAt(next[i]));
        }

        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if ("-".equals(args[0])) {
            transform();
        }
        if ("+".equals(args[0])) {
            inverseTransform();
        }
    }
}
