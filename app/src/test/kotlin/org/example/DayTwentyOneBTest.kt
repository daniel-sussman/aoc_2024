package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTwentyOneBTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayTwentyOneB("testdata21.txt").first()
        assertEquals(126384, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DaySix("testdata06.txt").second()
//        assertEquals(6, secondResult)
//    }
}