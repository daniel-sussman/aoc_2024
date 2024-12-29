package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayFourteenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayFourteen("testdata14.txt", true).first()
        assertEquals(12, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayThirteen("testdata13.txt").second()
//        assertEquals(81, secondResult)
//    }
}