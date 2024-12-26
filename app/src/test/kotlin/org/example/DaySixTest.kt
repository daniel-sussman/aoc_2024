package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DaySixTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DaySix("testdata06.txt").first()
        assertEquals(41, firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DaySix("testdata06.txt").second()
        assertEquals(6, secondResult)
    }
}