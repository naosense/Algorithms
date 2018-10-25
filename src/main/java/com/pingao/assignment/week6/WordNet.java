package com.pingao.assignment.week6;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pingao on 2018/10/23.
 */
public class WordNet {
    private final RedBlackBST<String, Integer> nouns = new RedBlackBST<>();
    private final List<String> words = new ArrayList<>();
    private final Digraph G;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("synsets and hypernyms must not be null");
        }

        int n = 0;
        In sin = new In(synsets);
        while (!sin.isEmpty()) {
            String line = sin.readLine();
            if (line.isEmpty()) {
                continue;
            }
            n++;
            String[] columns = line.split(",");
            nouns.put(columns[1], Integer.parseInt(columns[0]));
            words.add(columns[1]);
        }

        G = new Digraph(n);
        In hin = new In(hypernyms);
        while (!hin.isEmpty()) {
            String line = hin.readLine();
            String[] vw = line.split(",");
            if (line.isEmpty()) {
                continue;
            }
            for (int i = 1; i < vw.length; i++) {
                G.addEdge(Integer.parseInt(vw[0]), Integer.parseInt(vw[i]));
            }
        }

        // check cycle
        DirectedCycle cycle = new DirectedCycle(G);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException("Digraph is not DAG");
        }

        boolean isRooted = false;
        for (int v = 0; v < G.V(); v++) {
            if (G.indegree(v) == 0 && G.outdegree(v) > 0) {
                isRooted = true;
            }
        }
        if (!isRooted) {
            throw new IllegalArgumentException("Digraph is not rooted");
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.get(word) != null;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException(nounA + " and " + nounB + " is not in wordnet");
        }
        int a = nouns.get(nounA);
        int b = nouns.get(nounB);

        int distance = -1;
        BreadthFirstDirectedPaths ba = new BreadthFirstDirectedPaths(G, a);
        BreadthFirstDirectedPaths bb = new BreadthFirstDirectedPaths(G, b);
        for (int v = 0; v < G.V(); v++) {
            if (ba.hasPathTo(v) && bb.hasPathTo(v)) {
                int dist = ba.distTo(v) + bb.distTo(v);
                if (distance == -1 || distance > dist) {
                    distance = dist;
                }
            }
        }
        return distance;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException(nounA + " and " + nounB + " is not in wordnet");
        }
        int a = nouns.get(nounA);
        int b = nouns.get(nounB);

        int distance = -1;
        String sap = null;
        BreadthFirstDirectedPaths ba = new BreadthFirstDirectedPaths(G, a);
        BreadthFirstDirectedPaths bb = new BreadthFirstDirectedPaths(G, b);
        for (int v = 0; v < G.V(); v++) {
            if (ba.hasPathTo(v) && bb.hasPathTo(v)) {
                int dist = ba.distTo(v) + bb.distTo(v);
                if (distance == -1 || distance > dist) {
                    distance = dist;
                    sap = words.get(v);
                }
            }
        }
        return sap;
    }

    // do unit testing of this class
    //public static void main(String[] args) {
    //    WordNet wordNet = new WordNet(ResourceUtils.getTestResourcePath("week6-synsets.txt"),
    //                                  ResourceUtils.getTestResourcePath("week6-hypernyms.txt"));
    //
    //    System.out.println(wordNet.sap("miracle", "group_action"));
    //}
}
