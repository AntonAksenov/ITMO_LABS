import java.util.*

var n = 0
lateinit var edges: Array<MutableList<Int>>
lateinit var backEdges: Array<MutableList<Int>>
lateinit var used: BooleanArray
var order = mutableListOf<Int>()
lateinit var comp: IntArray

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    n = sc.nextInt()
    val m = sc.nextInt()
    var iToName = Array(n) { "" }
    var nameToI = HashMap<String, Int>()
    repeat(n) {
        var s = sc.next()
        iToName[it] = s
        nameToI[s] = it
    }
    edges = Array(2 * n) { mutableListOf<Int>() }
    backEdges = Array(2 * n) { mutableListOf<Int>() }

    // i+n for !i
    repeat(m) {
        var v = sc.next()
        sc.next()
        var u = sc.next()

        var vS = v[0] == '-'
        var uS = u[0] == '-'

        var vN = nameToI[v.substring(1)]!!
        var uN = nameToI[u.substring(1)]!!

        edges[vN + if (vS) n else 0].add(uN + if (uS) n else 0)
        backEdges[uN + if (uS) n else 0].add(vN + if (vS) n else 0)
    }
    used = BooleanArray(2 * n) { false }
    for (i in 0 until 2 * n) {
        if (!used[i]) {
            dfs1(i)
        }
    }
    comp = IntArray(2 * n) { -1 }
    var cnt = 0
    for (v in order.lastIndex downTo 0) {
        if (comp[v] == -1) {
            dfs2(v, cnt++)
        }
    }
    for (i in 0 until n) {
        if (comp[i] == comp[i + n]) {
            print("-1")
            return
        }
    }
    var ans = 0
    for (i in 0 until 2 * n) {
        if (comp[i] > comp[(i + n) % (2 * n)]) {
            ans++
        }
    }
    println(ans)
    for (i in 0 until 2 * n) {
        if (comp[i] > comp[(i + n) % (2 * n)]) {
            println(iToName[i])
        }
    }
}

fun dfs1(v: Int) {
    used[v] = true
    for (u in edges[v]) {
        if (!used[u]) {
            dfs1(u)
        }
    }
    order.add(v)
}

fun dfs2(v: Int, compN: Int) {
    comp[v] = compN
    for (u in backEdges[v]) {
        if (comp[u] == -1) {
            dfs2(u, compN)
        }
    }
}
