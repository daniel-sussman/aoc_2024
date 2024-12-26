package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayEight("data08.txt").first()
    println(firstResult)
//    val secondResult = DaySeven.second("data07.txt")
//    println(secondResult)
}