package com.pingao.graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;


/**
 * Created by pingao on 2018/10/18.
 */
public class Graph {
    private final int V;  // 顶点数目
    private int E;  // 边的数目
    private Bag<Integer>[] adj;  // 邻接表

    public Graph(int V) {
        this.V = V;
        adj = new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public Graph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}
