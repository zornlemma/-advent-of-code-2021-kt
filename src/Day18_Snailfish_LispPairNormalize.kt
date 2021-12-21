private class PNode(
        var v: Int?,
        var left: PNode? = null,
        var right: PNode? = null,
        var parent: PNode? = null,
) {
    override fun toString(): String {
        return if (left == null) {
            v.toString()
        } else {
            "[$left,$right]"
        }
    }

    fun firstValNode(): PNode {
        var p = this
        while (p.v == null) {
            p = p.left!!
        }
        return p
    }

    fun lastValNode(): PNode {
        var p = this
        while (p.v == null) {
            p = p.right!!
        }
        return p
    }

    fun nextValNode(): PNode? {
        var p = this
        while (p.isRightChild()) { // ↖
            p = p.parent!!
        }
        if (p.parent == null) {
            return null
        }
        p = p.parent!!.right!! // →
        p = p.firstValNode() // ↙
        return p
    }

    fun prevValNode(): PNode? {
        var p = this
        while (p.isLeftChild()) {
            p = p.parent!!
        }
        if (p.parent == null) {
            return null
        }
        p = p.parent!!.left!!
        p = p.lastValNode()
        return p
    }

    fun split() {
        this.left = PNode(this.v!! / 2, parent=this)
        this.right = PNode((this.v!! + 1) /2, parent=this)
        this.v = null
    }

    fun explode() {
        val prev = this.prevValNode()
        if (prev != null)
            prev.v = prev.v!! + this.left!!.v!!
        this.left = null
        val nxt = this.nextValNode()
        if (nxt != null)
            nxt.v = nxt.v!! + this.right!!.v!!
        this.right = null
        this.v = 0
    }

    fun isRightChild(): Boolean {
        return this.parent?.right == this
    }
    fun isLeftChild(): Boolean {
        return this.parent?.left == this
    }

    fun magnitude(): Int {
        if (this.v != null)
            return this.v!!
        else {
            return this.left!!.magnitude() * 3 + this.right!!.magnitude() * 2
        }
    }

    fun normalize() {
        var changeFlag = false
        fun inorderExplode(root: PNode, h: Int) {
            if (changeFlag) {
                return
            }
            if (root.v == null) {
                if (h >= 5) {
                    root.explode()
                    changeFlag = true
                } else {
                    inorderExplode(root.left!!, h+1)
                    inorderExplode(root.right!!, h+1)
                }
            }
        }
        fun inorderSplit(root: PNode) {
            if (changeFlag) {
                return
            }
            if (root.v != null) {
                if (root.v!! >= 10) {
                    root.split()
                    changeFlag = true
                }
            } else {
                inorderSplit(root.left!!)
                inorderSplit(root.right!!)
            }
        }
        do {
            changeFlag = false
            inorderExplode(this, 1)
            if (!changeFlag) inorderSplit(this)
        }
        while (changeFlag)
    }

    operator fun plus(o: PNode): PNode {
        val s = PNode(null, this, o)
        this.parent = s
        o.parent = s
        return s
    }
}

private fun parseLine(s: String): PNode {
    val head0 = PNode(null)
    val st = mutableListOf<PNode>(head0)
    for (ch in s) {
        when (ch) {
            '[' -> st.add(PNode(null))
            in '0'..'9' -> {
                val p = PNode(ch - '0')
                linkToLast(st, p)
            }
            ']' -> {
                val p = st.removeLast()
                linkToLast(st, p)
            }
        }
    }
    st[0].left!!.parent = null
    return st[0].left!!
}

private fun linkToLast(st: MutableList<PNode>, p: PNode) {
    p.parent = st.last()
    if (st.last().left == null) st.last().left = p
    else st.last().right = p
}

private fun test_explode() {
    val p1 = parseLine("[[[[[9,8],1],2],3],4]")
    p1.normalize()
    println("p1 = $p1")
    val p2 = parseLine("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
    p2.normalize()
    println("p2 = ${p2}")
}

private fun test_split() {
    val p1 = parseLine("[[[[4,3],4],4],[7,[[8,4],9]]]")
    val p2 = parseLine("[1,1]")
    val p3 = p1 + p2
    p3.normalize()
    println("$p3")
}

private fun part1(input: List<String>) {
//    test_explode()
    var p = parseLine(input[0])
    for (i in 1 until input.size) {
        val pi = parseLine(input[i])
//        println("p$i = ${pi}")
        p += pi
        p.normalize()
//        println("p = ${p}")
    }
    println("p.magnitude() = ${p.magnitude()}")
}

private fun part2(input: List<String>) {
    var maxMag = 0
    for (i in input.indices)
        for (j in input.indices) {
            if (j != i) {
                val p1 = parseLine(input[i])
                val p2 = parseLine(input[j])
                val p3 = p1 + p2
                // a persisted solution could be better
                p3.normalize()
                maxMag = kotlin.math.max(maxMag, p3.magnitude())
            }
        }
    println("maxMag = ${maxMag}")
}

fun main() {
    val input = readInput("day18")
    part1(input)
    part2(input)
}
