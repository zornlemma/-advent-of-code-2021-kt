import kotlin.math.abs
import kotlin.math.roundToInt

private fun part1(input: List<String>) {
    val arr = input[0].split(",").map { it.toInt() }.toMutableList()
    arr.sort()
    // consider at position x, there are m points; l left points; r right points;
    // if moving left 1, total will - l + m + r ; moving right 1, will - r + m + l
    val m = arr.size / 2
    val x = arr[m]
    var acc = 0
    for (i in 0 until m) {
        acc += x - arr[i]
    }
    for (i in m + 1 until arr.size) {
        acc += arr[i] - x
    }
    println(acc)
}


private fun part2(input: List<String>) {


    val arr = input[0].split(",").map { it.toInt() }.toMutableList()
    arr.sort()

    fun step2cost(n: Double): Double {
        return n * (n+1)/2
    }
    /*
    t = \sum 0.5 (x - a_i)^2 + 0.5 | x - a_i |
     */
    fun getCost(x: Double): Double {
        var acc = 0.0
        for (i in arr.indices) {
            acc += step2cost(abs(x - arr[i].toDouble()))
        }
        return acc
    }
    var l0 = arr[0].toDouble()
    var r0 = arr.last().toDouble()
    var l1 = (l0 + r0 * 0.618) / 1.618
    var r1 = (l0 * 0.618 + r0) / 1.618
    while (l1 + 1e-3 < r1) {
        val c1 = getCost(l1)
        val c2 = getCost(r1)
        if (c1 < c2) {
            r0 = r1
            r1 = l1
            l1 = (l0 + r0 * 0.618) / 1.618
        } else {
            l0 = l1
            l1 = r1
            r1 = (l0 * 0.618 + r0) / 1.618
        }
    }
    println("l1 = ${l1}")
    val ans1 = getCost(l1.toInt().toDouble()).toInt()
    println(ans1)
    val ans2 = getCost(l1.toInt().plus(1).toDouble()).toInt()
    println(ans2)
}

fun main() {
    val input = readInput("day7")
    part1(input)
    part2(input)
}