package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayNineTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayNine("testdata09.txt").first()
        assertEquals(1928L, firstResult)
    }
//
//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayEight("testdata08.txt").second()
//        assertEquals(34, secondResult)
//    }
}