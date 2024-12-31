package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayEighteen("data18.txt", false).first()
    println(firstResult)
//    val secondResult = DaySeventeen("data17.txt").second()
//    println(secondResult)
}