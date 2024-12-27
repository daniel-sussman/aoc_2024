package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayTen("data10.txt").first()
    println(firstResult)
    val secondResult = DayTen("data10.txt").second()
    println(secondResult)
}