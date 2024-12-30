package org.example

import kotlin.math.pow

class DaySeventeen(filepath: String) {
    private val data = Importer.extractText(filepath)
    private var registerA: Int
    private var registerB: Int
    private var registerC: Int
    private val program: List<Int>
    private var instructionPointer = 0
    private val sysOut: MutableList<Int> = mutableListOf()

    private val instructions: Map<Int, (Int) -> Unit> = mapOf(
        0 to { operand -> adv(operand) },
        1 to { operand -> bxl(operand) },
        2 to { operand -> bst(operand) },
        3 to { operand -> jnz(operand) },
        4 to { operand -> bxc(operand) },
        5 to { operand -> out(operand) },
        6 to { operand -> bdv(operand) },
        7 to { operand -> cdv(operand) }
    )

    private val comboOperands: Map<Int, Int>
        get() = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to getRegisterA(),
            5 to getRegisterB(),
            6 to getRegisterC()
        )

    init {
        val (a, b, c) = initializeRegisters()
        registerA = a
        registerB = b
        registerC = c
        program = initializeProgram()
    }

    private fun initializeRegisters(): List<Int> {
        val regexA = Regex("Register A: (\\d+)")
        val regexB = Regex("Register B: (\\d+)")
        val regexC = Regex("Register C: (\\d+)")
        val a = regexA.find(data)!!.groupValues[1].toInt()
        val b = regexB.find(data)!!.groupValues[1].toInt()
        val c = regexC.find(data)!!.groupValues[1].toInt()
        return listOf(a, b, c)
    }

    private fun initializeProgram(): List<Int> {
        val regex = Regex("Program: ([0-9,]+)")
        val match = regex.find(data)
        val programString = match!!.groupValues[1]
        return programString.split(",").map { it.toInt() }
    }

    private fun getRegisterA() = registerA
    private fun getRegisterB() = registerB
    private fun getRegisterC() = registerC

    public fun first(): String {
        while (instructionPointer < program.size) {
            nextInstruction()
        }

        return sysOut.joinToString(",")
    }

    private fun nextInstruction() {
        val (opcode, operand) = program.slice(instructionPointer..instructionPointer + 1)
        instructionPointer += 2
        instructions[opcode]?.invoke(operand)
    }

    private fun adv(operand: Int) {
        registerA = division(operand)
    }
    private fun bxl(operand: Int) {
        registerB = registerB xor operand
    }
    private fun bst(operand: Int) {
        registerB = moduloEight(operand)
    }
    private fun jnz(operand: Int) {
        if (registerA != 0) instructionPointer = operand
    }
    private fun bxc(operand: Int) {
        registerB = registerB xor registerC
    }
    private fun out(operand: Int) {
        val output = moduloEight(operand)
        sysOut.add(output)
    }
    private fun bdv(operand: Int) {
        registerB = division(operand)
    }
    private fun cdv(operand: Int) {
        registerC = division(operand)
    }

    private fun division(operand: Int): Int {
        val comboOperand = comboOperands[operand]!!
        val numerator = registerA
        val denominator = 1 shl comboOperand
        return numerator / denominator
    }

    private fun moduloEight(operand: Int) = comboOperands[operand]!! % 8
}