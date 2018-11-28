package com.pingao.assignment.week9;

import com.pingao.utils.ResourceUtils;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by pingao on 2018/11/26.
 */
public class BoggleSolver {
    private final BoggleTST<Integer> tri = new BoggleTST<>();

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
                dfs(board, null, new StringBuilder(), r, c, words, new boolean[board.rows()][board.cols()]);
            }
        }
        return words;
    }

    private void dfs(BoggleBoard board, BoggleTST.Node<Integer> node, StringBuilder word, int row, int col, Set<String> words, boolean[][] marked) {
        marked[row][col] = true;
        char letter = board.getLetter(row, col);
        if (letter == 'Q') {
            word.append("QU");
        } else {
            word.append(letter);
        }

        String ws = word.toString();
        int d = letter == 'Q' ? ws.length() - 2 : ws.length() - 1;
        BoggleTST.Node<Integer> root = tri.getNodeWithPrefix(node, ws, d);

        if (root == null) {
            return;
        }

        if (ws.length() > 2 && tri.contains(ws)) {
            words.add(ws);
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
                    dfs(board, root, word, r, c, words, marked);
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
        if (word == null) {
            throw new IllegalArgumentException("word can't be null");
        }

        if (!tri.contains(word)) {
            return 0;
        }

        int length = word.length();
        if (length < 3) {
            return 0;
        } else if (length < 5) {
            return 1;
        } else if (length < 6) {
            return 2;
        } else if (length < 7) {
            return 3;
        } else if (length < 8) {
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
