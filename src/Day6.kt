
private fun gen(cnt: MutableList<Long>, days: Int) {
    var cnt2 = cnt
    for (i in 1..days) {
        val cnt1 = MutableList(9) { 0L }
        for (j in 0 until 8) {
            cnt1[j] += cnt2[j + 1]
        }
        cnt1[6] += cnt2[0]
        cnt1[8] += cnt2[0]
        cnt2 = cnt1
    }
    println(cnt2.sum())
}

private fun part1(input: List<String>) {
    val arr = input[0].split(",").map { it.toInt() }
    var cnt = MutableList(9){0L}
    arr.forEach{ cnt[it]++ }
    gen(cnt, 80)
}


private fun part2(input: List<String>) {
    val arr = input[0].split(",").map { it.toInt() }
    var cnt = MutableList(9){0L}
    arr.forEach{ cnt[it]++ }
    gen(cnt, 256)
}

fun main() {
    val input = readInput("day6")
    part1(input)
    part2(input)
}