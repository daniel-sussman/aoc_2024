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

        fun second(filepath: String): Int {
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
                    val update = line.split(",").toMutableList()
                    val (needsFixing, fixedUpdate) = checkAndFix(update, rules, false)

                    if (needsFixing) {
                        result += getMiddleValue(fixedUpdate)
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

        private fun checkAndFix(series: MutableList<String>, rules: Map<String, MutableSet<String>>, wasModified: Boolean): Pair<Boolean, MutableList<String>> {
            for (i in 1 until series.size) {
                val elementA = series[i]
                val currentRule = rules[elementA] ?: continue

                val elementsToTest = series.subList(0, i)
                for (j in 0 until elementsToTest.size) {
                    val elementB = elementsToTest[j]
                    if (elementB in currentRule) {
                        series[i] = elementB
                        series[j] = elementA
                        return checkAndFix(series, rules, true)
                    }
                }
            }

            return Pair(wasModified, series)
        }

        private fun getMiddleValue(series: List<String>): Int {
            val middleIndex = series.size / 2
            return series[middleIndex].toInt()
        }
    }
}