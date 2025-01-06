package org.example

class DayTwentyTwo(filepath: String) {
    val secretNumbers = Importer.extractText(filepath).split('\n').map { it.toLong() }

    public fun first(): Long {
        var answer = 0L
        for (number in secretNumbers) {
            val result = nthNumber(number, 2000)
            answer += result
            println("$number: $result")
        }
        return answer
    }

    private fun nthNumber(initial: Long, n: Int): Long {
        var currentNumber = initial
        for (i in 0 until n) {
            currentNumber = nextNumber(currentNumber)
        }
        return currentNumber
    }

    private fun nextNumber(secretNumber: Long): Long {
        var num = secretNumber
        var result = num * 64
        result = mix(num, result)
        num = prune(result)

        result = num / 32
        result = mix(num, result)
        num = prune(result)

        result = num * 2048
        result = mix(num, result)
        num = prune(result)
        return num
    }

    private fun mix(a: Long, b: Long): Long {
        return b xor a
    }

    private fun prune(num: Long): Long {
        return num % 16777216
    }
}