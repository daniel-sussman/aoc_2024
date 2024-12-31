package org.example

import java.util.*

class DayTen(filepath: String) {
    private val input = Importer.extractMatrix(filepath)
    private val rows = input.size
    private val cols = input[0].size
    private val trailheads = mutableSetOf<Node>()
    private val nodeMap = buildNodeMap()

    class Node(public val x: Int, public val y: Int, public val value: Int, public val startingNode: Boolean) {
        public val children = mutableSetOf<Node>()

        public fun addChildren(nodeMap: Array<Array<Node>>) {
            if (areValidCoords(x - 1, y, nodeMap)) addChild(nodeMap[y][x - 1])
            if (areValidCoords(x + 1, y, nodeMap)) addChild(nodeMap[y][x + 1])
            if (areValidCoords(x, y - 1, nodeMap)) addChild(nodeMap[y - 1][x])
            if (areValidCoords(x, y + 1, nodeMap)) addChild(nodeMap[y + 1][x])
        }

        private fun addChild(child: Node) {
            if (child.value == value + 1) children.add(child)
        }

        private fun areValidCoords(x: Int, y: Int, matrix: Array<Array<Node>>): Boolean {
            return x in 0 until matrix[0].size && y in 0 until matrix.size
        }
    }

    public fun first(): Int {
        var result = 0
        addChildrenToNodes()
        trailheads.forEach { trailhead -> result += rateTrailhead(trailhead) }
        return result
    }

    public fun second(): Int {
        var result = 0
        addChildrenToNodes()
        trailheads.forEach { trailhead -> result += advancedRating(trailhead) }
        return result
    }

    private fun buildNodeMap(): Array<Array<Node>> {
        return Array(rows) { y ->
            Array(cols) { x ->
                val value = input[y][x].digitToInt()
                val isTrailhead = value == 0
                val node = Node(x, y, value, isTrailhead)
                if (isTrailhead) trailheads.add(node)
                node
            }
        }
    }

    private fun addChildrenToNodes() {
        nodeMap.flatten().forEach { node -> node.addChildren(nodeMap) }
    }

    private fun rateTrailhead(trailhead: Node): Int {
        val queue: Queue<Node> = LinkedList()
        val visited = mutableSetOf<Node>()
        var score = 0

        queue.addAll(trailhead.children)
        trailhead.children.forEach { node -> visited.add(node) }

        while (queue.isNotEmpty()) {
            val node = queue.poll()
            node.children.forEach { child -> if (child !in visited) {
                visited.add(child)
                queue.offer(child)
            } }
            if (node.value == 9) score++
        }

        return score
    }

    private fun advancedRating(trailhead: Node): Int {
        val queue: Queue<Node> = LinkedList()
        var score = 0

        queue.addAll(trailhead.children)

        while (queue.isNotEmpty()) {
            val node = queue.poll()
            node.children.forEach { child -> queue.offer(child) }
            if (node.value == 9) score++
        }

        return score
    }
}