package org.example

import kotlin.math.abs

class DayTwo {
    companion object {
        fun first(filepath: String): Int {
            val rows = Importer.extractRows(filepath)
            var numOfSafeRows = 0

            for (row in rows) {
                var increasing = false
                var rowIsSafe = true

                for (i in 0 until row.size) {
                    if (i == 0) continue

                    val value = row[i]
                    val prevValue = row[i - 1]
                    if (i == 1) increasing = value > prevValue

                    if (isUnsafe(value, prevValue, increasing)) {
                        rowIsSafe = false
                        break
                    }
                }
                if (rowIsSafe) numOfSafeRows++
            }

            return numOfSafeRows
        }

        fun second(filepath: String): Int {
            val rows = Importer.extractRows(filepath)
            var numOfSafeRows = 0

            for (row in rows) {
                if (rowIsSafe(row)) {
                    numOfSafeRows++
                } else {
                    for (i in 0 until row.size) {
                        val permutation = removeElementAtIndex(row, i)
                        if (rowIsSafe(permutation)) {
                            numOfSafeRows++
                            break
                        }
                    }
                }
            }
            return numOfSafeRows
        }

        private fun removeElementAtIndex(list: List<Int>, indexToRemove: Int): List<Int> {
            return list.filterIndexed { index, _ -> index != indexToRemove }
        }

        private fun rowIsSafe(row: List<Int>): Boolean {
            var increasing = false
            var rowIsSafe = true

            for (i in 0 until row.size) {
                if (i == 0) continue

                val value = row[i]
                val prevValue = row[i - 1]
                if (i == 1) increasing = value > prevValue

                if (isUnsafe(value, prevValue, increasing)) {
                    rowIsSafe = false
                    break
                }
            }
            return rowIsSafe
        }

        private fun isUnsafe(value: Int, prevValue: Int, increasing: Boolean): Boolean {
            val diff = abs(value - prevValue)

            if (diff < 1 || diff > 3) return true
            if (increasing && value < prevValue) return true
            if (!increasing && value > prevValue) return true

            return false
        }
    }
}