import kotlin.collections.ArrayDeque

private fun part1(input: List<String>) {
    val grid: List<List<Int>> = input.map { line -> line.map { it - '0' } }
    var ttl = 0
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            val isLow = grid.nbr(i, j).all { (x1, y1) ->
                grid[x1][y1] > grid[i][j]
            }
            if (isLow) {
                ttl += grid[i][j] + 1
            }
        }
    }
    println("ttl = ${ttl}")
}

private val diff = listOf(listOf(0, 1), listOf(1, 0), listOf(0, -1), listOf(-1, 0))

private fun List<List<Int>>.nbr(x: Int, y: Int): List<List<Int>> {
    return diff.map { listOf(it[0] + x, it[1] + y) }
            .filter { it[0] >= 0 && it[0] < this.size && it[1] >= 0 && it[1] < this[0].size }
}

private fun part2(input: List<String>) {
    val grid: List<List<Int>> = input.map { line -> line.map { it - '0' } }
    val vis = Array(grid.size){ BooleanArray(grid[0].size) }
    val basinSizes = mutableListOf<Int>()
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (! vis[i][j] && grid[i][j] != 9) {
                val dq = ArrayDeque<Pair<Int,Int>>()
                dq.add(Pair(i,j))
                vis[i][j] = true
                var cnt = 1
                while (dq.isNotEmpty()) {
                    val (x,y) = dq.removeFirst()
                    for ((x1,y1) in grid.nbr(x,y)) {
                        if (! vis[x1][y1] && grid[x1][y1] < 9) {
                            dq.add(Pair(x1,y1))
                            vis[x1][y1] = true
                            cnt++
                        }
                    }
                }
                basinSizes.add(cnt)
            }
        }
    }
    basinSizes.sortDescending()
    val ans = basinSizes.subList(0, 3).reduce{ a, b -> a*b }
    println("ans = ${ans}")
}

fun main() {
    val input = readInput("day9")
    part1(input)
    part2(input)
}