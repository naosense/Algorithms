package com.pingao.assignment.week6;

/**
 * Created by pingao on 2018/10/23.
 */
public class Outcast {
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException("WordNet can't be null");
        }
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null) {
            throw new IllegalArgumentException();
        }
        int outcast = -1;
        int distance = -1;
        for (int i = 0; i < nouns.length; i++) {
            int d = 0;
            for (int j = i + 1; j < nouns.length; j++) {
                String n1 = nouns[i];
                String n2 = nouns[j];
                d += wordNet.distance(n1, n2);
            }
            if (distance == -1 || distance < d) {
                distance = d;
                outcast = i;
            }
        }
        return nouns[outcast];
    }

    // see test client below
    //public static void main(String[] args) {
    //    WordNet wordNet = new WordNet(ResourceUtils.getTestResourcePath("week6-synsets.txt"),
    //                                  ResourceUtils.getTestResourcePath("week6-hypernyms.txt"));
    //    Outcast outcast = new Outcast(wordNet);
    //    String t = ResourceUtils.getTestResourcePath("week6-outcast5.txt");
    //    In in = new In(t);
    //    String[] nouns = in.readAllStrings();
    //    System.out.println(Arrays.toString(nouns));
    //    StdOut.println(t + ": " + outcast.outcast(nouns));
    //}
}
