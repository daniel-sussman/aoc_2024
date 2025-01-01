package org.example

import kotlin.math.abs

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val firstResult = DayTwenty("data20.txt").first(100)
    println(firstResult)
    val secondResult = DayTwenty("data20.txt").second(100)
    println(secondResult) // 856578
//    var secondResult = DayTwenty("testdata20.txt").second(76)
//    println(secondResult)
//    println(" compare: 3")
//    secondResult = DayTwenty("testdata20.txt").second(74)
//    println(secondResult)
//    println(" compare: ${3 + 4}")
//    secondResult = DayTwenty("testdata20.txt").second(72)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22}")
//    secondResult = DayTwenty("testdata20.txt").second(70)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12}")
//    secondResult = DayTwenty("testdata20.txt").second(68)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14}")
//    secondResult = DayTwenty("testdata20.txt").second(66)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14 + 12}")
//    secondResult = DayTwenty("testdata20.txt").second(64)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14 + 12 + 19}")
//    secondResult = DayTwenty("testdata20.txt").second(62)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14 + 12 + 19 + 20}")
//    secondResult = DayTwenty("testdata20.txt").second(60)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14 + 12 + 19 + 20 + 23}")
//    secondResult = DayTwenty("testdata20.txt").second(58)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14 + 12 + 19 + 20 + 23 + 25}")
//    secondResult = DayTwenty("testdata20.txt").second(56)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14 + 12 + 19 + 20 + 23 + 25 + 39}")
//    secondResult = DayTwenty("testdata20.txt").second(54)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14 + 12 + 19 + 20 + 23 + 25 + 39 + 29}")
//    secondResult = DayTwenty("testdata20.txt").second(52)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14 + 12 + 19 + 20 + 23 + 25 + 39 + 29 + 31}")
//    secondResult = DayTwenty("testdata20.txt").second(50)
//    println(secondResult)
//    println(" compare: ${3 + 4 + 22 + 12 + 14 + 12 + 19 + 20 + 23 + 25 + 39 + 29 + 31 + 32}")
}