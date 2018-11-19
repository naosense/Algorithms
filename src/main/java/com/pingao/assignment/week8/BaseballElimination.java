package com.pingao.assignment.week8;

import com.pingao.utils.ResourceUtils;
import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by pingao on 2018/11/19.
 */
public class BaseballElimination {
    private final int n;
    private final int[][] scores;
    private final int[][] against;
    private final Map<String, Integer> teamIndex = new HashMap<>();

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        validateNotNull(filename);
        In in = new In(filename);
        n = in.readInt();
        scores = new int[n][3];
        against = new int[n][n];

        int i = 0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            if (line == null || line.isEmpty()) {
                continue;
            }
            String[] columns = line.trim().split("\\s+");
            teamIndex.put(columns[0], i);
            for (int j = 0; j < 3 + n; j++) {
                if (j < 3) {
                    scores[i][j] = Integer.parseInt(columns[j + 1]);
                } else {
                    against[i][j - 3] = Integer.parseInt(columns[j + 1]);
                }
            }
            i++;
        }
    }

    // number of teamIndex
    public int numberOfTeams() {
        return n;
    }

    // all teamIndex
    public Iterable<String> teams() {
        return teamIndex.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        validateNotNull(team);
        validateTeam(team);
        return scores[teamIndex.get(team)][0];
    }

    // number of losses for given team
    public int losses(String team) {
        validateNotNull(team);
        validateTeam(team);
        return scores[teamIndex.get(team)][1];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validateNotNull(team);
        validateTeam(team);
        return scores[teamIndex.get(team)][2];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validateNotNull(team1);
        validateTeam(team1);
        validateNotNull(team2);
        validateTeam(team2);
        return against[teamIndex.get(team1)][teamIndex.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validateNotNull(team);
        validateTeam(team);

        int winsUpperBound = wins(team) + remaining(team);

        // trivial elimination
        for (String t : teams()) {
            if (t.equals(team)) {
                continue;
            }
            if (winsUpperBound < wins(t)) {
                return true;
            }
        }

        // nontrivial elimination
        int index = teamIndex.get(team);
        int gamePairs = cn2(n - 1);
        int teamLeft = n - 1;
        int ends = 2;
        int V = gamePairs + teamLeft + ends;
        int gameCountOfRemaining = 0;
        int k = 0;

        FlowNetwork network = new FlowNetwork(V);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i != index && j != index) {
                    gameCountOfRemaining += against[i][j];
                    network.addEdge(new FlowEdge(V - 2, k, against[i][j]));

                    if (i < index) {
                        network.addEdge(new FlowEdge(k, gamePairs + i, Double.POSITIVE_INFINITY));
                    } else {
                        network.addEdge(new FlowEdge(k, gamePairs + i - 1, Double.POSITIVE_INFINITY));
                    }

                    if (j < index) {
                        network.addEdge(new FlowEdge(k, gamePairs + j, Double.POSITIVE_INFINITY));
                    } else {
                        network.addEdge(new FlowEdge(k, gamePairs + j - 1, Double.POSITIVE_INFINITY));
                    }

                    k++;
                }
            }

            if (i != index) {
                if (i < index) {
                    network.addEdge(new FlowEdge(gamePairs + i, V - 1, winsUpperBound - scores[i][0]));
                } else {
                    network.addEdge(new FlowEdge(gamePairs + i - 1, V - 1, winsUpperBound - scores[i][0]));
                }
            }
        }

        FordFulkerson ff = new FordFulkerson(network, V - 2, V - 1);
        return ff.value() < gameCountOfRemaining;
    }

    // subset R of teamIndex that eliminates given team; null if not eliminated

    public Iterable<String> certificateOfElimination(String team) {
        validateNotNull(team);

        int winsUpperBound = wins(team) + remaining(team);

        // trivial elimination
        for (String t : teams()) {
            if (t.equals(team)) {
                continue;
            }
            if (winsUpperBound < wins(t)) {
                List<String> subset = new ArrayList<>();
                subset.add(t);
                return subset;
            }
        }

        // nontrivial elimination
        int index = teamIndex.get(team);
        int gamePairs = cn2(n - 1);
        int teamLeft = n - 1;
        int ends = 2;
        int V = gamePairs + teamLeft + ends;
        int gameCountOfRemaining = 0;
        int k = 0;

        FlowNetwork flowNetwork = new FlowNetwork(V);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i != index && j != index) {
                    gameCountOfRemaining += against[i][j];
                    flowNetwork.addEdge(new FlowEdge(V - 2, k, against[i][j]));

                    if (i < index) {
                        flowNetwork.addEdge(new FlowEdge(k, gamePairs + i, Double.POSITIVE_INFINITY));
                    } else {
                        flowNetwork.addEdge(new FlowEdge(k, gamePairs + i - 1, Double.POSITIVE_INFINITY));
                    }

                    if (j < index) {
                        flowNetwork.addEdge(new FlowEdge(k, gamePairs + j, Double.POSITIVE_INFINITY));
                    } else {
                        flowNetwork.addEdge(new FlowEdge(k, gamePairs + j - 1, Double.POSITIVE_INFINITY));
                    }

                    k++;
                }
            }
            if (i != index) {
                if (i < index) {
                    flowNetwork.addEdge(new FlowEdge(gamePairs + i, V - 1, winsUpperBound - scores[i][0]));
                } else {
                    flowNetwork.addEdge(new FlowEdge(gamePairs + i - 1, V - 1, winsUpperBound - scores[i][0]));
                }
            }
        }

        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, V - 2, V - 1);
        if (fordFulkerson.value() < gameCountOfRemaining) {
            List<String> subset = new ArrayList<>();
            for (String t : teams()) {
                if (t.equals(team)) {
                    continue;
                }
                int i = teamIndex.get(t);
                if (fordFulkerson.inCut(gamePairs + (i < index ? i : i - 1))) {
                    subset.add(t);
                }
            }
            return subset;
        } else {
            return null;
        }
    }

    private int cn2(int n) {
        return n * (n - 1) / 2;
    }

    private void validateNotNull(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s can't be null");
        }
    }

    private void validateTeam(String team) {
        if (teamIndex.get(team) == null) {
            throw new IllegalArgumentException("can't find " + team + "in table");
        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(ResourceUtils.getTestResourcePath("week8-team4.txt"));
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
