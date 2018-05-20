package com.pingao.assignment;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


/**
 * Created by pingao on 2018/5/20.
 */
public class PercolationStats {
    private final double[] threshold;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        threshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int index = StdRandom.uniform(n * n);
                int row = index / n + 1;
                int col = index - (row - 1) * n + 1;
                percolation.open(row, col);
            }
            threshold[i] = percolation.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(threshold.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(threshold.length));
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(2, 10000);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
