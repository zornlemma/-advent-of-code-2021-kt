private fun part1(input: List<String>) {
    var x = 0
    var y = 0
    for (line in input) {
        val s = line.split(" ")
        val dis = s[1].toInt()
        if (s[0] == "forward") {
            x += dis
        } else if (s[0] == "down") {
            y += dis
        } else {
            y -= dis
        }
    }
    println(x * y)
}

private fun part2(input: List<String>) {
    var x = 0
    var y = 0
    var aim = 0
    for (line in input) {
        val s = line.split(" ")
        val dis = s[1].toInt()
        if (s[0] == "forward") {
            x += dis
            y += aim * dis
        } else if (s[0] == "down") {
            aim += dis
        } else {
            aim -= dis
        }
    }
    println(x * y)
}

fun main() {
    val input = readInput("day2")
    part1(input)
    part2(input)
}