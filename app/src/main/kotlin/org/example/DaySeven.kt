package org.example

class DaySeven {
    companion object {
        fun first(filepath: String): Long {
            val raw = Importer.extractText(filepath)
            val lines = raw.split("\n")

            var result = 0L

            for (line in lines) {
                val (testValueString, equation) = line.split(": ")
                val testValue = testValueString.toLong()
                val terms = equation.split(" ").map { it.toLong() }

                if (validEquation(terms, testValue)) result += testValue
            }

            return result
        }

        private fun validEquation(terms: List<Long>, testValue: Long): Boolean {
            val last = terms.last()
            if (terms.size == 1) return last == testValue

            val shortened = terms.slice(0 until terms.size - 1)

            return validEquation(shortened, testValue - last) || (testValue % last == 0L && validEquation(shortened, testValue / last))
        }
    }
}