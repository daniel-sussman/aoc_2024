package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DaySixteenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DaySixteen("testdata16.txt").first()
        assertEquals(7036, firstResult)
    }

    @Test
    fun beAssuredPartOneIsCorrect() {
        val firstResult = DaySixteen("testdata16b.txt").first()
        assertEquals(11048, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayFifteenAndAHalf("testdata15.txt").second()
//        assertEquals(9021, secondResult)
//    }
}