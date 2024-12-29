package org.example

class DayFourteen(filepath: String, testMode: Boolean) {
    private val input = Importer.extractText(filepath).split("\n")
    private val rows = if (testMode) 7 else 103
    private val cols = if (testMode) 11 else 101

    class Robot(private val config: String, private val rows: Int, private val cols: Int) {
        public var x: Int
        public var y: Int
        public val dx: Int
        public val dy: Int

        init {
            val (x, y, dx, dy) = configure()
            this.x = x
            this.y = y
            this.dx = dx
            this.dy = dy
        }

        private fun configure(): List<Int> {
            val regex = Regex("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)")
            val data = regex.find(config)

            return listOf(data!!.groupValues[1].toInt(), data.groupValues[2].toInt(), data.groupValues[3].toInt(), data.groupValues[4].toInt())
        }

        public fun move(turns: Int) {
            x = (cols + (x + dx * turns) % cols) % cols
            y = (rows + (y + dy * turns) % rows) % rows
        }
    }

    public fun first(): Int {
        val robots = input.map { config -> Robot(config, rows, cols) }
        val numberOfTurns = 100
        var robotsInQ1 = 0
        var robotsInQ2 = 0
        var robotsInQ3 = 0
        var robotsInQ4 = 0

        for (robot in robots) {
            robot.move(numberOfTurns)
//            println("the robot's position after 100 moves is (${robot.x}, ${robot.y})")
            if (robot.x < cols / 2) {
                if (robot.y < rows / 2) {
                    robotsInQ1++
                } else if (robot.y > rows / 2) {
                    robotsInQ3++
                }
            } else if (robot.x > cols / 2) {
                if (robot.y < rows / 2) {
                    robotsInQ2++
                } else if (robot.y > rows / 2) {
                    robotsInQ4++
                }
            }
        }
        println("The number of robots in each quadrant is ($robotsInQ1, $robotsInQ2, $robotsInQ3, $robotsInQ4)")
        return robotsInQ1 * robotsInQ2 * robotsInQ3 * robotsInQ4
    }
}