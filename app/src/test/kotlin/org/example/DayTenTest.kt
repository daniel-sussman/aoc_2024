package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayTen("testdata10.txt").first()
        assertEquals(36, firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DayTen("testdata10.txt").second()
        assertEquals(81, secondResult)
    }
}