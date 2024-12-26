package org.example

class DayEight(filepath: String) {
    private val matrix = Importer.extractMatrix(filepath)
    private val rows = matrix.size
    private val cols = matrix[0].size
    private var antinodes = Array(rows) { Array(cols) { 0 } }
    private val map = mutableMapOf<Char, MutableSet<Pair<Int, Int>>>()

    public fun first(): Int {
        mapAntennas()
        for (key in map.keys) {
            val antennas = map[key] ?: mutableSetOf()
            populateAntinodes(antennas)
        }
        return countAntinodes()
    }

    public fun second(): Int {
        mapAntennas()
        for (key in map.keys) {
            val antennas = map[key] ?: mutableSetOf()
            modelUsingResonantHarmonics(antennas)
        }
        return countAntinodes()
    }

    private fun mapAntennas() {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                val key = matrix[y][x]
                if (key != '.') map.getOrPut(key) { mutableSetOf() }.add(Pair(x, y))
            }
        }
    }

    private fun populateAntinodes(antennas: MutableSet<Pair<Int, Int>>) {
        for (a in antennas) {
            val otherAntennas = antennas.filter { it != a }
            for (b in otherAntennas) {
                val dx = a.first - b.first
                val dy = a.second - b.second
                addAntinode(a.first + dx, a.second + dy)
            }
        }
    }

    private fun modelUsingResonantHarmonics(antennas: MutableSet<Pair<Int, Int>>) {
        for (a in antennas) {
            val otherAntennas = antennas.filter { it != a }
            for (b in otherAntennas) {
                val dx = b.first - a.first
                val dy = b.second - a.second
                var x = a.first + dx
                var y = a.second + dy
                while (areValidCoords(x, y)) {
                    addAntinode(x, y)
                    x += dx
                    y += dy
                }
            }
        }
    }

    private fun areValidCoords(x: Int, y: Int) = (x >= 0 && x < cols && y >= 0 && y < rows)

    private fun addAntinode(x: Int, y: Int) {
        if (x >= 0 && x < cols && y >= 0 && y < rows) {
            antinodes[y][x] = 1
        }
    }

    private fun countAntinodes() = antinodes.flatten().count { it > 0 }

    private fun visualizeAntinodes() {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                if (antinodes[y][x] > 0) {
                    print('#')
                } else {
                    print('.')
                }
            }
            print('\n')
        }
    }
}