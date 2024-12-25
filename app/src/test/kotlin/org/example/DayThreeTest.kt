package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayThreeTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DayThree.first("testdata03.txt")
        assertEquals(161, firstResult)
    }

//    @Test
//    fun partTwoIsCorrect() {
//        val secondResult = DayThree.second(testData)
//        assertEquals(4, secondResult)
//    }
}