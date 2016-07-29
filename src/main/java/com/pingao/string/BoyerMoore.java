package com.pingao.string;

import java.util.ArrayList;
import java.util.List;

public class BoyerMoore {
    private int[] right;
    private String pat;

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
        int skip = 0;
        List<Integer> pos = new ArrayList<>();
        System.out.println(txt);
        for (int i = 0; i <= N - M; i += skip) {

            for (int s = 0; s < i; s++) {
                System.out.print(" ");
            }
            System.out.println(pat);

            for (int j = M - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = j - right[txt.charAt(i + j)];
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

    public static void main(String[] args) {
        String pat = "ddada";
        String txt = "dadasdfsdadfseeeeeeeeeeeeeeeabceeeeeeeeeeeeeeabceeeeeeeeabceeeeddadazzzzzzzzzzz";
        BoyerMoore boyerMoore = new BoyerMoore(pat);
        List<Integer> positions = boyerMoore.search(txt);
        //int N = txt.length();
        //
        //for (int i = 0; i < N; i++) {
        //    System.out.print("=");
        //}
        //System.out.println();
        //for (Integer p:positions) {
        //    for (int s = 0; s < p; s++) {
        //        System.out.print(" ");
        //    }
        //    System.out.println(pat);
        //}
    }
}
