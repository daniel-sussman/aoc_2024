package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayNine("data09.txt").first()
    println(firstResult)
    val secondResult = DayNine("data09.txt").second()
    println(secondResult)
}