package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayEighteenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayEighteen("testdata18.txt", true).first()
        assertEquals(22, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayTen("testdata10.txt").second()
//        assertEquals(81, secondResult)
//    }
}