package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayFourTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayFour.first("testdata04.txt")
        assertEquals(18, firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DayFour.second("testdata04.txt")
        assertEquals(9, secondResult)
    }
}