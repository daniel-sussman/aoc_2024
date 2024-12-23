package org.example

import kotlin.math.abs

class DayOne {
    companion object {
        public fun first(filepath: String): Int {
            var totalDiff = 0
            val (firstColumn, secondColumn) = Importer.extract(filepath)
            val firstSorted = firstColumn.sorted()
            val secondSorted = secondColumn.sorted()

            for (i in 0 until firstColumn.size) {
                totalDiff += abs(firstSorted[i] - secondSorted[i])
            }

            return totalDiff
        }
    }
}