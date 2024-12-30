package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DaySixteenTest {
    @Test
    fun partOneIsCorrect() {
        val firstResult = DaySixteen("testdata16.txt").first()
        assertEquals(7036, firstResult)
    }

    @Test
    fun beAssuredPartOneIsCorrect() {
        val firstResult = DaySixteen("testdata16b.txt").first()
        assertEquals(11048, firstResult)
    }

    @Test
    fun partTwoIsCorrect() {
        val secondResult = DaySixteen("testdata16.txt").second()
        assertEquals(45, secondResult)
    }

    @Test
    fun beAssuredPartTwoIsCorrect() {
        val secondResult = DaySixteen("testdata16b.txt").second()
        assertEquals(64, secondResult)
    }
}