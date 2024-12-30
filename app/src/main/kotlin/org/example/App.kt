package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DaySixteen("data16.txt").first()
    println(firstResult)
    val secondResult = DaySixteen("data16.txt").second()
    println(secondResult)
}