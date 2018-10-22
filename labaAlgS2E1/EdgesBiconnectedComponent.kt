package labaAlgS2E1

import java.util.*
import kotlin.math.min

object EdgesBiconnectedComponent {
    lateinit var edges: Array<MutableList<Pair<Int, Int>>>
    lateinit var isBridge: Array<Boolean>
    lateinit var used: BooleanArray
    var timer = 0
    lateinit var inTime: IntArray
    lateinit var maxBackEdge: IntArray
    var compN = 0
    lateinit var comp: IntArray

    @JvmStatic
    fun main(args: Array<String>) {
        val sc = Scanner(System.`in`)
        val n = sc.nextInt()
        edges = Array(n) { mutableListOf<Pair<Int, Int>>() }

        val m = sc.nextInt()
        isBridge = Array(m + 1) { false }
        for (i in 0 until m) {
            var v = sc.nextInt() - 1
            var u = sc.nextInt() - 1
            edges[u].add(Pair(v, i + 1))
            edges[v].add(Pair(u, i + 1))
        }
        used = BooleanArray(n) { false }
        inTime = IntArray(n) { 0 }
        maxBackEdge = IntArray(n) { 0 }
        for (i in 0 until n) {
            if (!used[i]) {
                bridgesDfs(i, -1, -1)
            }
        }

        timer = 0
        used = BooleanArray(n) { false }

        comp = IntArray(n) { 0 }
        for (i in 0 until n) {
            if (!used[i]) {
                comp[i] = ++compN
                compDfs(i, compN)
            }
        }
        println(compN)
        for (i in 0 until n) {
            print("${comp[i]} ")
        }
    }

    fun bridgesDfs(v: Int, from: Int = -1, lastEdge: Int = -1) {
        used[v] = true
        inTime[v] = timer++
        maxBackEdge[v] = inTime[v]
        for ((u, e) in edges[v]) {
            if (u != from || e != lastEdge) {
                if (used[u]) {
                    maxBackEdge[v] = min(maxBackEdge[v], inTime[u])
                } else {
                    bridgesDfs(u, v, e)
                    maxBackEdge[v] = min(maxBackEdge[v], maxBackEdge[u])
                    if (maxBackEdge[u] > inTime[v]) {
                        isBridge[e] = true
                    }
                }
            }
        }
    }

    fun compDfs(v: Int, counter: Int) {
        used[v] = true
        for ((u, e) in edges[v]) {
            if (used[u]) continue
            if (isBridge[e]) {
                compN++
                comp[u] = compN
                compDfs(u, compN)
            } else {
                comp[u] = counter
                compDfs(u, counter)
            }
        }
    }

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
