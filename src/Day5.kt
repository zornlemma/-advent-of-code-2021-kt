import kotlin.math.sign

private fun points(x0: Int, y0:Int, x1:Int, y1:Int) = sequence {
    if (x0 != x1 && y0 != y1) return@sequence
    var x = x0
    var y = y0
    val dx = (x1-x0).sign
    val dy = (y1-y0).sign
    while (x != x1 || y != y1) {
        yield(Pair(x,y))
        x += dx
        y += dy
    }
    yield(Pair(x1, y1))
}

private fun points2(x0: Int, y0:Int, x1:Int, y1:Int) = sequence {
    var x = x0
    var y = y0
    val dx = (x1-x0).sign
    val dy = (y1-y0).sign
    while (x != x1 || y != y1) {
        yield(Pair(x,y))
        x += dx
        y += dy
    }
    yield(Pair(x1, y1))
}


private fun parseLine(line: String): List<Int> {
    val (front, back) = line.split("->", limit = 2)
    val ans = mutableListOf<Int>()
    front.split(",").mapTo(ans) { it.trim().toInt() }
    back.split(",").mapTo(ans) { it.trim().toInt() }
    return ans
}

const val BOARD_SIZE = 1000

private fun part1(input: List<String>) {
    val grid = Array(BOARD_SIZE){ IntArray(BOARD_SIZE) }
    val s = mutableSetOf<Pair<Int, Int>>()
    for (line in input) {
        val (x0, y0, x1, y1) = parseLine(line)
        for ((x,y) in points(x0,y0,x1,y1)) {
            grid[x][y] += 1
            if (grid[x][y] > 1) {
                s.add(Pair(x,y))
            }
        }
//        grid.forEach {
//            println(it.toList())
//        }
//        println("=".repeat(BOARD_SIZE * 2))
    }
    println(s.size)
}

private fun part2(input: List<String>) {
    val grid = Array(BOARD_SIZE){ IntArray(BOARD_SIZE) }
    val s = mutableSetOf<Pair<Int, Int>>()
    for (line in input) {
        val (x0, y0, x1, y1) = parseLine(line)
        for ((x,y) in points2(x0,y0,x1,y1)) {
            grid[x][y] += 1
            if (grid[x][y] > 1) {
                s.add(Pair(x,y))
            }
        }
    }
    println(s.size)
}

fun main() {
    val input = readInput("day5")
    part1(input)
    part2(input)
}