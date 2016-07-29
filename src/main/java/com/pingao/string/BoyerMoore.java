package com.pingao.string;

import java.util.ArrayList;
import java.util.List;

public class BoyerMoore {
    private int[] right;
    private String pat;
    private int step;
    private int count;

    public BoyerMoore(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        right = new int[R];

        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }

        for (int i = 0; i < M; i++) {
            right[pat.charAt(i)] = i;
        }
    }

    public List<Integer> search(String txt) {
        int N = txt.length();
        int M = pat.length();
        List<Integer> pos = new ArrayList<>();
        for (int i = 0, skip = 0; i <= N - M; i += skip) {
            printStep(pat, i);
            for (int j = M - 1; j >= 0; j--) {
                count++;
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = j - right[txt.charAt(i + j)];
                    if (skip < 1) {skip = 1;}
                    break;
                }
                if (j == 0) {
                    pos.add(i);
                    skip = M;
                    break;
                }
            }
        }
        return pos;
    }

    private void printStep(String pat, int position) {
        for (int i = 0; i < position; i++) {
            System.out.print(" ");
        }
        System.out.println(pat + " #" + step++ + " " + count);
    }

    public static void main(String[] args) {
        String pat = "aeeee";
        String txt = "eeeeeeeeeeeeeeeeeeee";
        int N = txt.length();
        int M = pat.length();
        BoyerMoore boyerMoore = new BoyerMoore(pat);
        List<Integer> positions = boyerMoore.search(txt);
        System.out.println(txt);
        StringBuilder mark = new StringBuilder();
        for (int i = 0; i < N; i++) {
            if (positions.contains(i)) {
                mark.append('*');
            } else {
                mark.append(' ');
            }
        }
        System.out.println(mark);
        System.out.println(M + ", " + N);
    }
}
