package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayFifteen("data15.txt").first()
    println(firstResult)
//    val secondResult = DayFourteen("data14.txt", false).second()
//    println(secondResult)
}