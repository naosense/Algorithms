package com.pingao.assignment;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * Created by pingao on 2018/5/19.
 */
public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final int n;
    private final byte[] sites;
    private int open;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("N must greater than 0");
        }
        this.n = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.sites = new byte[n * n];
        for (int i = 0; i < n * n; i++) {
            this.sites[i] = 0;
        }
        this.open = 0;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n)) {
            throw new IllegalArgumentException("Row and col must between 1 and " + n);
        }

        int index = index(row, col);
        if (sites[index] == 1) {
            return;
        }
        // connect to top virtual node
        if (row == 1) {
            uf.union(index, n * n);
        }
        // connect to bottom virtual node
        if (row == n) {
            uf.union(index, n * n + 1);
        }
        sites[index] = 1;
        open++;

        int left = index - 1;
        int right = index + 1;
        int up = index - n;
        int down = index + n;
        if (col > 1 && sites[left] == 1) {
            uf.union(left, index);
        }
        if (col < n && sites[right] == 1) {
            uf.union(right, index);
        }
        if (row > 1 && sites[up] == 1) {
            uf.union(up, index);
        }
        if (row < n && sites[down] == 1) {
            uf.union(down, index);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n)) {
            throw new IllegalArgumentException("Row and col must between 1 and " + n);
        }
        return sites[index(row, col)] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n)) {
            throw new IllegalArgumentException("Row and col must between 1 and " + n);
        }
        int index = index(row, col);
        return sites[index] == 1 && uf.connected(index, n * n);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(n * n, n * n + 1);
    }

    private int index(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        System.out.println(percolation.isOpen(1, 1));
        System.out.println(percolation.isOpen(1, 2));
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        percolation.open(5, 1);
        percolation.open(5, 1);
        percolation.open(5, 1);
        System.out.println(percolation.isFull(3, 1));
        System.out.println(percolation.isOpen(1, 1));
        System.out.println(percolation.isOpen(1, 2));
        System.out.println(percolation.percolates());
        System.out.println(percolation.isFull(5, 1));
    }
}
