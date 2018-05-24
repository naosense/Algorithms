package com.pingao.sort;

import com.pingao.utils.Timer;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;


/**
 * Created by pingao on 2018/5/24.
 */
public class Sorts {
    // prevent instance
    private Sorts() {
    }

    public static void selectSort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            swap(a, i, min);
        }
    }

    public static void insertSort(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (less(a[j], a[j - 1])) {
                    swap(a, j, j - 1);
                } else {
                    // a[i]是最大的，如果a[j]>=a[i]，那么对于i左边的元素就不用比了
                    break;
                }
            }
        }
    }

    public static void shellSort(Comparable[] a) {

    }

    @SuppressWarnings("unchecked")
    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void swap(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        int[] integers = StdRandom.permutation(10000);
        Integer[] i1 = Arrays.stream(integers).boxed().toArray(Integer[]::new);
        Integer[] i2 = Arrays.stream(integers).boxed().toArray(Integer[]::new);
        Timer tt = new Timer();
        selectSort(i1);
        tt.tt();
        insertSort(i2);
        tt.tt();
        System.out.println(Arrays.equals(i1, i2));
    }
}
