package labaAlgS2E1

import java.util.*
import kotlin.math.sqrt

object FlatMinSpanningTree {

    @JvmStatic
    fun main(args: Array<String>) {
        val sc = Scanner(System.`in`)
        val n = sc.nextInt()

        val edges = Array(n * n) { Triple(0, 0, 0) }
        var ans = 0.0
        val vertex = Array(n) { Pair(sc.nextInt(), sc.nextInt()) }

        for (i in 0 until n) {
            val (x1, y1) = vertex[i]
            for (j in 0 until n) {
                val (x2, y2) = vertex[j]
                edges[i * n + j] = Triple((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2), i, j)
            }
        }

        edges.sortWith(kotlin.Comparator { o1, o2 ->
            if (o1.first < o2.first) {
                1
            } else if (o1.first > o2.first) {
                -1
            } else {
                if (o1.second < o2.second) {
                    1
                } else if (o1.second > o2.second) {
                    -1
                } else {
                    if (o1.third < o2.third) {
                        1
                    } else if (o1.third > o2.third) {
                        -1
                    } else {
                        0
                    }
                }
            }
        })
        val treeId = Array(n) { it }

        for (i in 0 until edges.size) {
            val (l, v, u) = edges[i]
            if (treeId[v] != treeId[u]) {
                ans += sqrt(l.toDouble())
                val oldId = treeId[u]
                val newId = treeId[v]
                for (j in 0 until n) {
                    if (treeId[j] == oldId) {
                        treeId[j] = newId
                    }
                }
            }
        }

        println(ans)
    }
}

        /*
        while (!q.isEmpty()) {
            val v = q.poll()
            used[v] = true
            repeat(n) {
                if (!used[it] && edges[v][it] < d[it]) {
                    d[it] = sqrt(edges[v][it])
                    p[it] = v
                }
            }
            if (p[v] != -1) {
                ans += edges[p[v]][v]
            }
        }*/