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
        if ((row < 1 || row > n) || col < 1 || row > n) {
            throw new IllegalArgumentException("Row and col must between 1 and " + n);
        }

        int self = index(row, col);
        if (sites[self] == 1) {
            return;
        }
        // connect to top virtual node
        if (self < n) {
            uf.union(self, n * n);
        }
        // connect to bottom virtual node
        if (self >= n * (n - 1) && self < n * n) {
            uf.union(self, n * n + 1);
        }
        sites[self] = 1;
        open++;
        int left = self - 1;
        int right = self + 1;
        int up = self - n;
        int down = self + n;
        if (left >= 0 && sites[left] == 1) {
            uf.union(left, self);
        }
        if (right < n * n && sites[right] == 1) {
            uf.union(right, self);
        }
        if (up >= 0 && sites[up] == 1) {
            uf.union(up, self);
        }
        if (down < n * n && sites[down] == 1) {
            uf.union(down, self);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 1 || row > n) || col < 1 || row > n) {
            throw new IllegalArgumentException("Row and col must between 1 and " + n);
        }
        return sites[index(row, col)] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row < 1 || row > n) || col < 1 || row > n) {
            throw new IllegalArgumentException("Row and col must between 1 and " + n);
        }
        return uf.connected(index(row, col), n * n);
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
        System.out.println(percolation.isOpen(1, 1));
        System.out.println(percolation.isOpen(1, 2));
        System.out.println(percolation.percolates());
    }
}
