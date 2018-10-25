package com.pingao.assignment.week6;

import com.pingao.utils.ResourceUtils;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**
 * Created by pingao on 2018/10/23.
 */
public class SAP {
    private final Digraph G;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("Digraph can't be null");
        }
        this.G = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateRange(v);
        validateRange(w);
        int distance = -1;
        BreadthFirstDirectedPaths ba = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bb = new BreadthFirstDirectedPaths(G, w);
        for (int i = 0; i < G.V(); i++) {
            if (ba.hasPathTo(i) && bb.hasPathTo(i)) {
                int dist = ba.distTo(i) + bb.distTo(i);
                if (distance == -1 || distance > dist) {
                    distance = dist;
                }
            }
        }
        return distance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateRange(v);
        validateRange(w);
        int distance = -1;
        int sap = -1;
        BreadthFirstDirectedPaths ba = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bb = new BreadthFirstDirectedPaths(G, w);
        for (int i = 0; i < G.V(); i++) {
            if (ba.hasPathTo(i) && bb.hasPathTo(i)) {
                int dist = ba.distTo(i) + bb.distTo(i);
                if (distance == -1 || distance > dist) {
                    distance = dist;
                    sap = i;
                }
            }
        }
        return sap;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateRange(v);
        validateRange(w);
        int distance = -1;
        BreadthFirstDirectedPaths ba = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bb = new BreadthFirstDirectedPaths(G, w);
        for (int i = 0; i < G.V(); i++) {
            if (ba.hasPathTo(i) && bb.hasPathTo(i)) {
                int dist = ba.distTo(i) + bb.distTo(i);
                if (distance == -1 || distance > dist) {
                    distance = dist;
                }
            }
        }
        return distance;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateRange(v);
        validateRange(w);
        int distance = -1;
        int sap = -1;
        BreadthFirstDirectedPaths ba = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bb = new BreadthFirstDirectedPaths(G, w);
        for (int i = 0; i < G.V(); i++) {
            if (ba.hasPathTo(i) && bb.hasPathTo(i)) {
                int dist = ba.distTo(i) + bb.distTo(i);
                if (distance == -1 || distance > dist) {
                    distance = dist;
                    sap = i;
                }
            }
        }
        return sap;
    }

    private void validateRange(int v) {
        if (v < 0 || v > G.V()) {
            throw new IllegalArgumentException("Vertex is not between 0 ~ " + G.V());
        }
    }

    private void validateRange(Iterable<Integer> v) {
        if (v == null) {
            throw new IllegalArgumentException("Vertex can't be null");
        }
        for (Integer i : v) {
            validateRange(i);
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(ResourceUtils.getTestResourcePath("week6-digraph1.txt"));
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = 1;
            int w = 6;
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
