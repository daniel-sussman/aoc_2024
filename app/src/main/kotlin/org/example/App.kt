package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayFour.first("data04.txt")
    println(firstResult)
//    val secondResult = DayThree.second("data03.txt")
//    println(secondResult)
}