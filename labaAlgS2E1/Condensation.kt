package labaAlgS2E1

import java.util.*

object Condensation {

    lateinit var edges: Array<MutableList<Int>>
    lateinit var backEdges: Array<MutableList<Int>>
    lateinit var used: BooleanArray
    var outOrder = ArrayDeque<Int>(0)
    lateinit var component: Array<Int>

    @JvmStatic
    fun main(args: Array<String>) {
        val sc = Scanner(System.`in`)
        val n = sc.nextInt()

        edges = Array(n) { mutableListOf<Int>() }
        backEdges = Array(n) { mutableListOf<Int>() }
        used = BooleanArray(n) { false }
        component = Array(n) { -1 }

        val m = sc.nextInt()
        for (i in 0 until m) {
            val a = sc.nextInt() - 1
            val b = sc.nextInt() - 1
            edges[a].add(b)
            backEdges[b].add(a)
        }

        for (i in 0 until n) {
            if (!used[i]) {
                dfs1(i)
            }
        }
        used = BooleanArray(n) { false }
        for (i in 0 until n) {
            var v = outOrder.pollLast()
            if (!used[v]) {
                dfs2(v, v)
            }
        }

        var ans = 0
        for (v in 0 until n) {
            for (u in edges[v]) {
                if (component[v] != component[u]) {
                    ans++
                }
            }
        }
        print(ans)
    }

    fun dfs1(v: Int) {
        used[v] = true
        for (i in 0 until edges[v].size) {
            if (!used[edges[v][i]]) {
                dfs1(edges[v][i])
            }
        }
        outOrder.addLast(v)
    }

    fun dfs2(v: Int, i: Int) {
        used[v] = true
        component[v] = i
        for (u in backEdges[v]) {
            if (!used[u]) {
                dfs2(u, i)
            }
        }
    }

}
