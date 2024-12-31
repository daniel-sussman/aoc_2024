package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayNineteenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayNineteen("testdata19.txt").first()
        assertEquals(6, firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DayNineteen("testdata19.txt").second()
        assertEquals(16, secondResult)
    }
}