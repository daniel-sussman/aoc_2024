package org.example

import org.example.DayTen.Node
import java.util.*

class DayEleven(filepath: String) {
    private var stones = Importer.extractText(filepath).split(",").map { e -> e.toLong() }

    public fun first(limit: Int): Long {
        for (i in 0 until limit) {
            val nextStones = mutableListOf<Long>()

            for (n in stones) {
                if (n == 0L) {
                    nextStones.add(1L)
                    continue
                }

                val digits = n.toString()
                val numDigits = digits.length
                if (numDigits % 2 == 0) {
                    nextStones.add(digits.substring(0, numDigits / 2).toLong())
                    nextStones.add(digits.substring(numDigits / 2).toLong())
                } else {
                    nextStones.add(n * 2024)
                }
            }

            stones = nextStones
        }
        return stones.size.toLong()
    }

    public fun second(limit: Int): Long {
        var currentBlink: MutableMap<Long, Long> = mutableMapOf()
        var blinkCount = 0

        for (stone in stones) {
            currentBlink[stone] = currentBlink.getOrDefault(stone, 0L) + 1L
        }

        while (blinkCount < limit) {
            blinkCount++
            val nextBlink: MutableMap<Long, Long> = mutableMapOf()

            for (stone in currentBlink.keys) {
                if (stone == 0L) {
                    nextBlink[1L] = nextBlink.getOrDefault(1L, 0L) + currentBlink[stone]!!
                    continue
                }

                val digits = stone.toString()
                val numDigits = digits.length

                if (numDigits % 2 == 0) {
                    val first = digits.substring(0, numDigits / 2).toLong()
                    val second = digits.substring(numDigits / 2).toLong()
                    nextBlink[first] = nextBlink.getOrDefault(first, 0L) + currentBlink[stone]!!
                    nextBlink[second] = nextBlink.getOrDefault(second, 0L) + currentBlink[stone]!!
                } else {
                    val num = stone * 2024L
                    nextBlink[num] = nextBlink.getOrDefault(num, 0) + currentBlink[stone]!!
                }

            }
            currentBlink = nextBlink
        }

        return currentBlink.values.sum()
    }
}