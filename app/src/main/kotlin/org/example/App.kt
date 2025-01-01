package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayTwenty("data20.txt").first(100)
    println(firstResult)
//    val secondResult = DayNineteen("data19.txt").second()
//    println(secondResult)
}