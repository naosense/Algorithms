package com.pingao.utils;

/**
 * Created by pingao on 2018/5/24.
 */
public class Timer {
    private static final int C = 1000000000;
    private int n;
    private long start;

    public Timer() {
        start = System.nanoTime();
    }

    public void tt() {
        long stop = System.nanoTime();
        System.out.printf("#%d %f sec%n", ++n, 1.0f * (stop - start) / C);
        start = System.nanoTime();
    }
}
