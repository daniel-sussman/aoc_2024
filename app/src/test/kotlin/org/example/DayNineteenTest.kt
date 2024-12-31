package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayNineteenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayNineteen("testdata19.txt").first()
        assertEquals(6, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayEighteen("testdata18.txt", true).second()
//        assertEquals("6,1", secondResult)
//    }
}