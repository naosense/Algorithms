package com.pingao.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wocanmei on 2016/7/31.
 */
public class DirectSearch {
    private String pat;
    private int count;

    public DirectSearch(String pat) {
        this.pat = pat;
    }

    public int getCount() {
        return count;
    }

    public List<Integer> search(String txt) {
        int M = pat.length();
        int N = txt.length();
        List<Integer> pos = new ArrayList<>();
        for(int i = 0; i <= N - M; i++) {
            for (int j = 0; j < M; j++) {
                count++;
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
                if (j == M - 1) {
                    pos.add(i);
                }
            }
        }
        return pos;
    }
}
