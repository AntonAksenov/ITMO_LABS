package labaAlgS2E1

import java.io.File
import java.util.*
import kotlin.math.*

fun main(args: Array<String>) {
    val sc = Scanner(File("avia.in"))
    val n = sc.nextInt()
    val d = Array(n) { Array(n) { sc.nextInt() } }

    for (k in 0 until n) {
        for (i in 0 until n) {
            for (j in 0 until n) {
                d[i][j] = min(d[i][j], d[i][k] + d[k][j])
            }
        }
    }

    var ans = 0
    for (i in 0 until n) {
        for (j in 0 until n) {
            ans = max(ans, d[i][j])
        }
    }
    File("avia.out").printWriter().use {
        it.print(ans)
    }
}
