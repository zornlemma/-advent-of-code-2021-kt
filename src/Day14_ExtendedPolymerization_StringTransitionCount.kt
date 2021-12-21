
private fun part1(input: List<String>) {
    val s0 = input[0]
    val insertMap = mutableMapOf<Char, MutableMap<Char,Char>>()
    for (i in 2 until input.size) {
        val line = input[i]
        val u = line[0]
        val v = line[1]
        val w = line.last()
        insertMap.getOrPut(u) { mutableMapOf() }[v] = w
    }
    var s = s0
    for (i in 1 .. 10) {
        s = change(s, insertMap)
    }
    println("s.length = ${s.length}")
    val sCharCnt = s.groupingBy { it }.eachCount()
    val diff = sCharCnt.values.maxOrNull()!! - sCharCnt.values.minOrNull()!!
    println("diff = ${diff}")
}

private fun change(src: String, insertMap: MutableMap<Char, MutableMap<Char, Char>>): String {
    val target = StringBuilder()
    for (i in 0 until src.length - 1) {
        target.append(src[i])
        val c = insertMap.getOrDefault(src[i], emptyMap())[src[i+1]]
        if (c != null) {
            target.append(c)
        }
    }
    target.append(src.last())
    return target.toString()
}

private fun part2(input: List<String>) {
    val s0 = input[0]
    val insertMap = mutableMapOf<Char, MutableMap<Char,Char>>()
    for (i in 2 until input.size) {
        val line = input[i]
        val u = line[0]
        val v = line[1]
        val w = line.last()
        insertMap.getOrPut(u) { mutableMapOf() }[v] = w
    }
    var ch2cnt = mutableMapOf<Pair<Char,Char>, Long>()
    for ((k1, k2) in s0.zipWithNext()) {
        ch2cnt[Pair(k1,k2)] = ch2cnt.getOrDefault(Pair(k1,k2), 0L) + 1L
    }
    for (i in 1..40) {
        val newCh2Cnt = mutableMapOf<Pair<Char,Char>, Long>()
        for ((k,v) in ch2cnt.entries) {
            val m = insertMap.getOrDefault(k.first, emptyMap())[k.second]
            if (m == null) {
                newCh2Cnt[k] = newCh2Cnt.getOrDefault(k, 0L) + v
            } else {
                newCh2Cnt[Pair(k.first, m)] = newCh2Cnt.getOrDefault(Pair(k.first, m),0L) + v
                newCh2Cnt[Pair(m, k.second)] = newCh2Cnt.getOrDefault(Pair(m, k.second),0L) + v
            }
        }
        ch2cnt = newCh2Cnt
    }
//    println("ch2cnt = ${ch2cnt}")
    val chCnt = mutableMapOf<Char, Long>()
    for ((k,v) in ch2cnt.entries) {
        chCnt[k.first] = chCnt.getOrDefault(k.first, 0L) + v
    }
    chCnt[s0.last()] = chCnt.getOrDefault(s0.last(), 0L) + 1L
//    println("chCnt = ${chCnt}")
    val diff = chCnt.values.maxOrNull() !! -  chCnt.values.minOrNull() !!
    println("diff = ${diff}")
}

fun main() {
    val input = readInput("day14")
    part1(input)
    part2(input)
}
