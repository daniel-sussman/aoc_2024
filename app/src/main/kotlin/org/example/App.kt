package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayFive.first("data05.txt")
    println(firstResult)
//    val secondResult = DayFour.second("data04.txt")
//    println(secondResult)
}