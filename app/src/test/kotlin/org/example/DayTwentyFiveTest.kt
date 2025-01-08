package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTwentyFiveTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayTwentyFive("testdata25.txt").first()
        assertEquals(3, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DaySix("testdata25.txt").second()
//        assertEquals(6, secondResult)
//    }
}