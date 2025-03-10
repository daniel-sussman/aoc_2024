package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DaySevenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DaySeven.first("testdata07.txt")
        assertEquals(3749L, firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DaySeven.second("testdata07.txt")
        assertEquals(11387L, secondResult)
    }
}