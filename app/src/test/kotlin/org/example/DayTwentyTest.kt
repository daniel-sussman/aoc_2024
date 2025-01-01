package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTwentyTest {
    @Test
    fun partTwoIsCorrect() {
        var secondResult = DayTwenty("testdata20.txt").second(76)
        assertEquals(3, secondResult)
    }
}