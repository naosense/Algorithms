package com.pingao.assignment.week10;

/**
 * Created by pingao on 2018/12/10.
 */
public class CircularSuffixArray {
    private String s;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s can't be null");
        }
        this.s = s;
        this.index = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            index[i] = i;
        }
        sort(index, index.length);
    }

    // length of s
    public int length() {
        return index.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > length() - 1) {
            throw new IllegalArgumentException("i should between 0 and " + (length() - 1));
        }
        return index[i];
    }

    // lsd sort
    private void sort(int[] a, int w) {
        int n = length();
        int R = 256;   // extend ASCII alphabet size
        int[] aux = new int[n];

        for (int d = w - 1; d >= 0; d--) {
            // sort by key-indexed counting on dth character
            // compute frequency counts
            int[] count = new int[R + 1];
            for (int i = 0; i < n; i++) {
                count[s.charAt(i) + 1]++;
            }

            // compute cumulates
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            // move data
            for (int i = 0; i < n; i++) {
                aux[count[s.charAt((index(i) + d) % n)]++] = a[i];
            }

            // copy back
            for (int i = 0; i < n; i++) {
                a[i] = aux[i];
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); i++) {
            System.out.println(csa.index(i));
        }
    }
}
