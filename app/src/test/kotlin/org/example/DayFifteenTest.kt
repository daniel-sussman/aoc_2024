package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayFifteenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayFifteen("testdata15.txt").first()
        assertEquals(10092, firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DayFifteenAndAHalf("testdata15.txt").second()
        assertEquals(9021, secondResult)
    }
}