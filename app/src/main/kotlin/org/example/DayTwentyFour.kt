package org.example

import java.util.*

class DayTwentyFour(filepath: String) {
    private val wires: MutableMap<String, Int>
    private val gates: Queue<String>

    init {
        val (wireStrings, gateStrings) = Importer.extractText(filepath).split("\n\n")
        val wires: MutableMap<String, Int> = mutableMapOf()
        val gates: Queue<String> = LinkedList()

        for (string in wireStrings.split("\n")) {
            val (key, value) = string.split(": ")
            wires[key] = value.toInt()
        }

        this.wires = wires

        for (string in gateStrings.split("\n")) {
            gates.offer(string)
        }

        this.gates = gates

        updateWires()
    }

    private fun updateWires() {
        while (gates.isNotEmpty()) {
            val string = gates.poll()
            val regex = Regex("(\\w{3}) ([A-Z]+) (\\w{3}) -> (\\w{3})")
            val match = regex.find(string)!!
            val inputA = match.groupValues[1]
            val logic = match.groupValues[2]
            val inputB = match.groupValues[3]
            val output = match.groupValues[4]

            if (wires[inputA] != null && wires[inputB] != null) {
                handleGate(inputA, inputB, output, logic)
            } else {
                gates.offer(string)
            }
        }
    }

    private fun handleGate(inputA: String, inputB: String, output: String, logic: String) {
        when (logic) {
            "AND" -> wires[output] = wires[inputA]!! and wires[inputB]!!
            "OR" -> wires[output] = wires[inputA]!! or wires[inputB]!!
            "XOR" -> wires[output] = wires[inputA]!! xor wires[inputB]!!
            else -> throw Exception("Bad logic!")
        }
    }

    public fun first(): Long {
        val zWires = wires.filter { it.key.startsWith('z')}

        val result = StringBuilder()
        for (key in zWires.keys.sorted().reversed()) {
            result.append(wires[key])
        }
        return result.toString().toLong(2)
    }
}