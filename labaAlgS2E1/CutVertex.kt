package labaAlgS2E1

import java.util.Scanner
import kotlin.math.min

object CutVertex {

    lateinit var edges: Array<MutableList<Int>>
    lateinit var used: BooleanArray
    var t = 0
    lateinit var inTime: IntArray
    lateinit var backEdgeTime: IntArray
    lateinit var ans: BooleanArray

    @JvmStatic
    fun main(args: Array<String>) {
        val sc = Scanner(System.`in`)
        val n = sc.nextInt()
        val m = sc.nextInt()
        edges = Array(n) { mutableListOf<Int>() }
        used = BooleanArray(n)
        ans = BooleanArray(n)
        t = 0
        inTime = IntArray(n)
        backEdgeTime = IntArray(n)

        for (i in 0 until m) {
            val v = sc.nextInt() - 1
            val u = sc.nextInt() - 1
            edges[u].add(v)
            edges[v].add(u)
        }

        for (i in 0 until n) {
            if (!used[i]) {
                if (dfs(i, -1) > 1) {
                    ans[i] = true
                }
            }
        }

        var ansCnt = 0
        for (i in 0 until n) {
            if (ans[i]) {
                ansCnt++
            }
        }

        println(ansCnt)
        for (i in 0 until n) {
            if (ans[i]) {
                print((i + 1).toString() + " ")
            }
        }
    }

    internal fun dfs(v: Int, p: Int): Int {
        var ch = 0
        used[v] = true
        backEdgeTime[v] = t++
        inTime[v] = backEdgeTime[v]
        for (u in edges[v]) {
            if (u != p) {
                if (used[u]) {
                    backEdgeTime[v] = min(backEdgeTime[v], inTime[u])
                } else {
                    dfs(u, v)
                    backEdgeTime[v] = min(backEdgeTime[v], backEdgeTime[u])
                    if (backEdgeTime[u] >= inTime[v] && p != -1) {
                        ans[v] = true
                    }
                    ch++
                }
            }
        }
        return ch
    }
}