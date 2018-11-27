package com.pingao.assignment.week9;

import com.pingao.utils.ResourceUtils;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by pingao on 2018/11/26.
 */
public class BoggleSolver {
    private final TST<Integer> tri = new TST<>();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("dictionary can't be null");
        }

        for (String w : dictionary) {
            tri.put(w, 1);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> words = new HashSet<>();
        for (int r = 0; r < board.rows(); r++) {
            for (int c = 0; c < board.cols(); c++) {
                dfs(board, new StringBuilder(), r, c, words, new boolean[board.rows()][board.cols()]);
            }
        }
        return words;
    }

    private void dfs(BoggleBoard board, StringBuilder word, int row, int col, Set<String> words, boolean[][] marked) {
        marked[row][col] = true;
        char letter = board.getLetter(row, col);
        if (letter == 'Q') {
            word.append("QU");
        } else {
            word.append(letter);
        }

        int size = 0;
        String ws = word.toString();
        Iterable<String> prefix = tri.keysWithPrefix(ws);
        for (String w : prefix) {
            // 长度大于2的才行？
            if (w.equals(ws) && w.length() > 2) {
                words.add(ws);
            }
            size++;
        }

        if (size == 0) {
            return;
        }

        // 计算邻接元素的行和列边界
        int rl = row == 0 ? 0 : row - 1;
        int rh = row == board.rows() - 1 ? board.rows() - 1 : row + 1;
        int cl = col == 0 ? 0 : col - 1;
        int ch = col == board.cols() - 1 ? board.cols() - 1 : col + 1;

        for (int r = rl; r <= rh; r++) {
            for (int c = cl; c <= ch; c++) {
                if (r == row && c == col) {
                    continue;
                }

                if (!marked[r][c]) {
                    dfs(board, word, r, c, words, marked);
                    // 删掉尾部字符，如果是Q的话，要删除两次
                    if (board.getLetter(r, c) == 'Q') {
                        word.deleteCharAt(word.length() - 1);
                        word.deleteCharAt(word.length() - 1);
                    } else {
                        word.deleteCharAt(word.length() - 1);
                    }
                    // 恢复marked
                    marked[r][c] = false;
                }
            }
        }
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int l = word.length();
        if (l < 3) {
            return 0;
        } else if (l < 5) {
            return 1;
        } else if (l < 6) {
            return 2;
        } else if (l < 7) {
            return 3;
        } else if (l < 8) {
            return 5;
        } else {
            return 11;
        }
    }

    public static void main(String[] args) {
        In in = new In(ResourceUtils.getTestResourcePath("week9-dictionary-algs4.txt"));
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(ResourceUtils.getTestResourcePath("week9-board4x4.txt"));
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
