package com.pingao.assignment.week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdOut;


/**
 * Created by pingao on 2018/7/1.
 */
public class Solver {
    private final ResizingArrayQueue<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solution = new ResizingArrayQueue<>();
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(null, initial, 1));
        solution.enqueue(initial);

        Node current = pq.delMin();
        while (!current.board.isGoal()) {
            for (Board n : current.board.neighbors()) {
                System.out.println(n);
                if (current.predecessor == null || !n.equals(current.predecessor.board)) {
                    System.out.println(pq.size());
                    pq.insert(new Node(current, n, current.move + 1));
                }
            }
            current = pq.delMin();
            solution.enqueue(current.board);
        }


    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution.size();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private static class Node implements Comparable<Node>{
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
            return Integer.compare(board.manhattan() + move, o.board.manhattan() + o.move);
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(System.getProperty("user.dir") + "/src/test/resources/week4-puzzle04.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
