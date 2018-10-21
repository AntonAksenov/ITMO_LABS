package labaAlgS2E1;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class TopSort {
    static ArrayList<Integer>[] edges;
    static int[] used;
    static Stack<Integer> ans = new Stack<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), m = in.nextInt();
        edges = new ArrayList[n];
        used = new int[n];

        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList();
        }

        for (int i = 0; i < m; i++) {
            edges[in.nextInt() - 1].add(in.nextInt() - 1);
        }

        for (int i = 0; i < n; i++) {
            dfs(i);
        }

        while (!ans.empty()) {
            System.out.print((ans.pop() + 1) + " ");
        }
    }

    static void dfs(int v) {
        if (used[v] == 1) {
            System.out.println(-1);
            System.exit(0);
        } else if (used[v] != 2) {
            used[v] = 1;
            for (int u : edges[v]) {
                dfs(u);
            }
            ans.push(v);
            used[v] = 2;
        }
    }

}

//6 6
//1 2
//3 2
//4 2
//2 5
//6 5
//4 6

//4 6 3 1 2 5 
