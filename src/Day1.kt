private fun part1(input: List<String>) {
    val arr = input.map{ it.toInt() }
    val ans = arr.zipWithNext().count { (a, b) -> a < b }
    println(ans)
}

private fun part2(input: List<String>) {
    val arr = input.map{ it.toInt() }
    val n = arr.size
//    var acc = 0
//    for (i in 0 until 3) {
//        acc += arr[i]
//    }
//    var prev = acc
//    var ttl = 0
//    for (i in 3 until n) {
//        acc = acc + arr[i] - arr[i-3]
//        if (acc > prev) {
//            ttl += 1
//        }
//        prev = acc
//    }
    var ttl = 0
    for (i in 3 until n) {
        if (arr[i] > arr[i-3]) {
            ttl += 1
        }
    }
    println(ttl)
}

fun main() {
    val input = readInput("day1")
    part1(input)
    part2(input)
}