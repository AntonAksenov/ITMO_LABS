package labaAlgS2E1

import java.util.*
import kotlin.math.min

object VertexBiconnectedComponent {

    lateinit var edges: Array<MutableList<Int>>
    lateinit var used: BooleanArray
    var t = 0
    lateinit var inTime: IntArray
    lateinit var backEdgeTime: IntArray
    lateinit var cutVertex: BooleanArray
    lateinit var component: IntArray

    @JvmStatic
    fun main(args: Array<String>) {
        val sc = Scanner(System.`in`)
        val n = sc.nextInt()
        val m = sc.nextInt()
        edges = Array(n) { mutableListOf<Int>() }
        used = BooleanArray(n)
        cutVertex = BooleanArray(n)
        t = 0
        inTime = IntArray(n)
        backEdgeTime = IntArray(n)
        component = IntArray(n)

        for (i in 0 until m) {
            val v = sc.nextInt() - 1
            val u = sc.nextInt() - 1
            edges[u].add(v)
            edges[v].add(u)
        }

        for (i in 0 until n) {
            if (!used[i]) {
                if (cutVertexDfs(i, -1) > 1) {
                    cutVertex[i] = true
                }
            }
        }

        for (i in 0 until n) {
            used[i] = false
        }

        var count = 0
        for (i in 0 until n) {
            if (!used[i]) {
                biconnectedComponentdfs(i, -1, ++count)
            }
        }

        println(count)
        for (i in 0 until n) {
            print(component[i])
            print(" ")
        }
    }

    fun cutVertexDfs(v: Int, p: Int): Int {
        var ch = 0
        used[v] = true
        backEdgeTime[v] = t++
        inTime[v] = backEdgeTime[v]
        for (u in edges[v]) {
            if (u != p) {
                if (used[u]) {
                    backEdgeTime[v] = min(backEdgeTime[v], inTime[u])
                } else {
                    cutVertexDfs(u, v)
                    backEdgeTime[v] = min(backEdgeTime[v], backEdgeTime[u])
                    if (backEdgeTime[u] >= inTime[v] && p != -1) {
                        cutVertex[v] = true
                    }
                    ch++
                }
            }
        }
        return ch
    }

    fun biconnectedComponentdfs(v: Int, p: Int, count: Int) {
        used[v] = true
        component[v] = count
        for (u in edges[v]) {
            if (u != p && !used[u]) {
                if (!cutVertex[u]) {
                    biconnectedComponentdfs(u, v, count)
                }
            }
        }
    }

}
