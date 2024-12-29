package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayFourteen("data14.txt", false).first()
    println(firstResult)
//    val secondResult = DayThirteen("data13.txt").second()
//    println(secondResult)
}