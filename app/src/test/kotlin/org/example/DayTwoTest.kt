package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTwoTest {
    @Test
    fun partOneIsCorrect() {
        val result = DayTwo.first("testdata02.csv")
        assertEquals(2, result)
    }
}