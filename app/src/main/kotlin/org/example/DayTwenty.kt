package org.example

import java.util.*

class DayTwenty(filepath: String) {
    private val data = Importer.extractMatrix(filepath)
    private val racetrack = Racetrack(data)

    class Racetrack(data: List<List<Char>>) {
        public val rows = data.size
        public val cols = data[0].size
        public val map = Array(rows) { y -> Array(cols) { x -> Position(this, x, y, data[y][x]) } }

        public fun getPosition(x: Int, y: Int): Position? {
            return if (x in 1 until cols - 1 && y in 1 until rows - 1) map[y][x] else null
        }
    }

    class Position(private val racetrack: Racetrack, public val x: Int, public val y: Int, public val value: Char) {
        public val isWall: Boolean = value == '#'
        public var time = Int.MAX_VALUE

        public fun neighbors(): List<Position> {
            val left = racetrack.getPosition(x - 1, y)
            val right = racetrack.getPosition(x + 1, y)
            val up = racetrack.getPosition(x, y - 1)
            val down = racetrack.getPosition(x, y + 1)
            return listOf(left, right, up, down).filterNotNull().filter { !it.isWall }
        }

        public fun adjacent(): List<Position> {
            val left = racetrack.getPosition(x - 1, y)
            val right = racetrack.getPosition(x + 1, y)
            val up = racetrack.getPosition(x, y - 1)
            val down = racetrack.getPosition(x, y + 1)
            return listOf(left, right, up, down).filterNotNull()
        }

        public fun print(): String {
            return "Position($x, $y) value: $value"
        }
    }


    public fun first(minTimeSaved: Int): Int {
        val (start, end, shortcuts) = analyzeMap()
        completeCourseWithoutCheating(start, end)
        return countShortcuts(shortcuts, minTimeSaved)
    }

    public fun second(minTimeSaved: Int): Long {
        val (start, end) = brieflyAnalyzeMap()
        completeCourseWithoutCheating(start, end)
        val path = tracePath(end)
        return countCheats(path, minTimeSaved)
    }

    private fun tracePath(end: Position): Set<Position> {
        return racetrack.map.flatten().filter { !it.isWall && it.time < end.time }.toSet()
    }

    private fun countCheats(path: Set<Position>, minTimeSaved: Int): Long {
        var cheats = 0L
        val maxDistance = 20
        for (start in path) {
            val time: MutableMap<Position, Int> = mutableMapOf()
            time[start] = 0
            val queue: Queue<Position> = LinkedList()
            queue.offer(start)
            while (queue.isNotEmpty()) {
                val position = queue.poll()
                for (adjacent in position.adjacent()) {
                    if (adjacent in time.keys && time[adjacent]!! <= time[position]!! + 1) continue

                    time[adjacent] = time[position]!! + 1
                    if (time[adjacent]!! < maxDistance) queue.offer(adjacent)
                }
            }
//            time.keys.filter { !it.isWall && it.time - start.time - time[it]!! >= minTimeSaved }.forEach { println("shortcut between ${start.print()} and ${it.print()} takes ${time[it]!!} picoseconds and saves ${it.time - start.time - time[it]!!}") }
            cheats += time.keys.filter { !it.isWall && it.time - start.time - time[it]!! >= minTimeSaved }.size
        }

        return cheats
    }

    private fun countShortcuts(shortcuts: Set<Position>, minTime: Int): Int {
        var count = 0
        for (shortcut in shortcuts) {
            val neighbors = shortcut.neighbors()
            for (n in neighbors) {
                val others = neighbors.filter { it != n }
                others.forEach { o -> if (o.time - n.time - 2 >= minTime) count++ }
            }
        }
        return count
    }

    private fun brieflyAnalyzeMap(): Pair<Position, Position> {
        var start = racetrack.getPosition(1, 1)!!
        var end = racetrack.getPosition(1, 1)!!
        for (y in 1 until racetrack.rows - 1) {
            for (x in 1 until racetrack.cols - 1) {
                val position = racetrack.getPosition(x, y)!!
                if (position.value == 'S') {
                    start = position
                } else if (position.value == 'E') {
                    end = position
                }
            }
        }
        return Pair(start, end)
    }

    private fun analyzeMap(): Triple<Position, Position, Set<Position>> {
        var start = racetrack.getPosition(1, 1)!!
        var end = racetrack.getPosition(1, 1)!!
        val shortcuts: MutableSet<Position> = mutableSetOf()
        for (y in 1 until racetrack.rows - 1) {
            for (x in 1 until racetrack.cols - 1) {
                val position = racetrack.getPosition(x, y)!!
                if (position.value == 'S') {
                    start = position
                } else if (position.value == 'E') {
                    end = position
                } else if (position.isWall && position.neighbors().size >= 2) {
                    shortcuts.add(position)
                }
            }
        }
        return Triple(start, end, shortcuts.toSet())
    }

    private fun completeCourseWithoutCheating(start: Position, end: Position) {
        start.time = 0
        val visited: MutableSet<Position> = mutableSetOf(start)
        val queue: Queue<Position> = LinkedList()
        queue.offer(start)
        while (queue.isNotEmpty()) {
            val position = queue.poll()
            if (position == end) continue // don't add neighbors

            for (neighbor in position.neighbors()) {
                if (neighbor in visited && neighbor.time <= position.time + 1) continue

                neighbor.time = position.time + 1
                visited.add(neighbor)
                queue.offer(neighbor)
            }
        }
    }
}