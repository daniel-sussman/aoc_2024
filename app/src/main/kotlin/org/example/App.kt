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
    val secondResult = DaySix("data06.txt").second()
    println(secondResult)
}