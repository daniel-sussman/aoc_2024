package org.example

import java.util.*

class DaySixteen(filepath: String) {
    private val matrix = Importer.extractMatrix(filepath)
    private val rows = matrix.size
    private val cols = matrix[0].size

    class Node(private val matrix: List<List<Char>>, public val x: Int, public val y: Int, public val dir: Char, public val parent: Node? = null) {
        public val value = matrix[y][x]
        public var cost = Int.MAX_VALUE
        private val right = when (dir) {
            'n' -> 'e'
            'e' -> 's'
            's' -> 'w'
            else -> 'n'
        }
        private val left = when (dir) {
            'n' -> 'w'
            'e' -> 'n'
            's' -> 'e'
            else -> 's'
        }

        public fun updateCost(newCost: Int) {
            if (newCost < cost) cost = newCost
        }

        public fun children(): Set<Node> {
            val result: MutableSet<Node> = mutableSetOf()

            val noTurn = Pair(x + vector(dir).first, y + vector(dir).second)
            val rightTurn = Pair(x + vector(right).first, y + vector(right).second)
            val leftTurn = Pair(x + vector(left).first, y + vector(left).second)

            if (isValid(noTurn)) {
                val child = Node(matrix, noTurn.first, noTurn.second, dir, this)
                child.cost = cost + 1
                result.add(child)
            }
            if (isValid(rightTurn)) {
                val child = Node(matrix, rightTurn.first, rightTurn.second, right, this)
                child.cost = cost + 1001
                result.add(child)
            }
            if (isValid(leftTurn)) {
                val child = Node(matrix, leftTurn.first, leftTurn.second, left, this)
                child.cost = cost + 1001
                result.add(child)
            }
            return result
        }

        private fun vector(d: Char): Pair<Int, Int> {
            return when (d) {
                'n' -> Pair(0, -1)
                'e' -> Pair(1, 0)
                's' -> Pair(0, 1)
                else -> Pair(-1, 0)
            }
        }

        private fun isValid(coords: Pair<Int, Int>): Boolean {
            val xx = coords.first
            val yy = coords.second
            return (xx in matrix[0].indices && yy in matrix.indices && matrix[yy][xx] != '#')
        }
    }

    public fun first(): Int {
        val start = startingNode()
        val queue: Queue<Node> = LinkedList()
        val visited: MutableMap<Triple<Int, Int, Char>, Int> = mutableMapOf()
        val endTiles: MutableSet<Node> = mutableSetOf()

        visited[Triple(start.x, start.y, start.dir)] = 0
        queue.offer(start)

        while (queue.isNotEmpty()) {
            val currentNode = queue.poll()

            if (currentNode.value == 'E') {
                endTiles.add(currentNode)
                continue
            }

            val children = currentNode.children()
            for (child in children) {
                val key = Triple(child.x, child.y, child.dir)
                if (key in visited.keys && visited[key]!! < child.cost) {
                    continue
                } else {
                    queue.offer(child)
                    visited[key] = child.cost
                }
            }
        }

        return endTiles.minBy { it.cost }.cost
    }

    public fun second(): Int {
        val start = startingNode()
        val queue: Queue<Node> = LinkedList()
        val visited: MutableMap<Triple<Int, Int, Char>, Int> = mutableMapOf()
        val endTiles: MutableSet<Node> = mutableSetOf()

        visited[Triple(start.x, start.y, start.dir)] = 0
        queue.offer(start)

        while (queue.isNotEmpty()) {
            val currentNode = queue.poll()

            if (currentNode.value == 'E') {
                endTiles.add(currentNode)
                continue
            }

            val children = currentNode.children()
            for (child in children) {
                val key = Triple(child.x, child.y, child.dir)
                if (key in visited.keys && visited[key]!! < child.cost) {
                    continue
                } else {
                    queue.offer(child)
                    visited[key] = child.cost
                }
            }
        }

        val criterion = endTiles.minBy { it.cost }.cost
        val bestPaths = endTiles.filter { it.cost == criterion }
        return countBestSpotsToSit(bestPaths)
    }

    private fun countBestSpotsToSit(bestPaths: List<Node>): Int {
        val canvas = Array(rows) { Array(cols) { false } }
        for (endTile in bestPaths) {
            var currentTile: Node? = endTile
            while (currentTile != null) {
                canvas[currentTile.y][currentTile.x] = true
                currentTile = currentTile.parent
            }
        }
        return countTiles(canvas)
    }

    private fun countTiles(map: Array<Array<Boolean>>) = map.flatten().count { it }

    private fun startingNode(): Node {
        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                if (matrix[y][x] == 'S') {
                    val node = Node(matrix, x, y, 'e')
                    node.updateCost(0)
                    return node
                }
            }
        }
        throw Exception("No starting tile!")
    }
}