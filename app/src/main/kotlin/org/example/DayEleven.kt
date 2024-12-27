package org.example

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
}