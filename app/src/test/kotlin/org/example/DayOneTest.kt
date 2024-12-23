package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DayOneTest {
    @Test
    fun returnsCorrectInt() {
        val result = DayOne.first("testdata01.csv")
        assertEquals(11, result)
    }
}