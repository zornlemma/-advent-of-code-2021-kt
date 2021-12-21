
private val diff = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0)) +
        listOf(Pair(1, 1), Pair(1, -1), Pair(-1, -1), Pair(-1, 1))

private fun List<List<Int>>.nbr(x: Int, y: Int): List<Pair<Int,Int>> {
    return diff.map { Pair(it.first + x, it.second + y) }
            .filter { it.first >= 0 && it.first < this.size && it.second >= 0 && it.second < this.size }
}

private fun List<List<Int>>.pprint() {
    for (line in this) {
        for (e in line) {
            print(e)
        }
        println()
    }
    println("======")
}

private fun part1(input: List<String>) {
    val grid = input.map{ line -> line.map { it - '0' }.toMutableList() }
    var flashTtl = 0
    for (step in 1 .. 100) {
        for (i in 0 until 10)
            for (j in 0 until 10) {
                grid[i][j] += 1
            }

        var flashCnt = 0
        for (i in 0 until 10) {
            for (j in 0 until 10) {
                if (grid[i][j]>=10) {
                    val dq = ArrayDeque<Pair<Int,Int>>()
                    dq.add(Pair(i,j))
                    grid[i][j] = 0
                    flashCnt++
                    while (dq.isNotEmpty()) {
                        val (x,y) = dq.removeFirst()
                        for ((x1,y1) in grid.nbr(x,y)) {
                            if (grid[x1][y1] != 0) {
                                grid[x1][y1]++
                                if (grid[x1][y1] >= 10) {
                                    grid[x1][y1] = 0
                                    flashCnt++
                                    dq.add(Pair(x1,y1))
                                }
                            }
                        }
                    }

                }
            }
        }
//        println("step: $step")
//        grid.pprint()
        flashTtl += flashCnt
    }
    println("flashTtl = ${flashTtl}")
}

private fun part2(input: List<String>) {
    val grid = input.map{ line -> line.map { it - '0' }.toMutableList() }
    var step = 0
    var flashCnt = 0
    while (flashCnt != 10 * 10) {
        step++
        flashCnt = 0
        for (i in 0 until 10)
            for (j in 0 until 10) {
                grid[i][j] += 1
            }

        for (i in 0 until 10) {
            for (j in 0 until 10) {
                if (grid[i][j]>=10) {
                    val dq = ArrayDeque<Pair<Int,Int>>()
                    dq.add(Pair(i,j))
                    grid[i][j] = 0
                    flashCnt++
                    while (dq.isNotEmpty()) {
                        val (x,y) = dq.removeFirst()
                        for ((x1,y1) in grid.nbr(x,y)) {
                            if (grid[x1][y1] != 0) {
                                grid[x1][y1]++
                                if (grid[x1][y1] >= 10) {
                                    grid[x1][y1] = 0
                                    flashCnt++
                                    dq.add(Pair(x1,y1))
                                }
                            }
                        }
                    }

                }
            }
        }
//        println("step: $step")
//        grid.pprint()
    }
    println("step = ${step}")
}

fun main() {
    val input = readInput("day11")
    part1(input)
    part2(input)
}