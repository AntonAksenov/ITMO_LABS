package labaAlgS2E1

import java.util.*

lateinit var edges: Array<MutableList<Int>>
lateinit var backEdges: Array<MutableList<Int>>
lateinit var used: BooleanArray
var outOrder = ArrayDeque<Int>(0)
lateinit var component: Array<Int>


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
    var temp = 0
    for (v in outOrder) {
        if (!used[v]) {
            dfs2(v, temp++)
        }
    }

    var edgesSet = HashSet<Pair<Int, Int>>()
    
    for (v in 0 until n) {
        for (u in edges[v]) {
            if (component[v] != component[u] && !edgesSet.contains(Pair(component[v], component[u]))) {
                edgesSet.add(Pair(component[v], component[u]))
            }
        }
    }
    print(edgesSet.size)
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
