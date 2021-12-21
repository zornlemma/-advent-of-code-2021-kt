import kotlin.math.*

private val re = Regex("x=(.+)\\.{2}(.+),.*y=(.+)\\.{2}(.+)$")

private fun parseLine(s: String): List<Int> {
    val mr = re.find(s)!!
    return mr.groupValues.subList(1, 5).map { it.toInt() }
}

/*
y + (y-1) + (y-2) + ... + (y-n+1)
(2y-n+1) * n / 2 = b
maximum: n = y + 1 or y
x + (x-1) + (x-2) + ... + (x-n+1) ; n <= x
(2x-n+1) * n / 2 = a
(n+1) * n / 2 ; n > x
 */
/*
(2y+1)n - n^2 - 2b == 0
n^2 - (2y+1)n + 2b = 0
n = (2y+1) + sqrt( (2y+1)^2 - 8b )
    /  2
y = (2b/n + n - 1 ) / 2
 */

private data class TargetArea(
        val xt1: Int,
        val xt2: Int,
        val yt1: Int,
        val yt2: Int,
) {
    fun x2n(x: Int): Pair<Int, Int> {
        var vx = x
        var acc = 0
        var nMin = x+1
        var nMax = 0
        for (i in 1 .. x) {
            acc += vx
            vx -= 1
            if ((xt1..xt2).contains(acc)) {
                nMin = min(nMin, i)
                nMax = max(nMax, i)
            }
        }
        return Pair(nMin, nMax)
    }
    fun n2y(n: Int): Pair<Int,Int> {
        val y1 = ((2*yt1 / n.toDouble() + n - 1) / 2.0).let{ ceil(it).toInt() }
        val y2 = ((2*yt2 / n.toDouble() + n - 1) / 2.0).let{ floor(it).toInt() }
        var maxH = 0
        return Pair(y1,y2)
    }
}

private fun part1(input: List<String>) {
    val (xt1, xt2, yt1, yt2) = parseLine(input[0])
    val targetArea = TargetArea(xt1, xt2, yt1, yt2)
    var maxH = 0
    for (x in xt2 downTo 1) {
        val nRange = targetArea.x2n(x)
        for (n in nRange.first..nRange.second) {
            val (y1,y2) = targetArea.n2y(n)
            for (y in y1..y2) {
                val h = (y+1) * y / 2
                maxH = max(maxH, h)
            }
        }
    }
    println("maxH = ${maxH}")
}

private fun part2(input: List<String>) {
    val (xt1, xt2, yt1, yt2) = parseLine(input[0])
    val targetArea = TargetArea(xt1, xt2, yt1, yt2)
    var cnt = 0
    for (x in xt2 downTo 1) {
        val nRange = targetArea.x2n(x)
        val ySet = mutableSetOf<Int>()
        for (n in nRange.first..nRange.second) {
            val (y1,y2) = targetArea.n2y(n)
            ySet.addAll(y1..y2)
        }
        if (nRange.second == x) {
            val yDiff = yt2 - yt1
            // don't know exactly. but likely big enough for (2b/n)/2 small than (0,1)
            for (n in nRange.second + 1 .. nRange.second + yDiff*10) {
                val (y1,y2) = targetArea.n2y(n)
                ySet.addAll(y1..y2)
            }
        }
//        ySet.forEach{ print("($x,$it) ") }
//        println()
        cnt += ySet.size
    }
    println("cnt = ${cnt}")
}

fun main() {
    val input = readInput("day17")
    part1(input)
    part2(input)
}
