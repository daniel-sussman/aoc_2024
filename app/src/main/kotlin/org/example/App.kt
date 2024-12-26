package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DaySix("data06.txt").first()
    println(firstResult)
//    val secondResult = DayFive.second("data05.txt")
//    println(secondResult)
}