package org.example

class DayFive {
    companion object {
        fun first(filepath: String): Int {
            val raw = Importer.extractText(filepath)
            val lines = raw.split("\n")
            var setRule = true

            val rules = mutableMapOf<String, MutableSet<String>>()
            var result = 0

            for (line in lines) {
                if (line == "") {
                    setRule = false
                    continue
                }

                if (setRule) {
                    val (prior, subsequent) = line.split("|")
                    rules.getOrPut(prior) { mutableSetOf() }.add(subsequent)
                } else {
                    val update = line.split(",")
                    val updateIsValid = validate(update, rules)

                    if (updateIsValid) {
                        result += getMiddleValue(update)
                    }
                }
            }
            return result
        }

        private fun validate(series: List<String>, rules: Map<String, MutableSet<String>>): Boolean {
            for (i in 1 until series.size) {
                val currentRule = rules[series[i]] ?: continue

                val elementsToTest = series.subList(0, i)
                if (elementsToTest.any { it in currentRule }) return false
            }
            return true
        }

        private fun getMiddleValue(series: List<String>): Int {
            val middleIndex = series.size / 2
            return series[middleIndex].toInt()
        }
    }
}