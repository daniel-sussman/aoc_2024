package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayThirteenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayThirteen("testdata13.txt").first()
        assertEquals(480, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayThirteen("testdata13.txt").second()
//        assertEquals(81, secondResult)
//    }
}