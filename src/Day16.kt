import kotlin.test.fail

private fun List<Int>.subVal(startIdx: Int, endIdx: Int): Int {
    var acc = 0
    for (i in startIdx until endIdx) {
        acc = acc * 2 + this[i]
    }
    return acc
}

data class PacketInfo(
        val version: Int,
        val nextIdx: Int,
        val value: Long,
)

private fun calcSubPackets(typeId: Int, values: List<Long>): Long {
    return when (typeId) {
        0 -> values.sum()
        1 -> values.reduce{a, b -> a * b}
        2 -> values.minOrNull()!!
        3 -> values.maxOrNull()!!
        5 -> if (values[0] > values[1]) 1 else 0
        6 -> if (values[0] < values[1]) 1 else 0
        7 -> if (values[0] == values[1]) 1 else 0
        else -> fail("Unknown type id")
    }
}

private fun parsePacket(s: List<Int>, startIdx: Int): PacketInfo {
    val version = s.subVal(startIdx, startIdx+3)
    var versionSum = version
    val typeId = s.subVal(startIdx+3, startIdx+6)
    val subPackets = mutableListOf<PacketInfo>()
    if (typeId == 4) {
        var acc = 0L
        var litIdx = startIdx + 6
        while (true) {
            val lit = s.subVal(litIdx+1, litIdx+5)
            acc = acc * 16 + lit
            litIdx += 5
            if (s[litIdx - 5] == 0) {
                break
            }
        }
        return PacketInfo(versionSum, litIdx, acc)
    } else { // typeId != 4
        val lengthTypeId = s.subVal(startIdx + 6, startIdx+7)
        if (lengthTypeId == 0) {
            val subPacketsLen = s.subVal(startIdx + 7, startIdx + 22)
            var subPacketsIdx = startIdx + 22
            val subPacketEndIdx = subPacketsIdx + subPacketsLen
            while (true) {
                val subPacketInfo = parsePacket(s, subPacketsIdx)
                subPackets.add(subPacketInfo)
                versionSum += subPacketInfo.version
                subPacketsIdx = subPacketInfo.nextIdx
                if (subPacketsIdx + 6 >= subPacketEndIdx) break
            }
            val v = calcSubPackets(typeId, subPackets.map{ it.value} )
            return PacketInfo(versionSum, subPacketEndIdx, v)
        } else { // lengthTypeId == 1
            val subPacketNum = s.subVal(startIdx + 7, startIdx + 18)
            var subPacketsIdx = startIdx + 18
            for (si in 0 until subPacketNum) {
                val subPacketInfo = parsePacket(s, subPacketsIdx)
                subPackets.add(subPacketInfo)
                versionSum += subPacketInfo.version
                subPacketsIdx = subPacketInfo.nextIdx
            }
            val v = calcSubPackets(typeId, subPackets.map{ it.value} )
            return PacketInfo(versionSum, subPacketsIdx, v)
        }
    }
}

private fun hexCh2Arr(ch: Char): List<Int> {
    var v = ch.toString().toInt(16)
    val ans = MutableList(4){0}
    for (i in ans.indices.reversed()) {
        ans[i] = v % 2
        v /= 2
    }
    return ans
}

private fun part1(input: List<String>) {
    for (line in input) {
        val b1 = line.flatMap { hexCh2Arr(it) }
        val pi = parsePacket(b1, 0)
        println("version = ${pi.version}")
    }
}

private fun part2(input: List<String>) {
    for (line in input) {
        val b1 = line.flatMap { hexCh2Arr(it) }
        val pi = parsePacket(b1, 0)
        println("value = ${pi.value}")
    }
}

fun main() {
    val input = readInput("day16")
    part1(input)
    part2(input)
}
