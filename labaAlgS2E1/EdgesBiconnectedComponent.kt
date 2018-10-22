package labaAlgS2E1

import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min

lateinit var edges: Array<MutableList<Int>>
lateinit var used: BooleanArray
var t = 0
lateinit var inTime: IntArray
lateinit var backEdgeTime: IntArray
lateinit var ans: BooleanArray

var maxComponent = 0
val edgesMap = HashMap<Pair<Int, Int>, Int>()
lateinit var component: IntArray


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
    component = IntArray(m) { -1 }

    for (i in 0 until m) {
        val v = sc.nextInt() - 1
        val u = sc.nextInt() - 1
        edges[u].add(v)
        edges[v].add(u)
        edgesMap[Pair(v, u)] = i
        edgesMap[Pair(u, v)] = i
    }

    for (i in 0 until n) {
        if (!used[i]) {
            if (dfs(i, -1) > 1) {
                ans[i] = true
            }
        }
    }
    used = BooleanArray(n)
    for (v in 0 until n) {
        if (!used[v]) {
            maxComponent++
            paint(v, maxComponent, -1)
        }
    }

    val componentNumber = IntArray(n) { -1 }
    var componentCounter = 0

    for (i in 0 until m) {
        val comp = componentNumber[component[i]]
        if (comp == -1) {
            componentNumber[component[i]] = ++componentCounter
            component[i] = componentCounter
        } else {
            component[i] = comp
        }
    }

    println(componentCounter)
    for (i in 0 until component.size) {
        print("${component[i]} ")
    }

}

fun paint(v: Int, color: Int, parent: Int) {
    used[v] = true
    for (u in edges[v]) {
        if (u != parent) {
            if (!used[u]) {
                if (backEdgeTime[u] >= inTime[v]) {
                    component[edgesMap[Pair(v, u)]!!] = ++maxComponent
                    paint(u, maxComponent, v)
                } else {
                    component[edgesMap[Pair(v, u)]!!] = color
                    paint(u, color, v)
                }
            } else if (inTime[u] < inTime[v]) {
                component[edgesMap[Pair(v, u)]!!] = color
            }
        }
    }
}

fun dfs(v: Int, p: Int): Int {
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

//6 7
//1 2
//2 3
//3 1
//1 4
//4 5
//4 6
//5 6

//2
//1 1 1 2 2 2
