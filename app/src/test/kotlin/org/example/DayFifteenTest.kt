package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayFifteenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayFifteen("testdata15.txt").first()
        assertEquals(10092, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayThirteen("testdata13.txt").second()
//        assertEquals(81, secondResult)
//    }
}