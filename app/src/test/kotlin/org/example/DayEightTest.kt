package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayEightTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayEight("testdata08.txt").first()
        assertEquals(14, firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DayEight("testdata08.txt").second()
        assertEquals(34, secondResult)
    }
}