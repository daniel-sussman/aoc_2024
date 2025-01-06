package org.example

import java.util.*

class DayTwentyTwo(filepath: String) {
    val secretNumbers = Importer.extractText(filepath).split('\n').map { it.toLong() }
    val cache: MutableMap<Long, Long> = mutableMapOf()
    val sequences: MutableMap<List<Int>, Int> = mutableMapOf()

    public fun first(): Long {
        var answer = 0L
        for (number in secretNumbers) {
            val result = nthNumber(number, 2000)
            answer += result
        }
        return answer
    }

    public fun second(): Long? {
        first() // build cache
        for (number in secretNumbers) {
            updateSequencesFor(number)
        }

        return sequences.values.maxOrNull()?.toLong()
    }

    private fun updateSequencesFor(initial: Long): Long {
        val n = 2000

        var currentNumber = initial
        val array: Array<Pair<Int, Int>> = Array(n) { Pair(10, 10) }
        for (i in 0 until n) {
            val previousNumber = currentNumber
            val previousNumberDigit = (previousNumber % 10).toInt()
            currentNumber = cache[currentNumber]!!
            val currentNumberDigit = (currentNumber % 10).toInt()
            val diff = currentNumberDigit - previousNumberDigit
            array[i] = Pair(diff, currentNumberDigit)
        }

        var bestPrices: MutableMap<List<Int>, Int> = mutableMapOf()
        for (j in 3 until array.size) {
            val list = listOf(array[j - 3].first, array[j - 2].first, array[j - 1].first, array[j].first)
            val price = array[j].second
            if (bestPrices[list] == null) bestPrices[list] = price // only the first occurrence
        }

        for (sequence in bestPrices) {
            val profits = sequences[sequence.key] ?: 0
            sequences[sequence.key] = profits + sequence.value
        }

        return currentNumber
    }

    private fun nthNumber(initial: Long, n: Int): Long {
        var currentNumber = initial
        for (i in 0 until n) {
            if (currentNumber in cache.keys) {
                currentNumber = cache[currentNumber]!!
            } else {
                val nextNumber = nextNumber(currentNumber)
                cache[currentNumber] = nextNumber
                currentNumber = nextNumber(currentNumber)
            }
        }
        return currentNumber
    }

    private fun nextNumber(secretNumber: Long): Long {
        var num = secretNumber
        var result = num shl 6 // num * 64
        result = mix(num, result)
        num = prune(result)

        result = num shr 5 // num / 32
        result = mix(num, result)
        num = prune(result)

        result = num shl 11 // num * 2048
        result = mix(num, result)
        num = prune(result)
        return num
    }

    private fun mix(a: Long, b: Long): Long {
        return b xor a
    }

    private fun prune(num: Long): Long {
        return num and 16777215
    }
}