package com.pingao.assignment;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


/**
 * Created by pingao on 2018/5/20.
 */
public class PercolationStats {
    private static final double RATIO = 1.96;
    private final double[] threshold;
    private final double mean;
    private final double sd;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("N and trials must greater than 0");
        }
        threshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            threshold[i] = percolation.numberOfOpenSites() * 1.0 / (n * n);
        }
        mean = StdStats.mean(threshold);
        sd = StdStats.stddev(threshold);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return sd;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (RATIO * sd / Math.sqrt(threshold.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (RATIO * sd / Math.sqrt(threshold.length));
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
