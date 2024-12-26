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

        fun second(filepath: String): Long {
            val raw = Importer.extractText(filepath)
            val lines = raw.split("\n")

            var result = 0L

            for (line in lines) {
                val (testValueString, equation) = line.split(": ")
                val testValue = testValueString.toLong()
                val terms = equation.split(" ").map { it.toLong() }

                if (validWithThreeOperators(terms, testValue)) result += testValue
            }

            return result
        }

        private fun validEquation(terms: List<Long>, testValue: Long): Boolean {
            val last = terms.last()
            if (terms.size == 1) return last == testValue

            val shortened = terms.slice(0 until terms.size - 1)

            return validEquation(shortened, testValue - last) || (testValue % last == 0L && validEquation(shortened, testValue / last))
        }

        private fun validWithThreeOperators(terms: List<Long>, testValue: Long): Boolean {
            val last = terms.last()
            if (terms.size == 1) return last == testValue

            val shortened = terms.slice(0 until terms.size - 1)

            return validWithThreeOperators(shortened, testValue - last) ||
                    (testValue % last == 0L && validWithThreeOperators(shortened, testValue / last)) ||
                    (decatenatable(testValue.toString(), last.toString()) && validWithThreeOperators(shortened, decatenate(testValue.toString(), last.toString())))
        }

        private fun decatenatable(testValue: String, last: String): Boolean {
            val digits = last.length
            if (digits >= testValue.length) return false
            val tail = testValue.substring(testValue.length - digits)

            return last == tail
        }

        private fun decatenate(testValue: String, last: String): Long {
            val digits = last.length

            return testValue.substring(0, testValue.length - digits).toLong()
        }
    }
}