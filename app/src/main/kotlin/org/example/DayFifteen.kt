package org.example

import java.util.*

class DayFifteen(filepath: String) {
    private val robot: Robot
    private val warehouse: Array<CharArray>

    init {
        val (w, m) = Importer.extractText(filepath).split("\n\n")
        val warehouse = w.split("\n").map { it.toCharArray() }.toTypedArray()
        val movements: Queue<Char> = LinkedList()
        for (chr in m) {
            if (chr != '\n') movements.offer(chr)
        }
        this.robot = Robot(warehouse, movements)
        this.warehouse = warehouse
    }

    class Robot(public val warehouse: Array<CharArray>, public val movements: Queue<Char>) {
        private val rows = warehouse.size
        private val cols = warehouse[0].size
        public var x: Int
        public var y: Int

        init {
            val (x, y) = getCoordinates()
            this.x = x
            this.y = y
        }

        private fun getCoordinates(): Pair<Int, Int> {
            for (y in 0 until rows) {
                for (x in 0 until cols) {
                    if (warehouse[y][x] == '@') return Pair(x, y)
                }
            }
            return Pair(-1, -1) // Not located
        }

        private fun getColumn(col: Int): CharArray {
            val result = CharArray(rows)
            for (row in 0 until rows) {
                result[row] = warehouse[row][col]
            }
            return result
        }

        public fun moveAll() {
            while (movements.isNotEmpty()) move()
        }

        public fun move() {
            val movement = movements.poll()
            val path = truncatePath(longPath(movement))
            val vector: Pair<Int, Int> = vectorOf(movement)

            if (path.isEmpty()) return
            if (path.first() == '.') {
                advanceAlong(vector)
                return
            }

            for (i in path.indices) {
                if (path[i] == 'O') continue

                val distance = i + 1
                warehouse[y + vector.second * distance][x + vector.first * distance] = 'O'
                advanceAlong(vector)
                return
            }
        }

        private fun advanceAlong(vector: Pair<Int, Int>) {
            warehouse[y][x] = '.'
            x += vector.first
            y += vector.second
            warehouse[y][x] = '@'
        }

        private fun vectorOf(movement: Char): Pair<Int, Int> {
            return when (movement) {
                '>' -> Pair(1, 0)
                '<' -> Pair(-1, 0)
                '^' -> Pair(0, -1)
                else -> Pair(0, 1)
            }
        }

        private fun longPath(movement: Char): CharArray {
            return when (movement) {
                '>' -> warehouse[y].sliceArray(x + 1 until cols)
                '<' -> warehouse[y].sliceArray(0 until x).reversedArray()
                '^' -> getColumn(x).sliceArray(0 until y).reversedArray()
                else -> getColumn(x).sliceArray(y + 1 until rows)
            }
        }

        private fun truncatePath(path: CharArray): CharArray {
            for (i in 0 until path.size) {
                if (path[i] == '#') return path.sliceArray(0 until i)
            }
            return path
        }
    }

    public fun first(): Int {
        robot.moveAll()
        printWarehouse()
        return countBoxes()
    }

    private fun printWarehouse() {
        for (row in warehouse) println(row.toList())
    }

    private fun countBoxes(): Int {
        var result = 0
        for (y in 1 until warehouse.size - 1) {
            for (x in 1 until warehouse[0].size - 1) {
                if (warehouse[y][x] == 'O') result += x + 100 * y
            }
        }
        return result
    }
}