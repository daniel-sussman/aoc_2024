package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTwoTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayTwo.first("testdata02.csv")
        assertEquals(2, firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DayTwo.second("testdata02.csv")
        assertEquals(4, secondResult)
    }
}