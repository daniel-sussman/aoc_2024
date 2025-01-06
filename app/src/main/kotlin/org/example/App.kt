package org.example

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
//    val firstResult = DayTwentyTwo("data22.txt").first()
//    println(firstResult)
    val secondResult = DayTwentyTwo("data22.txt").second()
    println(secondResult)
}