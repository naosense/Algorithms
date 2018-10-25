package com.pingao.assignment.week6;

import com.pingao.utils.ResourceUtils;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;


/**
 * Created by pingao on 2018/10/23.
 */
public class WordNet {
    private final RedBlackBST<String, Integer> nouns = new RedBlackBST<>();
    private final Digraph digraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("synsets and hypernyms must not be null");
        }

        In sin = new In(synsets);
        while (!sin.isEmpty()) {
            String line = sin.readLine();
            if (line.isEmpty()) {
                continue;
            }
            String[] columns = line.split(",");
            nouns.put(columns[1], Integer.parseInt(columns[0]));
        }

        digraph = new Digraph(82192);
        In hin = new In(hypernyms);
        while (!hin.isEmpty()) {
            String line = hin.readLine();
            String[] vw = line.split(",");
            if (line.isEmpty()) {
                continue;
            }
            for (int i = 1; i < vw.length; i++) {
                digraph.addEdge(Integer.parseInt(vw[0]), Integer.parseInt(vw[i]));
            }
        }

        // check cycle
        DirectedCycle cycle = new DirectedCycle(digraph);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException("Digraph is not DAG");
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
        if (!isNoun(nounA) || isNoun(nounB)) {
            throw new IllegalArgumentException("nounA and nounB must be in wordnet");
        }
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || isNoun(nounB)) {
            throw new IllegalArgumentException("nounA and nounB must be in wordnet");
        }
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(ResourceUtils.getTestResourcePath("week6-synsets.txt"),
                                      ResourceUtils.getTestResourcePath("week6-hypernyms.txt"));

        wordNet.nouns().forEach(System.out::println);

        System.out.println(wordNet.digraph);
    }
}
