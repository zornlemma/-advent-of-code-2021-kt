import java.util.*

private data class Pos(
        val d:Int,
        val x:Int,
        val y:Int,
)

private fun part1(input: List<String>) {
    val grid = input.map{ line -> line.map{ it - '0'}.toMutableList() }.toMutableList()
    val m = grid.size
    val n = grid[0].size
    val dis = MutableList(m){ MutableList(n) {0x7fff7fff} }
    val dq = PriorityQueue<Pos>(compareBy { it.d })
    dq.add(Pos(0,0,0))
    while (dq.isNotEmpty()) {
        val (d, x, y) = dq.poll()
        if (dis[x][y] <= d) {
            continue
        }
        dis[x][y] = d
        if (x == m-1 && y == n-1) {
            break
        }
        for ((x1, y1) in grid.nbr(x,y)) {
            dq.add(Pos(grid[x1][y1] + d, x1, y1))
        }
    }
    println("dis = ${dis[m-1][n-1]}")
    println("Done")
}

private val diff = listOf(Pair(-1,0), Pair(1,0), Pair(0, 1), Pair(0, -1))

private fun List<List<Int>>.nbr(x: Int, y: Int): List<Pair<Int, Int>> {
    return diff.map { Pair(it.first + x, it.second + y) }
            .filter { it.first >= 0 && it.first < this.size && it.second >= 0 && it.second < this.size }
}


private fun part2(input: List<String>) {
    val grid0 = input.map{ line -> line.map{ it - '0'}.toMutableList() }.toMutableList()
    val m = grid0.size
    val n = grid0[0].size
    val grid5 = MutableList(m*5){ MutableList(n*5){0} }
    for (stepI in 0 until 5)
        for (stepJ in 0 until 5)
            for (i in 0 until m)
                for (j in 0 until n) {
                    grid5[stepI * m + i][stepJ * n + j] = (grid0[i][j] + stepI + stepJ).let {
                        if (it <= 9) it else it - 9
                    }
                }
    val dis = MutableList(m*5){ MutableList(n*5) {0x7fff7fff} }
    val dq = PriorityQueue<Pos>(compareBy { it.d })
    dq.add(Pos(0,0,0))
    while (dq.isNotEmpty()) {
        val (d, x, y) = dq.poll()
        if (dis[x][y] <= d) {
            continue
        }
        dis[x][y] = d
        if (x == m*5-1 && y == n*5-1) {
            break
        }
        for ((x1, y1) in grid5.nbr(x,y)) {
            dq.add(Pos(grid5[x1][y1] + d, x1, y1))
        }
    }
    println("dis = ${dis[m*5-1][n*5-1]}")
    println("Done")
}

fun main() {
    val input = readInput("day15")
    part1(input)
    part2(input)
}
