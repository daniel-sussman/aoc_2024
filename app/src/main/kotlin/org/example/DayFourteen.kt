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
//        println("The number of robots in each quadrant is ($robotsInQ1, $robotsInQ2, $robotsInQ3, $robotsInQ4)")
        return robotsInQ1 * robotsInQ2 * robotsInQ3 * robotsInQ4
    }

    public fun second(): Int {
        val testRobots = input.map { config -> Robot(config, rows, cols) }
        val interestingTurns = getInterestingTurns(testRobots, 100000)

        for (turnNumber in interestingTurns) {
            val robots = input.map { config -> Robot(config, rows, cols) }
            for (robot in robots) robot.move(turnNumber)
            if (evaluate(robots)) {
                printASCII(robots)
                return turnNumber
            }
        }
        return 0
    }

    private fun getInterestingTurns(robots: List<Robot>, limit: Int): List<Int> {
        val interestingTurns = mutableListOf<Int>()
        var elapsedTurns = 0

        for (i in 0 until limit) {
            elapsedTurns++

            for (robot in robots) {
                robot.move(1)
            }
            if (possibleEasterEgg(robots)) {
                interestingTurns.add(elapsedTurns)
            }
        }

        return interestingTurns
    }

    public fun third(turnsToMove: Int) {
        val robots = input.map { config -> Robot(config, rows, cols) }
        for (robot in robots) {
            robot.move(turnsToMove)
        }
        printASCII(robots)
    }

    private fun printASCII(robots: List<Robot>) {
        val canvas = Array(rows) { y ->
            Array(cols) { x ->
                robots.filter { robot ->
                    robot.x == x && robot.y == y
                }.size
            }
        }

        for (row in canvas) {
            println(row.toList().map { cell -> if (cell > 0) '#' else ' ' })
        }
    }

    private fun possibleEasterEgg(robots: List<Robot>): Boolean {
        val alignmentCriterion = 34
        var meetingCriterion = 0
        for (col in 0 until cols) {
            val robotsAligned = robots.filter { robot -> robot.x == col }.size
            if (robotsAligned >= alignmentCriterion) meetingCriterion++
            if (meetingCriterion > 1) return true
        }
        return false
    }

    private fun evaluate(robots: List<Robot>): Boolean {
        val consecutiveCriterion = 25
        var consecutive = 0
        var lastY = -1

        val interestingColumn = 34
        val robotsAligned = robots.filter { robot -> robot.x == interestingColumn }
        val sortedRobots = robotsAligned.sortedBy { robot -> robot.y }
        for (robot in sortedRobots) {
            if (robot.y == lastY + 1) {
                consecutive++
                lastY++
            } else {
                lastY = robot.y
            }
            if (consecutive == consecutiveCriterion) return true
        }

        return false
    }

    private fun easterEgg(robots: List<Robot>): Boolean {
//        val middleColumn = cols / 2
//        val robotsAligned = robots.filter { robot -> robot.x == middleColumn }
//        return robotsAligned.size > 30
//        val firstColumn = 0
//        val robotsAligned = robots.filter { robot -> robot.x == firstColumn }
//        return robotsAligned.size > 60
        val alignmentCriterion = 34
        var meetingCriterion = 0
        for (col in 0 until cols) {
            val robotsAligned = robots.filter { robot -> robot.x == col }.size
            if (robotsAligned >= alignmentCriterion) meetingCriterion++
            if (meetingCriterion > 1) return true
        }
        return false
    }

    private fun sampleEasterEgg(robots: List<Robot>): Int {
        val middleColumn = cols / 2
        val robotsAligned = robots.filter { robot -> robot.x == middleColumn }
        return robotsAligned.size
    }
}