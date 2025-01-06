package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayTwentyThreeTest {
    @Test
    fun partTwoIsCorrect() {
        val secondResult = DayTwentyThree("testdata23.txt").second()
        assertEquals("co,de,ka,ta", secondResult)
    }
}