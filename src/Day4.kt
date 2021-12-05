private fun part1(input: List<String>) {
    val seq = input[0].split(",").map{ it.toInt() }
    val bingoList = mutableListOf<List<MutableList<Int>>>()
    for (i in 2 until input.size step 6) {
        val a = (0 until 5).map { j -> input[i+j] }.map { line ->
            line.split(" ").filter{ it.isNotBlank() }.map { it.toInt() }.toMutableList()
        }
        bingoList.add(a)
    }

    var targetBingoIdx = -1
    var targetSeqIdx = -1
    seq@
    for ((seqIdx, target) in seq.withIndex()) {
        bingo@
        for ((bingoIdx, bingo) in bingoList.withIndex()) {
            for (i in 0 until 5)
                for (j in 0 until 5) {
                    if (bingo[i][j] == target) {
                        bingo[i][j] = -1
                        if ((0 until 5).all{ bingo[i][it] == -1 } || (0 until 5).all { bingo[it][j] == -1 } ) {
                            targetBingoIdx = bingoIdx
                            targetSeqIdx = seqIdx
                            break@seq
                        }
                        continue@bingo
                    }
                }
        }
    }
    val s = bingoList[targetBingoIdx].map { line -> line.filter{ it != -1 }.sum() }.sum()
    println(s * seq[targetSeqIdx])
}

private fun part2(input: List<String>) {
    val seq = input[0].split(",").map{ it.toInt() }
    val bingoList = mutableListOf<List<MutableList<Int>>>()
    for (i in 2 until input.size step 6) {
        val a = (0 until 5).map { j -> input[i+j] }.map { line ->
            line.split(" ").filter{ it.isNotBlank() }.map { it.toInt() }.toMutableList()
        }
        bingoList.add(a)
    }

    var targetBingoIdx = -1
    var targetSeqIdx = -1
    val winningBingoIdxSet = mutableSetOf<Int>()
    for ((seqIdx, target) in seq.withIndex()) {
        bingo@
        for ((bingoIdx, bingo) in bingoList.withIndex()) {
            if (! winningBingoIdxSet.contains(bingoIdx)) {
                for (i in 0 until 5)
                    for (j in 0 until 5) {
                        if (bingo[i][j] == target) {
                            bingo[i][j] = -1
                            if ((0 until 5).all{ bingo[i][it] == -1 } || (0 until 5).all { bingo[it][j] == -1 } ) {
                                targetBingoIdx = bingoIdx
                                targetSeqIdx = seqIdx
                                winningBingoIdxSet.add(bingoIdx)
                            }
                            continue@bingo
                        }
                    }
            }
        }
    }
    val s = bingoList[targetBingoIdx].map { line -> line.filter{ it != -1 }.sum() }.sum()
    println(s * seq[targetSeqIdx])
}

fun main() {
    val input = readInput("day4")
    part1(input)
    part2(input)
}