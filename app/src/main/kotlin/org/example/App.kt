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
//    val secondResult = DaySixteen("testdata16.txt").second()
//    println(secondResult)
}