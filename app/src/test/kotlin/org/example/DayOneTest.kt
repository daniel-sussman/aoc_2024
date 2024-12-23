package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DayOneTest {
    @Test
    fun partOneIsCorrect() {
        val result = DayOne.first("testdata01.csv")
        assertEquals(11, result)
    }

    @Test
    fun partTwoIsCorrect() {
        val result = DayOne.second("testdata01.csv")
        assertEquals(31, result)
    }
}