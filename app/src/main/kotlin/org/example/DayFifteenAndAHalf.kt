package org.example

import java.util.*

class DayFifteenAndAHalf(filepath: String) {
    private val robot: Robot
    private val warehouse: Array<CharArray>

    init {
        val (w, m) = Importer.extractText(filepath).split("\n\n")
        val warehouse = getWarehouse(w)
        val movements: Queue<Char> = LinkedList()
        for (chr in m) {
            if (chr != '\n') movements.offer(chr)
        }
        this.robot = Robot(warehouse, movements)
        this.warehouse = warehouse
    }

    private fun getWarehouse(w: String): Array<CharArray> {
        return w.split("\n").map {
            it
                .replace("#", "##")
                .replace("O", "[]")
                .replace(".", "..")
                .replace("@", "@.")
                .toCharArray()
        }.toTypedArray()
    }

    class Robot(private val warehouse: Array<CharArray>, private val movements: Queue<Char>) {
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

            val boxesToMove: MutableList<Box> = mutableListOf()
            if (vector.first != 0) {
                for (i in path.indices) {
                    val distance = i + 1

                    if (path[i] == ']') continue
                    if (path[i] == '[') {
                        boxesToMove.add(Box(warehouse,x + vector.first * distance, x + vector.first * distance + 1, y))
                        continue
                    }

                    for (box in boxesToMove.sortedBy { it.x1 * -vector.first }) box.move(vector)
                    advanceAlong(vector)
                    return
                }
            } else {
                val box =
                    if (path.first() == '[') Box(warehouse, x, x + 1, y + vector.second)
                    else Box(warehouse, x - 1, x, y + vector.second)
                if (box.ableToMove(vector)) {
                    val affectedBoxes = box.affectedBoxes(vector).sortedBy { it.y * -vector.second }
                    affectedBoxes.forEach { it.move(vector) }
                    box.move(vector)
                    advanceAlong(vector)
                }
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

    class Box(private val warehouse: Array<CharArray>, public val x1: Int, public val x2: Int, public val y: Int) {
        public fun move(vector: Pair<Int, Int>) {
            warehouse[y][x1] = '.'
            warehouse[y][x2] = '.'
            warehouse[y + vector.second][x1 + vector.first] = '['
            warehouse[y + vector.second][x2 + vector.first] = ']'
        }

        public fun ableToMove(vector: Pair<Int, Int>): Boolean {
            val leftTarget = warehouse[y + vector.second][x1 + vector.first]
            val rightTarget = warehouse[y + vector.second][x2 + vector.first]

            if (leftTarget == '#' || rightTarget == '#') return false
            if (leftTarget == '.' && rightTarget == '.') return true

            return affectedBoxes(vector).all { it.ableToMove(vector) }
        }

        public fun affectedBoxes(vector: Pair<Int, Int>): List<Box> {
            // only use this if movement axis is vertical
            val affectedBoxes: MutableList<Box> = mutableListOf()

            val leftTarget = warehouse[y + vector.second][x1 + vector.first]
            val rightTarget = warehouse[y + vector.second][x2 + vector.first]

            if (leftTarget == '.' && rightTarget == '.') return affectedBoxes
            if (leftTarget == '[' && rightTarget == ']') {
                val box = Box(warehouse, x1 + vector.first, x2 + vector.first, y + vector.second)
                affectedBoxes.add(box)
                affectedBoxes.addAll(box.affectedBoxes(vector))
            }
            if (leftTarget == ']') {
                val box = Box(warehouse, x1 + vector.first - 1, x2 + vector.first - 1, y + vector.second)
                affectedBoxes.add(box)
                affectedBoxes.addAll(box.affectedBoxes(vector))
            }
            if (rightTarget == '[') {
                val box = Box(warehouse, x1 + vector.first + 1, x2 + vector.first + 1, y + vector.second)
                affectedBoxes.add(box)
                affectedBoxes.addAll(box.affectedBoxes(vector))
            }

            return affectedBoxes
        }
    }

    public fun second(): Int {
        robot.moveAll()
        return countBoxes()
    }

    private fun printWarehouse() {
        for (row in warehouse) {
            for (cell in row) {
                print(cell)
            }
            print('\n')
        }
    }

    private fun countBoxes(): Int {
        var result = 0
        for (y in 1 until warehouse.size - 1) {
            for (x in 1 until warehouse[0].size - 1) {
                if (warehouse[y][x] == '[') result += x + 100 * y
            }
        }
        return result
    }
}