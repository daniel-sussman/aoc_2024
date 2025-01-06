package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTwentyFourTest {
    @Test
    fun partOneIsCorrect() {
        val secondResult = DayTwentyFour("testdata24.txt").first()
        assertEquals(4L, secondResult)
    }

    @Test
    fun yeahPrettySurePartOneIsCorrect() {
        val secondResult = DayTwentyFour("testdata24b.txt").first()
        assertEquals(2024L, secondResult)
    }
}