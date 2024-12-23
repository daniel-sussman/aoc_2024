package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayTwo.first("data02.csv")
    println(firstResult)
    val secondResult = DayTwo.second("data02.csv")
    println(secondResult)
}