package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayElevenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayEleven("testdata11.csv").first(6)
        assertEquals(22L, firstResult)
        val nextResult = DayEleven("testdata11.csv").first(25)
        assertEquals(55312L, nextResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DayEleven("testdata11.csv").second(6)
        assertEquals(22L, secondResult)
        val nextResult = DayEleven("testdata11.csv").second(25)
        assertEquals(55312L, nextResult)
    }
}