import java.util.*
import kotlin.math.min

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val edges = Array(n) { HashMap<Int, Int>() }
    val m = sc.nextInt()
    repeat(m) {
        var v = sc.nextInt() - 1
        var u = sc.nextInt() - 1
        var l = sc.nextInt()
        if (!edges[v].contains(u) || edges[v][u]!! > l) {
            edges[v][u] = l
            edges[u][v] = l
        }
    }

    val dist = Array(n) { Int.MAX_VALUE }
    dist[0] = 0
    val used = BooleanArray(n) { false }
    var ans = 0
    repeat(n) {
        var min_dist = Int.MAX_VALUE
        var u = 0
        for (j in 0 until n) {
            if (!used[j] && dist[j] < min_dist) {
                min_dist = dist[j]
                u = j
            }
        }
        ans += min_dist
        used[u] = true
        for (v in 0 until n) {
            if (edges[u][v] != null) {
                dist[v] = min(dist[v], edges[u][v]!!)
            }
        }
    }
    print(ans)

}
