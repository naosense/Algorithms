package com.pingao.assignment.week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdOut;


/**
 * Created by pingao on 2018/7/1.
 */
public class Solver {
    private ResizingArrayStack<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Node> pq1 = new MinPQ<>();
        MinPQ<Node> pq2 = new MinPQ<>();
        pq1.insert(new Node(null, initial, 1));
        pq2.insert(new Node(null, initial.twin(), 1));

        Node current1 = pq1.delMin();
        Node current2 = pq2.delMin();
        while (!current1.board.isGoal() && !current2.board.isGoal()) {
            for (Board n : current1.board.neighbors()) {
                if (current1.predecessor == null || !n.equals(current1.predecessor.board)) {
                    pq1.insert(new Node(current1, n, current1.move + 1));
                }
            }
            for (Board n : current2.board.neighbors()) {
                if (current2.predecessor == null || !n.equals(current2.predecessor.board)) {
                    pq2.insert(new Node(current2, n, current2.move + 1));
                }
            }
            current1 = pq1.delMin();
            current2 = pq2.delMin();
        }

        if (current1.board.isGoal()) {
            solution = new ResizingArrayStack<>();
            solution.push(current1.board);
            for (Node pre = current1.predecessor; pre != null; pre = pre.predecessor) {
                solution.push(pre.board);
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? solution.size() - 1 : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private static class Node implements Comparable<Node> {
        private final Node predecessor;
        private final Board board;
        private final int move;

        public Node(Node predecessor, Board board, int move) {
            this.predecessor = predecessor;
            this.board = board;
            this.move = move;
        }

        @Override
        public int compareTo(Node o) {
            int priority1 = board.manhattan() + move;
            int priority2 = o.board.manhattan() + o.move;
            int compare = Integer.compare(priority1, priority2);
            if (compare == 0) {
                return Integer.compare(board.manhattan(), o.board.manhattan());
            } else {
                return compare;
            }
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(System.getProperty("user.dir") + "/src/test/resources/week4-puzzle04.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
