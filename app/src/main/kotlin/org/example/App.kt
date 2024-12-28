package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayTwelve("data12.txt").first()
    println(firstResult)
    val secondResult = DayTwelve("data12.txt").second()
    println(secondResult)
}