private fun part1(input: List<String>) {
    val n = input[0].length
    println("n = ${n}")
    val ttl = input.size
    println("ttl = ${ttl}")
    val cnt = MutableList(n){0}
    for (line in input) {
        val arr = line.map { it.toInt() - '0'.toInt() }
        for (j in 0 until n) {
            cnt[j] += arr[j]
        }
    }
    val gamma_str = cnt.map { if (it * 2 >= ttl) 1 else 0 }.joinToString("") { it.toString() }
    val gamma = gamma_str.toInt(2)
    val epsilon = ((1 shl n) - 1) - gamma
    println(gamma * epsilon)
}

private fun part2(input: List<String>) {
    val n = input[0].length
    println("n = ${n}")
    val ttl = input.size
    println("ttl = ${ttl}")
    val cnt = MutableList(n){0}
    var lineNums = (0 until ttl).toList()
    for (j in 0 until n) {
        val (lineNums0, lineNums1) = lineNums.partition { i -> input[i][j] == '0' }
        if (lineNums1.size >= lineNums0.size) {
            lineNums = lineNums1
        } else {
            lineNums = lineNums0
        }
        if (lineNums.size == 1) break
    }
//    println("oxy_1 lineNums = ${lineNums.size}")
    val oxy_1 = input[lineNums[0]].toInt(2)
    lineNums = (0 until ttl).toList()
    for (j in 0 until n) {
        val (lineNums0, lineNums1) = lineNums.partition { i -> input[i][j] == '0' }
        if (lineNums1.size < lineNums0.size) {
            lineNums = lineNums1
        } else {
            lineNums = lineNums0
        }
        if (lineNums.size == 1) break
    }
//    println("co2 lineNums = ${lineNums.size}")
    val co2_1 = input[lineNums[0]].toInt(2)
    println(oxy_1 * co2_1)
}

fun main() {
    val input = readInput("day3")
    part1(input)
    part2(input)
}