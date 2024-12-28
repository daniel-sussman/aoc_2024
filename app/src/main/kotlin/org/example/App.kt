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
//    val secondResult = DayEleven("data11.csv").second(75)
//    println(secondResult)
}