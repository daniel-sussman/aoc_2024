package org.example

class DayFour {
    companion object {
        fun first(filepath: String): Int {
            val matrix = Importer.extractMatrix(filepath)
            val rows = matrix.size
            val cols = matrix[0].size

            var result = 0

            for (y in matrix.indices) {
                for (x in matrix[y].indices) {
                    if (findHorizontal(matrix, x, y, rows, cols)) result++
                    if (findVertical(matrix, x, y, rows, cols)) result++
                    if (findDiagonalUp(matrix, x, y, rows, cols)) result++
                    if (findDiagonalDown(matrix, x, y, rows, cols)) result++
                }
            }
            return result
        }

        fun second(filepath: String): Int {
            val matrix = Importer.extractMatrix(filepath)
            val rows = matrix.size
            val cols = matrix[0].size

            var result = 0

            for (y in matrix.indices) {
                for (x in matrix[y].indices) {
                    if (findX(matrix, x, y, rows, cols)) result++
                }
            }
            return result
        }

        private fun findVertical(matrix: List<List<Char>>, x: Int, y: Int, rows: Int, cols: Int): Boolean {
            if (rows - 4 < y) return false

            val stringBuilder = StringBuilder()
            stringBuilder
                .append(matrix[y][x])
                .append(matrix[y + 1][x])
                .append(matrix[y + 2][x])
                .append(matrix[y + 3][x])
            return stringBuilder.toString() == "XMAS" || stringBuilder.toString() == "SAMX"
        }

        private fun findHorizontal(matrix: List<List<Char>>, x: Int, y: Int, rows: Int, cols: Int): Boolean {
            if (cols - 4 < x) return false

            val stringBuilder = StringBuilder()
            stringBuilder
                .append(matrix[y][x])
                .append(matrix[y][x + 1])
                .append(matrix[y][x + 2])
                .append(matrix[y][x + 3])
            return stringBuilder.toString() == "XMAS" || stringBuilder.toString() == "SAMX"
        }

        private fun findDiagonalUp(matrix: List<List<Char>>, x: Int, y: Int, rows: Int, cols: Int): Boolean {
            if (y < 3 || cols - 4 < x) return false

            val stringBuilder = StringBuilder()
            stringBuilder
                .append(matrix[y][x])
                .append(matrix[y - 1][x + 1])
                .append(matrix[y - 2][x + 2])
                .append(matrix[y - 3][x + 3])
            return stringBuilder.toString() == "XMAS" || stringBuilder.toString() == "SAMX"
        }

        private fun findDiagonalDown(matrix: List<List<Char>>, x: Int, y: Int, rows: Int, cols: Int): Boolean {
            if (rows - 4 < y || cols - 4 < x) return false

            val stringBuilder = StringBuilder()
            stringBuilder
                .append(matrix[y][x])
                .append(matrix[y + 1][x + 1])
                .append(matrix[y + 2][x + 2])
                .append(matrix[y + 3][x + 3])
            return stringBuilder.toString() == "XMAS" || stringBuilder.toString() == "SAMX"
        }

        private fun findX(matrix: List<List<Char>>, x: Int, y: Int, rows: Int, cols: Int): Boolean {
            if (y < 1 || y > rows - 2 || x < 1 || x > cols - 2) return false

            val a = StringBuilder()
            a
                .append(matrix[y - 1][x - 1])
                .append(matrix[y][x])
                .append(matrix[y + 1][x + 1])

            val b = StringBuilder()
            b
                .append(matrix[y + 1][x - 1])
                .append(matrix[y][x])
                .append(matrix[y - 1][x + 1])

            return (a.toString() == "MAS" || a.toString() == "SAM") && (b.toString() == "MAS" || b.toString() == "SAM")
        }
    }
}