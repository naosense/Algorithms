package com.pingao;

import com.pingao.string.BoyerMoore;
import com.pingao.string.DirectSearch;

import java.util.List;
import java.util.Random;

/**
 * Created by wocanmei on 2016/7/30.
 */
public class BoyerMooreTest {
    private static Random random = new Random();

    public static void main(String[] args) {
        testRandomString();
    }

    private static void testRandomString() {
        int count1 = 0;
        int count2 = 0;
        String txt;
        String pat;

        System.out.println("txtlen\tcount1\tcount2");
        for (int i = 0; i < 10000; i += 100) {
            for (int times = 0; times < 10; times++) {
                txt = randomString(i);
                pat = randomString(10);
                BoyerMoore boyerMoore = new BoyerMoore(pat);
                DirectSearch directSearch = new DirectSearch(pat);
                boyerMoore.search(txt);
                directSearch.search(txt);
                count1 += boyerMoore.getCount();
                count2 += directSearch.getCount();

            }
            count1 = count1 / 10;
            count2 = count2 / 10;
            System.out.println(i + "\t" + count1 + "\t" + count2);
        }
    }

    private static void testRepeatString() {
        String pat = "ABBBB";
        String txt = "BBBBBBBBBBBBBBBBBBBB";
        int N = txt.length();
        int M = pat.length();
        System.out.println("txt length: " + N);
        System.out.println("pat length: " + M);
        System.out.println(txt.replaceAll(".", "-"));
        System.out.println(txt);
        BoyerMoore boyerMoore = new BoyerMoore(pat);
        List<Integer> positions = boyerMoore.search(txt);
        StringBuilder mark = new StringBuilder();
        for (int i = 0; i < N; i++) {
            if (positions.contains(i)) {
                mark.append('*');
            } else {
                mark.append(' ');
            }
        }
        System.out.println(mark);
    }

    private static String randomString(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char c = (char) (random.nextInt(26) + 65);
            sb.append(c);
        }
        return sb.toString();
    }
}
