package com.pingao.sort;

import com.pingao.utils.Timer;
import edu.princeton.cs.algs4.Shell;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;


/**
 * Created by pingao on 2018/5/24.
 */
public class Sorts {
    // prevent instance
    private Sorts() {
    }

    /**
     * 选择排序
     * <p>
     * 选择排序大约需要(n-1) + (n-2) + ... + 1 = n(n-1)/2 次比较和n-1次交换
     *
     * @param a 需要排序的数组
     */
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
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
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
        int h = 1;
        while (h < a.length / 3) {
            h = h * 3 + 1;
        }

        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (less(a[j], a[j - h])) {
                        swap(a, j, j - h);
                    } else {
                        // a[i]是最大的，如果a[j]>=a[i]，那么对于i左边的元素就不用比了
                        break;
                    }
                }
            }
            h /= 3;
        }
    }

    public static void mergeSort(Comparable[] a) {

    }

    public static void quickSort(Comparable[] a) {

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
        Timer tt = new Timer();
        int[] integers = StdRandom.permutation(10000);
        tt.tt();
        Integer[] i1 = Arrays.stream(integers).boxed().toArray(Integer[]::new);
        Integer[] i2 = Arrays.stream(integers).boxed().toArray(Integer[]::new);
        Integer[] i3 = Arrays.stream(integers).boxed().toArray(Integer[]::new);
        Integer[] i4 = Arrays.stream(integers).boxed().toArray(Integer[]::new);
        selectSort(i1);
        tt.tt();
        insertSort(i2);
        tt.tt();
        shellSort(i3);
        tt.tt();
        Shell.sort(i4);
        tt.tt();

        System.out.println(Arrays.equals(i1, i2));
        System.out.println(Arrays.equals(i1, i3));
        System.out.println(Arrays.equals(i1, i4));
    }
}
