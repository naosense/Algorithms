package com.pingao.assignment.week6;

import com.pingao.utils.ResourceUtils;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import org.pmw.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pingao on 2018/10/23.
 */
public class WordNet {
    private final RedBlackBST<String, Bag<Integer>> nouns = new RedBlackBST<>();
    private final List<String> words = new ArrayList<>();
    // private final Digraph G;
    private final SAP sap;

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
            words.add(columns[1]);
            for (String w : columns[1].split(" ")) {
                Bag<Integer> index = nouns.get(w);
                if (index == null) {
                    index = new Bag<>();
                }
                index.add(Integer.parseInt(columns[0]));
                nouns.put(w, index);
            }
        }

        Digraph G = new Digraph(n);
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

        int root = 0;
        for (int v = 0; v < G.V(); v++) {
            if (G.indegree(v) > 0 && G.outdegree(v) == 0) {
                root++;
            }
        }
        if (root != 1) {
            throw new IllegalArgumentException("Digraph is not rooted");
        }

        sap = new SAP(G);
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

        Bag<Integer> a = nouns.get(nounA);
        Bag<Integer> b = nouns.get(nounB);

        return sap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException(nounA + " and " + nounB + " is not in wordnet");
        }

        Bag<Integer> a = nouns.get(nounA);
        Bag<Integer> b = nouns.get(nounB);

        int index = sap.ancestor(a, b);
        return index == -1 ? null : words.get(index);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(ResourceUtils.getTestResourcePath("week6-synsets3.txt"),
                                      ResourceUtils.getTestResourcePath("week6-hypernyms3InvalidTwoRoots.txt"));

        Logger.info(wordNet.sap("tea", "coffee"));
        Logger.info(wordNet.sap("increase", "damage"));
    }
}
