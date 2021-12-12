
private fun part1(input: List<String>) {
    val adj = HashMap<String, MutableList<String>>()
    for (line in input) {
        val (u,v) = line.split("-", limit = 2)
        adj.getOrPut(u) { mutableListOf() }.add(v)
        adj.getOrPut(v) { mutableListOf() }.add(u)
    }
    var ttl = 0
    val vis = mutableSetOf<String>()
    fun dfs(u: String) {
        if (u == "end") {
            ttl++
            return
        }
        if (u[0].isLowerCase()) {
            if (vis.contains(u)) {
                return
            }
            vis.add(u)
        }
        for (v in adj[u]!!) {
            dfs(v)
        }
        if (u[0].isLowerCase()) {
            vis.remove(u)
        }
    }
    dfs("start")
    println("ttl = ${ttl}")
}

private fun part2(input: List<String>) {
    val adj = HashMap<String, MutableList<String>>()
    for (line in input) {
        val (u,v) = line.split("-", limit = 2)
        if (v != "start")
            adj.getOrPut(u) { mutableListOf() }.add(v)
        if (u != "start")
            adj.getOrPut(v) { mutableListOf() }.add(u)
    }
    var ttl = 0
    val vis = mutableSetOf<String>()
    var smallVis2: String? = null
//    val path = mutableListOf<String>()
    fun dfs(u: String) {
        if (u == "end") {
            ttl++
//            println(path.joinToString(","))
            return
        }
        if (u[0].isLowerCase()) {
            if (vis.contains(u)) {
                if (smallVis2 == null) {
                    smallVis2 = u
                } else {
                    return
                }
            } else {
                vis.add(u)
            }
        }
//        path.add(u)
        for (v in adj[u]!!) {
            dfs(v)
        }
//        path.removeLast()
        if (u[0].isLowerCase()) {
            if (vis.contains(u)) {
                if (smallVis2 == u) {
                    smallVis2 = null
                } else {
                    vis.remove(u)
                }
            }
        }
    }
    dfs("start")
    println("ttl = ${ttl}")
}

fun main() {
    val input = readInput("day12")
    part1(input)
    part2(input)
}