package labaAlgS2E1

import java.util.*
import kotlin.math.min
import kotlin.math.sqrt

object FlatMinSpanningTree {

    @JvmStatic
    fun main(args: Array<String>) {
        val sc = Scanner(System.`in`)
        val n = sc.nextInt()
        val vertex = Array(n) { Pair(0, 0) }
        for (i in 0 until n) {
            vertex[i] = Pair(sc.nextInt() - 1, sc.nextInt() - 1)
        }

        val dist = Array(n) { Double.MAX_VALUE }
        dist[0] = 0.0
        val used = BooleanArray(n) { false }
        var ans = 0.0
        for (i in 0 until n) {
            var minDist = Double.MAX_VALUE
            var u = 0
            for (j in 0 until n) {
                if (!used[j] && dist[j] < minDist) {
                    minDist = dist[j]
                    u = j
                }
            }
            ans += minDist
            used[u] = true
            for (v in 0 until n) {
                val (x1, y1) = vertex[v]
                val (x2, y2) = vertex[u]
                dist[v] = min(dist[v], sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)).toDouble())
                )
            }
        }
        print(ans)
    }
}

//2
//0 0
//1 1

//1.4142135624
