package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTwelveTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayTwelve("testdata12.txt").first()
        assertEquals(1930, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayTen("testdata10.txt").second()
//        assertEquals(81, secondResult)
//    }
}