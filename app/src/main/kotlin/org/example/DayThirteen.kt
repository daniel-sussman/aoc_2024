package org.example

class DayThirteen(filepath: String) {
    private val input = Importer.extractText(filepath).split("\n\n")

    class Machine(private val config: String) {
        public val buttonA: Pair<Int, Int>
        public val buttonB: Pair<Int, Int>
        public val target: Pair<Int, Int>
        public val costToWin: Int

        init {
            val (a, b, t) = configure()
            buttonA = a
            buttonB = b
            target = t
            costToWin = calculate()
            println("cost to win this machine is $costToWin")
        }

        private fun configure(): Triple<Pair<Int, Int>, Pair<Int, Int>, Pair<Int, Int>> {
            val button = Regex("Button [AB]: X([+-]\\d+), Y([+-]\\d+)")
            val buttons = button.findAll(config).toList()

            val targetRegex = Regex("Prize: X=(\\d+), Y=(\\d+)")
            val targetCoords = targetRegex.findAll(config).toList()

            val buttonA = buttons[0].groupValues.let {
                Pair(it[1].toInt(), it[2].toInt())
            }
            val buttonB = buttons[1].groupValues.let {
                Pair(it[1].toInt(), it[2].toInt())
            }
            val target = targetCoords[0].groupValues.let {
                Pair(it[1].toInt(), it[2].toInt())
            }
            return Triple(buttonA, buttonB, target)
        }

        public fun calculate(): Int {
            val firstEquation = listOf(buttonA.first, buttonB.first, target.first) // ax + by = z
            val secondEquation = listOf(buttonA.second, buttonB.second, target.second)
            val first = firstEquation.map { n -> n * secondEquation[0] }
            val second = secondEquation.map { n -> n * firstEquation[0] }

            val b = (second[2] - first[2]) / (second[1] - first[1])
            val a = (firstEquation[2] - firstEquation[1] * b) / firstEquation[0]

            if (a > 100 || b > 100) return 0

            if (firstEquation[0] * a + firstEquation[1] * b == firstEquation[2] &&
                secondEquation[0] * a + secondEquation[1] * b == secondEquation[2]) {
                return 3 * a + b
            } else {
                return 0
            }
        }
    }

    public fun first(): Int {
        val machines = input.map { config -> Machine(config) }
        return machines.sumOf { machine -> machine.costToWin }
    }

    public fun second(): Int {
        return 0
    }
}