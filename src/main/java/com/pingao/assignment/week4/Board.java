package com.pingao.assignment.week4;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;


/**
 * Created by pingao on 2018/7/1.
 */
public class Board {
    private final int[][] blocks;
    private int manhattan;
    private final Queue<Board> neighbors;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }

        this.blocks = deepCopy(blocks);
        this.manhattan = -1;
        this.neighbors = new Queue<>();
    }

    // (where blocks[i][j] = block in row i, column j)
    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * n + j + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattan >= 0) {
            return manhattan;
        }
        int distance = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * n + j + 1) {
                    int i2 = (blocks[i][j] - 1) / n;
                    int j2 = blocks[i][j] - i2 * n - 1;
                    distance += Math.abs(i - i2) + Math.abs(j - j2);
                }
            }
        }
        manhattan = distance;
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int n = dimension();
        int i1 = -1, j1 = -1;
        int i2 = -1, j2 = -1;
        out:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0) {
                    if (i1 == -1) {
                        i1 = i;
                        j1 = j;
                    } else if (i2 == -1) {
                        i2 = i;
                        j2 = j;
                    } else {
                        break out;
                    }
                }
            }
        }

        int[][] bl = deepCopy(blocks);
        swap(i1, j1, i2, j2, bl);
        return new Board(bl);
    }

    private int[][] deepCopy(int[][] a) {
        int[][] copy = new int[a.length][];
        for (int i = 0; i < a.length; i++) {
            copy[i] = a[i].clone();
        }
        return copy;
    }

    private void swap(int i1, int j1, int i2, int j2, int[][] blocks) {
        int temp = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = temp;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (y instanceof Board) {
            Board that = (Board) y;
            return Arrays.deepEquals(blocks, that.blocks);
        }

        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (neighbors.isEmpty()) {
            int n = dimension();

            // find empty
            int x = -1;
            int y = -1;
            out:
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (blocks[i][j] == 0) {
                        x = i;
                        y = j;
                        break out;
                    }
                }
            }

            // top
            if (isRange(x - 1)) {
                int[][] bl = deepCopy(blocks);
                swap(x, y, x - 1, y, bl);
                neighbors.enqueue(new Board(bl));
            }

            // left
            if (isRange(y - 1)) {
                int[][] bl = deepCopy(blocks);
                swap(x, y, x, y - 1, bl);
                neighbors.enqueue(new Board(bl));
            }

            // bottom
            if (isRange(x + 1)) {
                int[][] bl = deepCopy(blocks);
                swap(x, y, x + 1, y, bl);
                neighbors.enqueue(new Board(bl));
            }

            // right
            if (isRange(y + 1)) {
                int[][] bl = deepCopy(blocks);
                swap(x, y, x, y + 1, bl);
                neighbors.enqueue(new Board(bl));
            }
        }

        return neighbors;
    }

    private boolean isRange(int x) {
        return 0 <= x && x <= dimension() - 1;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = dimension();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == n - 1) {
                    sb.append(blocks[i][j]).append("\n");
                } else {
                    sb.append(String.format("%2d", blocks[i][j])).append(' ');
                }
            }
        }
        return sb.toString();
    }
}
