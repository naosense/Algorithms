package com.pingao.assignment.week4;

import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;


/**
 * Created by pingao on 2018/7/1.
 */
public class Board {
    private final int[][] blocks;
    private int hamming;
    private int manhattan;
    private ResizingArrayQueue<Board> neighbors;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }

        this.blocks = blocks;
        this.neighbors = new ResizingArrayQueue<>();
    }

    // (where blocks[i][j] = block in row i, column j)
    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        if (hamming > 0) {
            return hamming;
        }
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
        if (manhattan > 0) {
            return manhattan;
        }
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * n + j + 1) {
                    int i2 = blocks[i][j] / n;
                    int j2 = blocks[i][j] - i2 * n - 1;
                    manhattan += Math.abs(i - i2) + Math.abs(j - j2);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int n = dimension();
        int i1 = StdRandom.uniform(n);
        int j1 = StdRandom.uniform(n);
        int i2 = StdRandom.uniform(n);
        int j2 = StdRandom.uniform(n);
        while (true) {
            if (blocks[i1][j1] == 0) {
                i1 = StdRandom.uniform(n);
                j1 = StdRandom.uniform(n);
            } else if (blocks[i2][j2] == 0) {
                i2 = StdRandom.uniform(n);
                j2 = StdRandom.uniform(n);
            } else if (i1 == i2 && j1 == j2) {
                i1 = StdRandom.uniform(n);
                j1 = StdRandom.uniform(n);
            } else {
                break;
            }
        }
        int[][] blocksCopy = blocks.clone();
        swap(i1, j1, i2, j2, blocksCopy);
        return new Board(blocksCopy);
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
            int n = dimension();
            for (int i = 0; i < n; i++) {
                if (!Arrays.equals(this.blocks[i], that.blocks[i])) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (neighbors.isEmpty()) {
            int n = dimension();

            // find empty
            int x = 0;
            int y = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (blocks[i][j] == 0) {
                        x = i;
                        y = j;
                        break;
                    }
                }
                if (x != 0 && y != 0) {
                    break;
                }
            }

            // top
            if (isRange(x - 1)) {
                int[][] bl = blocks.clone();
                swap(x, y, x - 1, y, bl);
                neighbors.enqueue(new Board(bl));
            }

            // left
            if (isRange(y - 1)) {
                int[][] bl = blocks.clone();
                swap(x, y, x, y - 1, bl);
                neighbors.enqueue(new Board(bl));
            }

            // bottom
            if (isRange(x + 1)) {
                int[][] bl = blocks.clone();
                swap(x, y, x + 1, y, bl);
                neighbors.enqueue(new Board(bl));
            }

            // right
            if (isRange(y + 1)) {
                int[][] bl = blocks.clone();
                swap(x, y, x, y + 1, bl);
                neighbors.enqueue(new Board(bl));
            }
        }

        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return neighbors.iterator();
            }
        };
    }

    private boolean isRange(int x) {
        return 0 <= x && x <= dimension() - 1;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    if (j == n - 1) {
                        sb.append("%n");
                    } else {
                        sb.append("  ");
                    }
                } else {
                    if (j == n - 1) {
                        sb.append(blocks[i][j]).append("%n");
                    } else {
                        sb.append(blocks[i][j]).append(' ');
                    }
                }
            }
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}
