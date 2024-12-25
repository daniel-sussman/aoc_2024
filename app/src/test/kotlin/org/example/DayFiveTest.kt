package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayFiveTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayFive.first("testdata05.txt")
        assertEquals(143, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayFive.second("testdata05.txt")
//        assertEquals(9, secondResult)
//    }
}