package org.example

import java.util.*

class DayEighteen(filepath: String, testMode: Boolean) {
    val size = if (testMode) 7 else 71
    val simulationLength = if (testMode) 12 else 1024

    val bytes = Importer.extractText(filepath).split("\n").map { it.split(",").map { it.toInt() } }
    val memorySpace = MemorySpace(size)

    class MemorySpace(size: Int) {
        public val map = Array(size) { y -> Array(size) { x -> Location(this, x, y) } }

        public fun getLocation(coords: List<Int>): Location? {
            val (x, y) = coords
            return if (x in map.indices && y in map[0].indices) map[y][x] else null
        }

        public fun corrupt(coords: List<Int>) {
            val location = getLocation(coords)
            location?.isCorrupted = true
        }

        public fun print() {
            for (row in map) {
                for (cell in row) if (cell.isCorrupted) print("#") else print(".")
                print("\n")
            }
        }
    }

    class Location(private val memorySpace: MemorySpace, public val x: Int, public val y: Int) {
        public var isCorrupted = false
        public var steps = Int.MAX_VALUE

        public fun intactNeighbors(): List<Location> {
            val left = memorySpace.getLocation(listOf(x - 1, y))
            val right = memorySpace.getLocation(listOf(x + 1, y))
            val up = memorySpace.getLocation(listOf(x, y - 1))
            val down = memorySpace.getLocation(listOf(x, y + 1))
            return listOf(left, right, up, down).filterNotNull().filter { !it.isCorrupted }
        }
    }

    public fun first(): Int {
        dropBytes()
        return findShortestPath()
    }

    private fun dropBytes() {
        for (i in 0 until simulationLength) {
            val coords = bytes[i]
            memorySpace.corrupt(coords)
        }
    }

    private fun findShortestPath(): Int {
        val start = memorySpace.getLocation(listOf(0, 0))!!
        val exit = memorySpace.getLocation(listOf(size - 1, size - 1))!!
        start.steps = 0
        val visited: MutableSet<Location> = mutableSetOf(start)
        val queue: Queue<Location> = LinkedList()
        queue.offer(start)
        while (queue.isNotEmpty()) {
            val location = queue.poll()
            if (location == exit) continue // don't add neighbors

            for (neighbor in location.intactNeighbors()) {
                if (neighbor in visited && neighbor.steps <= location.steps + 1) continue

                neighbor.steps = location.steps + 1
                visited.add(neighbor)
                queue.offer(neighbor)
            }
        }
        return exit.steps
    }
}