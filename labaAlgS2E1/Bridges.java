package labaAlgS2E1;

import javafx.util.Pair;
import java.util.*;

public class Bridges {
    static ArrayList<Pair<Integer, Integer>>[] edges;
    static boolean[] used;
    static int[] backEdgeTime;
    static int t;
    static int[] inTime;
    static ArrayList<Integer> ans = new ArrayList<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), m = in.nextInt();
        edges = new ArrayList[n];
        used = new boolean[n];
        backEdgeTime = new int[n];
        t = 0;
        inTime = new int[n];

        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList();
        }

        for (int i = 0; i < m; i++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            edges[u].add(new Pair<>(v, i + 1));
            edges[v].add(new Pair<>(u, i + 1));
        }

        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                dfs(i, -1);
            }
        }

        System.out.println(ans.size());
        ans.sort(Integer::compareTo);
        for (int i = 0; i < ans.size(); i++) {
            System.out.print(ans.get(i) + " ");
        }
    }

    static void dfs(int v, int p) {
        used[v] = true;
        inTime[v] = backEdgeTime[v] = t++;
        for (Pair<Integer, Integer> edge : edges[v]) {
            int u = edge.getKey();
            if (u != p) {
                if (used[u]) {
                    backEdgeTime[v] = Math.min(backEdgeTime[v], inTime[u]);
                } else {
                    dfs(u, v);
                    backEdgeTime[v] = Math.min(backEdgeTime[v], backEdgeTime[u]);
                    if (backEdgeTime[u] > inTime[v]) {
                        ans.add(edge.getValue());
                    }
                }
            }
        }
    }

}

//6 7
//1 2
//2 3
//3 4
//1 3
//4 5
//4 6
//5 6

//1
//3
