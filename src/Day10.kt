private val end2start = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
private val endVal = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

private fun part1(input: List<String>) {
    var ttl = 0
    for (line in input) {
        val st = mutableListOf<Char>()
        for (ch in line) {
            if (end2start.containsKey(ch)) {
                if (st.last() == end2start[ch]) {
                    st.removeLast()
                } else {
                    ttl += endVal[ch]!!
                    break
                }
            } else {
                st.add(ch)
            }
        }
    }
    println("ttl = ${ttl}")
}

private val startPoints = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

private fun part2(input: List<String>) {
    val scores = mutableListOf<Long>()
    line@
    for (line in input) {
        val st = mutableListOf<Char>()
        for (ch in line) {
            if (end2start.containsKey(ch)) {
                if (st.last() == end2start[ch]) {
                    st.removeLast()
                } else {
                    continue@line
                }
            } else {
                st.add(ch)
            }
        }
        var acc = 0L
        for (ch in st.reversed()) {
            acc = acc * 5 + startPoints[ch]!!
        }
        scores.add(acc)
    }
    scores.sort()
    println("middle score = ${scores[scores.size / 2]}")
}

fun main() {
    val input = readInput("day10")
    part1(input)
    part2(input)
}