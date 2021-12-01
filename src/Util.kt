import java.io.File

fun readln() = readLine()!!.trim()
fun readlnInt() = readln().toInt()
fun readlnStrings() = readln().split(' ')
fun readlnInts() = readlnStrings().map { it.toInt() }
fun readlnLongs() = readlnStrings().map { it.toLong() }

fun readInput(name: String): List<String> = File("src", "$name.txt").readLines()