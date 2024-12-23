package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayOne.first("data01.csv")
    println(firstResult)
    val secondResult = DayOne.second("data01.csv")
    println(secondResult)
}