
private fun part1(input: List<String>) {
    var dots = mutableSetOf<Pair<Int,Int>>()
    var lineIdx = 0
    while (lineIdx < input.size) {
        val line = input[lineIdx]
        if (line.isEmpty()) {
            break
        }
        val (x,y) = line.split(",", limit = 2).map { it.toInt() }
        dots.add(Pair(x,y))
        lineIdx++
    }
    lineIdx++
    val folds = mutableListOf<Pair<Char, Int>>()
    while (lineIdx < input.size) {
        val line = input[lineIdx]
        val equalIdx = line.indexOf('=')
        val axis = line[equalIdx-1]
        val pos = line.substring(equalIdx+1).toInt()
        folds.add(Pair(axis, pos))
        lineIdx++
    }
    val (a, p) = folds[0]
    if (a=='x') {
        println("fold x: $p")
        dots = dots.foldPaperX(p)
    } else {
        println("fold y: $p")
        dots = dots.foldPaperY(p)
    }
    println("dots.size = ${dots.size}")
}

private fun Iterable<Pair<Int,Int>>.foldPaperX(pos: Int): MutableSet<Pair<Int, Int>> {
    return this.mapNotNull {
        when {
            it.first > pos -> Pair(pos * 2 - it.first, it.second)
            it.first == pos -> null
            else -> it
        }
    }.toMutableSet()
}

private fun Iterable<Pair<Int,Int>>.foldPaperY(pos: Int): MutableSet<Pair<Int, Int>> {
    return this.mapNotNull {
        when {
            it.second > pos -> Pair(it.first, pos * 2 - it.second)
            it.second == pos -> null
            else -> it
        }
    }.toMutableSet()
}

private fun part2(input: List<String>) {
    var dots = mutableSetOf<Pair<Int,Int>>()
    var lineIdx = 0
    while (lineIdx < input.size) {
        val line = input[lineIdx]
        if (line.isEmpty()) {
            break
        }
        val (x,y) = line.split(",", limit = 2).map { it.toInt() }
        dots.add(Pair(x,y))
        lineIdx++
    }
    lineIdx++
    val folds = mutableListOf<Pair<Char, Int>>()
    while (lineIdx < input.size) {
        val line = input[lineIdx]
        val equalIdx = line.indexOf('=')
        val axis = line[equalIdx-1]
        val pos = line.substring(equalIdx+1).toInt()
        folds.add(Pair(axis, pos))
        lineIdx++
    }
    for ((a,p) in folds) {
        if (a=='x') {
            println("fold x: $p")
            dots = dots.foldPaperX(p)
        } else {
            println("fold y: $p")
            dots = dots.foldPaperY(p)
        }
    }
    val dotsXLen = dots.map { it.first }.maxOrNull()!!
    val dotsYLen = dots.map { it.second }.maxOrNull()!!
    val grids = MutableList(dotsYLen+1) { MutableList(dotsXLen+1) {'.'} }
    dots.forEach{ grids[it.second][it.first] = '#' }
    grids.forEach{ line ->
        println(line.joinToString(""))
    }
}

fun main() {
    val input = readInput("day13")
    part1(input)
    part2(input)
}