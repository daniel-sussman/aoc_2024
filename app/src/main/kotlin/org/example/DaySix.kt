package org.example

class DaySix(filepath: String) {
    private val matrix = Importer.extractMatrix(filepath)
    private val rows = matrix.size
    private val cols = matrix[0].size
    private val path = Array(rows) { Array(cols) { 0 } }
    private var bearing = 'n'
    private var x: Int
    private var y: Int

    init {
        val (x, y) = getInitialPosition(matrix)
        this.x = x
        this.y = y
    }

    public fun first(): Int {
        while (coordsValid()) {
            path[y][x] = 1
            update()
        }

        return sumValues(path)
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

    private fun sumValues(matrix: Array<Array<Int>>): Int {
        return matrix.sumOf { row -> row.sum() }
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