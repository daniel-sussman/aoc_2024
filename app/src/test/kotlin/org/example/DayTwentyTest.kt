package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTwentyTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayTwenty("testdata20.txt").first(8)
        assertEquals(14, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayNineteen("testdata19.txt").second()
//        assertEquals(16, secondResult)
//    }
}