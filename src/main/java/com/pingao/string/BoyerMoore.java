package com.pingao.string;

import java.util.ArrayList;
import java.util.List;

public class BoyerMoore {
    // right���飬ÿ���ַ���ģʽ�����ұߵ�λ�ã�Ĭ��ֵΪ-1
    private int[] right;
    // delta���飬ÿ���ַ����ı��������ֵ�λ�ã�Ĭ��ֵΪģʽ�ĳ���
    private int[] delta;
    // ģʽ�ַ�����Ҫ�����Ĺؼ���
    private String pat;
    // ������ģʽ�ƶ��Ĵ�����ͳ����
    private int step;
    // �Ƚϵ��ַ�����ͳ����
    private int count;

    public BoyerMoore(String pat) {
        this.pat = pat;
        init(256);
    }

    private void init(int R) {
        right = new int[R];
        delta = new int[R];
        int M = pat.length();

        for (int c = 0; c < R; c++) {
            right[c] = -1;
            delta[c] = pat.length();
        }

        for (int i = 0; i < M; i++) {
            right[pat.charAt(i)] = i;
        }
    }

    public int getStep() {
        return step;
    }

    public int getCount() {
        return count;
    }

    public List<Integer> search(String txt) {
        int N = txt.length();
        int M = pat.length();
        List<Integer> pos = new ArrayList<>();
        for (int i = 0, skip = 0; i <= N - M; i += skip) {
            //printStep(pat, i);
            step++;
            for (int j = M - 1; j >= 0; j--) {
                count++;
                char c1 = txt.charAt(i + j);
                char c2 = pat.charAt(j);
                delta[c1] = j;
                if (c1 != c2) {
                    int skip1 = j - right[c1];
                    int skip2 = delta[c2] - j;
                    skip = Math.max(skip1, skip2);
                    if (skip < 1) {skip = 1;}
                    break;
                }
                if (j == 0) {
                    pos.add(i);
                    skip = M;
                    resetDelta();
                    break;
                }
            }
        }
        return pos;
    }

    /**
     * ����delta���飬ÿ�β��ҳɹ�����Ҫ����delta����
     */
    private void resetDelta() {
        for (int c = 0; c < 256; c++) {
            delta[c] = pat.length();
        }
    }

    /**
     * ��ӡ����ģʽ�ַ��Ĳ���
     *
     * @param pat      ģʽ�ַ���
     * @param position i��λ�ã�Ҳ��ģʽ�ַ�����ʼ��λ��
     */
    private void printStep(String pat, int position) {
        for (int i = 0; i < position; i++) {
            System.out.print(" ");
        }
        System.out.println(pat + " #" + step++ + " " + count);
    }
}
