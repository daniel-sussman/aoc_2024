package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayElevenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayEleven("testdata11.csv").first(6)
        assertEquals(22L, firstResult)
        val nextResult = DayEleven("testdata11.csv").first(25)
        assertEquals(55312L, nextResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayEleven("testdata11.txt").second()
//        assertEquals(81, secondResult)
//    }
}