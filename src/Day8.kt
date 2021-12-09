private fun part1(input: List<String>) {
    /*
    digit | seg
    0 | 6
    1 | 2
    2 | 5
    3 | 5
    4 | 4
    5 | 5
    6 | 6
    7 | 3
    8 | 7
    9 | 6
     */
    val possibleLens1478 = listOf(2,4,3,7)
    var cnt = 0
    for (line in input) {
        val (infoStr, outStr) = line.split("|", limit = 2)
        val outs = outStr.trim().split(" ")
        for (out in outs) {
            if (out.length in possibleLens1478) {
                cnt++
            }
        }
    }
    println(cnt)
}

/*
 aaa
b   c
b   c
 ddd
e   f
e   f
 ggg
 */

val originalSegs = listOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg").map { it.toList() }

private fun part2(input: List<String>) {
    var ttl = 0
    for (line in input) {
        val (infoStr, outStr) = line.split("|", limit = 2)
        val infos = infoStr.trim().split(" ").map{ it.toList() }
        val chMap = getChMap(infos)
//        println("chMap = ${chMap}")
        val newSegs = originalSegs.map{ seg -> seg.map { chMap[it]!! }.toSet() }
        val outs = outStr.trim().split(" ")
        var acc = 0
        for (out in outs) {
            val digit = newSegs.indexOf(out.toSet())
            acc = acc * 10 + digit
        }
        ttl += acc
    }
    println("ttl = ${ttl}")
}

private fun getChMap(infos: List<List<Char>>): Map<Char, Char> {
    val cf = infos.find { it.size == 2 }!! // 0
    val acf = infos.find { it.size == 3 }!! // 7
    val bdcf = infos.find { it.size == 4 }!! // 4
    val a = acf.subtract(cf)
    val bd = bdcf.subtract(cf)
    val x235 = infos.filter { it.size == 5 }
    val adg = x235[0].intersect(x235[1]).intersect(x235[2])
    val d = adg.intersect(bd)
    val b = bd.subtract(d)
    val g = adg.subtract(a).subtract(d)
    val x069 = infos.filter { it.size == 6 }
    val abfg = x069[0].intersect(x069[1]).intersect(x069[2])
    val f = abfg.subtract(a).subtract(b).subtract(g)
    val c = cf.subtract(f)
    val e = "abcdefg".toSet().subtract(bdcf).subtract(adg)
    return ('a'..'g').zip(listOf(a,b,c,d,e,f,g).map{it.first()}).associate { it }
}

fun main() {
    val input = readInput("day8")
    part1(input)
    part2(input)
}