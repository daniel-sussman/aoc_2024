package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DaySeventeenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DaySeventeen("testdata17.txt").first()
        assertEquals("4,6,3,5,6,3,5,2,1,0", firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DaySeventeen("testdata17b.txt").second()
        assertEquals(117440, secondResult)
    }
}