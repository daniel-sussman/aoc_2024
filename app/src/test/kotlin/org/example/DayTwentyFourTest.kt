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

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DayTwentyFour("testdata24c.txt").second()
        assertEquals("z00,z01,z02,z05", secondResult)
    }
}