package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayNineteen("data19.txt").first()
    println(firstResult)
    val secondResult = DayNineteen("data19.txt").second()
    println(secondResult)
}