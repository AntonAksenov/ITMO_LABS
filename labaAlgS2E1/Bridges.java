package labaAlgS2E1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bridges {
    static ArrayList<Integer>[] edges;
    static boolean[] used;
    static int[] backEdgeTime;
    static int t;
    static int[] inTime;
    static Map<String, Integer> edgesMap = new HashMap<>();
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
            int v = in.nextInt() - 1, u = in.nextInt() - 1;
            edges[u].add(v);
            edges[v].add(u);
            edgesMap.put(v + " " + u, i + 1);
        }

        for (int i = 0; i < n; i++) {
            dfs(i, -1);
        }

        System.out.println(ans.size());
        for(int i=0; i<ans.size();i++) {
            System.out.print(ans.get(i) + " ");
        }
    }

    static void dfs(int v, int p) {
        if (!used[v]) {
            used[v] = true;
            inTime[v] = backEdgeTime[v] = t++;
            for (int u : edges[v]) {
                if (u != p) {
                    dfs(u, v);
                    backEdgeTime[v] = Math.min(backEdgeTime[v], backEdgeTime[u]);
                    if (backEdgeTime[u] > inTime[v]) {
                        ans.add(edgesMap.get(v + " " + u));
                    }
                }
            }
        }
    }

}
