package org.example

class DayThree {
    companion object {
        fun first(filepath: String): Int {
            val text = Importer.extractText(filepath)
            val regex = Regex("mul\\([0-9]{1,3},[0-9]{1,3}\\)")
            val matches = regex.findAll(text)

            var result = 0

            for (match in matches) {
                result += compute(match.value)
            }

            return result
        }

        fun second(filepath: String): Int {
            val text = Importer.extractText(filepath)
            val regex = Regex("mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)")
            val matches = regex.findAll(text)

            var result = 0
            var switchedOn = true

            for (match in matches) {
                if (match.value == "do()") {
                    switchedOn = true
                } else if (match.value == "don't()") {
                    switchedOn = false
                } else if (switchedOn) {
                    result += compute(match.value)
                }
            }

            return result
        }

        private fun compute(input: String): Int {
            val regex = Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")

            val matchResult = regex.matchEntire(input)
            if (matchResult != null) {
                val (a, b) = matchResult.destructured
                return a.toInt() * b.toInt()
            } else {
                throw Exception("Oops! The instruction $input is invalid.")
            }
        }
    }
}