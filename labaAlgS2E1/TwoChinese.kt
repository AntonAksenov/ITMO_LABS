package labaAlgS2E1

import java.util.*
import kotlin.math.max
import kotlin.math.min

lateinit var used: BooleanArray

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()

    val m = sc.nextInt()
    val edges = MutableList(m) { Triple(0, 0, 0) }
    val edges1 = Array(n) { mutableListOf<Int>() }
    repeat(m) {
        val a = sc.nextInt() - 1
        val b = sc.nextInt() - 1
        val l = sc.nextInt()
        edges[it] = Triple(a, b, l)
        edges1[a].add(b)
    }

    used = BooleanArray(n)
    dfs(0, edges1)
    var flag = false
    repeat(n) {
        flag = flag || !used[it]
    }
    if (flag) {
        println("NO")
    } else {
        println("YES")
        print(findMST(edges, n, 0))
    }
}

fun findMST(edges: MutableList<Triple<Int, Int, Int>>, n: Int, root: Int): Int {
    var res = 0
    val minEdge = Array(n) { Int.MAX_VALUE }
    for ((v, u, l) in edges) {
        minEdge[u] = min(l, minEdge[u])
    }
    for (v in 0 until n) {
        if (v != root) {
            res += minEdge[v]
        }
    }
    val zeroEdges = Array(n) { mutableListOf<Int>() }
    for ((v, u, l) in edges) {
        if (l == minEdge[u]) {
            zeroEdges[v].add(l - minEdge[u])
        }
    }
    used = BooleanArray(n)
    dfs(root, zeroEdges)
    var flag = false
    repeat(n) { flag = flag || !used[it] }
    if (flag) {
        return res
    }
    val newComponents = Cond.cond(n, zeroEdges)
    val newEdges = mutableListOf<Triple<Int, Int, Int>>()
    for (e in edges) {
        if (newComponents[e.first] != newComponents[e.second]) {
            newEdges.add(Triple(newComponents[e.first], newComponents[e.second], e.third - minEdge[e.second]))
        }
    }
    var newN = 0
    repeat(n) {
        newN = max(newN, newComponents[it])
    }
    res += findMST(newEdges, newN, newComponents[root])
    return res
}

fun dfs(v: Int, edges: Array<MutableList<Int>>) {
    used[v] = true
    for (u in edges[v]) {
        if (!used[u]) {
            dfs(u, edges)
        }
    }
}

object Cond {
    lateinit var edges: Array<MutableList<Int>>
    lateinit var backEdges: Array<MutableList<Int>>
    lateinit var used: BooleanArray
    var outOrder = ArrayDeque<Int>(0)
    lateinit var component: IntArray

    fun cond(n: Int, newEdges: Array<MutableList<Int>>): IntArray {
        edges = Array(n) { mutableListOf<Int>() }
        backEdges = Array(n) { mutableListOf<Int>() }
        used = BooleanArray(n) { false }
        component = IntArray(n) { -1 }

        for (i in 0 until n) {
            for (u in newEdges[i]) {
                edges[i].add(u)
                backEdges[u].add(i)
            }
        }

        for (i in 0 until n) {
            if (!used[i]) {
                dfs1(i)
            }
        }

        used = BooleanArray(n) { false }
        var temp = 0
        for (v in outOrder) {
            if (!used[v]) {
                dfs2(v, temp++)
            }
        }

        return component
    }

    fun dfs1(v: Int) {
        used[v] = true
        for (u in edges[v]) {
            if (!used[u]) {
                dfs1(u)
            }
        }
        outOrder.addFirst(v)
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
