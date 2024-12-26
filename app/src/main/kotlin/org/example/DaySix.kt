package org.example

class DaySix(filepath: String) {
    private val matrix = Importer.extractMatrix(filepath).map { it.toMutableList() }.toMutableList()
    private val rows = matrix.size
    private val cols = matrix[0].size
    private var path = Array(rows) { Array(cols) { mutableSetOf<Char>() } }
    private var bearing = '~'
    private var x: Int = -1
    private var y: Int = -1

    public fun first(): Int {
        initializeInstance()

        while (coordsValid()) {
            if (bearing in path[y][x]) return -1

            path[y][x].add(bearing)
            update()
        }

        return sumValues(path)
    }

    private fun initializeInstance() {
        val (x, y) = getInitialPosition(matrix)
        this.x = x
        this.y = y
        path = Array(rows) { Array(cols) { mutableSetOf<Char>() } }
        bearing = 'n'
    }

    public fun second(): Int {
        var result = 0
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val originalValue = matrix[i][j]

                if (originalValue == '#') continue

                matrix[i][j] = '#'
                if (first() == -1) result++
                matrix[i][j] = originalValue
            }
        }

        return result
    }

    private fun move(coords: Pair<Int, Int>) {
        val (x, y) = coords
        this.x = x
        this.y = y
    }

    private fun update() {
        val (nextX, nextY) = nextPosition()
        if (checkCoords(nextX, nextY) && matrix[nextY][nextX] == '#') {
            turnRight()
        } else {
            move(nextPosition())
        }
    }

    private fun turnRight() {
        if (bearing == 'n') {
            bearing = 'e'
        } else if (bearing == 'e') {
            bearing = 's'
        } else if (bearing == 's') {
            bearing = 'w'
        } else if (bearing == 'w') {
            bearing = 'n'
        }
    }

    private fun nextPosition(): Pair<Int, Int> {
        if (bearing == 'n') return Pair(x, y - 1)
        if (bearing == 'e') return Pair(x + 1, y)
        if (bearing == 's') return Pair(x, y + 1)
        if (bearing == 'w') return Pair(x - 1, y)

        throw Exception("Invalid bearing")
    }

    private fun sumValues(matrix: Array<Array<MutableSet<Char>>>): Int {
        var result = 0
        for (p in matrix) {
            for (q in p) {
                if (q.size > 0) result++
            }
        }
        return result
    }

    private fun getInitialPosition(matrix: List<List<Char>>): Pair<Int, Int> {
        for (y in 0 until matrix.size) {
            for (x in 0 until matrix[y].size) {
                if (matrix[y][x] == '^') return Pair(x, y)
            }
        }
        return Pair(-1, -1)
    }

    private fun coordsValid(): Boolean {
        return (x in 0 until cols && y in 0 until rows)
    }

    private fun checkCoords(x1: Int, y1: Int): Boolean {
        return (x1 in 0 until cols && y1 in 0 until rows)
    }
}