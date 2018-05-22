package com.pingao.assignment.week1;

import java.util.Arrays;


/**
 * 3-SUM in quadratic time. Design an algorithm for the 3-SUM problem that takes time proportional to n^2
 * in the worst case. You may assume that you can sort the nn integers in time proportional to n^2
 * or better.
 * <p>
 * Created by pingao on 2018/5/21.
 */
public class ThreeSum {
    public static void find(int[] a) {
        Arrays.sort(a);
        for (int i = 0; i < a.length - 2; i++) {
            if (i> 0 && a[i] == a[i - 1]) {
                continue;
            }
            linearSearch(a, -a[i], i);
        }
    }

    // 线性搜索a[i] + a[j] = x，对于给定的x，数组中两个元素之和等于x的话，必然
    // 有一个小于x/2，一个大于x/2，所以如果以双重循环来搜索x，同时搜索下半区域
    // 和同时搜索下半区域都是一种浪费
    private static void linearSearch(int[] a, int x, int i) {
        for (int l = i + 1, h = a.length - 1; l < h; ) {
            if (a[l] + a[h] > x) {
                h--;
            } else if (a[l] + a[h] < x) {
                l++;
            } else {
                for (; l < h && a[l] == a[l + 1]; l++) ;
                for (; l < h && a[h] == a[h - 1]; h--) ;

                System.out.printf("i = %d, j = %d, k = %d%n", a[i], a[l], a[h]);
                l++;
                h--;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {-2, -1, 0, -1, 1, 1, 3, 2};
        find(a);
    }
}
