package org.example

import java.util.*

class DayTwentyFourB(val filepath: String) {
    var nodes: MutableSet<Node>
    var memoryA: Node? = null
    var memoryB: Node? = null

    init {
        val (nodeStrings, logicStrings) = Importer.extractText(filepath).split("\n\n")
        val xAndYNodes: MutableSet<Node> = mutableSetOf()
        val gates: Queue<String> = LinkedList()

        for (string in nodeStrings.split("\n")) {
            val (name, initialValue) = string.split(": ")
            xAndYNodes.add(Node(name, initialValue.toInt()))
        }

        this.nodes = xAndYNodes

        for (string in logicStrings.split("\n")) {
            gates.offer(string)
        }

        updateNodes(gates)
    }

    private fun updateNodes(gates: Queue<String>) {
        while (gates.isNotEmpty()) {
            val string = gates.poll()
            val regex = Regex("(\\w{3}) ([A-Z]+) (\\w{3}) -> (\\w{3})")
            val match = regex.find(string)!!
            val inputA: Node? = nodes.find { it.name == match.groupValues[1] }
            val logicString = match.groupValues[2]
            val inputB: Node? = nodes.find { it.name == match.groupValues[3] }
            val outputString = match.groupValues[4]


            if (inputA != null && inputB != null) {
                handleGate(inputA, inputB, outputString, logicString)
            } else {
                gates.offer(string)
            }
        }
    }

    private fun logic(inputA: Node, inputB: Node, logic: String): Int {
        return when (logic) {
            "AND" -> inputA.value!! and inputB.value!!
            "OR" -> inputA.value!! or inputB.value!!
            "XOR" -> inputA.value!! xor inputB.value!!
            else -> throw Exception("Bad logic!")
        }
    }

    private fun handleGate(inputA: Node, inputB: Node, outputString: String, logic: String) {
        inputA.sibling = inputB
        inputB.sibling = inputA

        val outputValue = logic(inputA, inputB, logic)
        val output = Node(outputString, outputValue)
        output.parents = setOf(inputA, inputB)
        output.logic = logic
        nodes.add(output)
    }

    open class Node(public val name: String, public val value: Int? = null) {
        public var parents: Set<Node>? = null
        public var sibling: Node? = null
        public var logic: String? = null
        public var expectedValue: Int? = null
    }

    public fun second(): String {
        val nodesToSwap = listOf(
            Pair("kfp", "hbs"),
            Pair("z27", "jcp"),
            Pair("z18", "dhq"),
            Pair("z22", "pdg")
        )
        swapNodes(nodesToSwap)
        val zNodes = nodesThatStartWith('z').sortedBy { it.name }

        println(listOf("kfp", "hbs", "z27", "jcp", "z18", "dhq", "z22", "pdg").sorted().joinToString(","))
//        var alreadySeen: MutableSet<Node> = mutableSetOf()
//        for (node in zNodes) {
//            alreadySeen = diagnoseProblems(node, alreadySeen)
//        }

//        for (node in zNodes) {
//            alreadySeen = findNewAncestors(node, alreadySeen)
//        }
//
//        val selected = zNodes[18]
//        val result = traceBackward(selected)
        return ""
    }

    private fun readNodes(chr: Char): List<Int> {
        return nodesThatStartWith(chr).map { it.value!! }
    }

    private fun swapNodes(nodesToSwap: List<Pair<String, String>>) {
        val (nodeStrings, logicStrings) = Importer.extractText(filepath).split("\n\n")
        val xAndYNodes: MutableSet<Node> = mutableSetOf()
        val gates: Queue<String> = LinkedList()

        for (string in nodeStrings.split("\n")) {
            val (name, initialValue) = string.split(": ")
            xAndYNodes.add(Node(name, initialValue.toInt()))
        }

        this.nodes = xAndYNodes

        for (string in logicStrings.split("\n")) {
            var processedString = string
            for (pair in nodesToSwap) {
                val a = pair.first
                val b = pair.second
                if ("-> $a" in string) {
                    println("swapped $a and $b")
                    processedString = string.replace(a, b)
                }
                if ("-> $b" in string) {
                    println("swapped $b and $a")
                    processedString = string.replace(b, a)
                }
            }
            gates.offer(processedString)
        }

        updateNodes(gates)
    }

    private fun nodesThatStartWith(chr: Char): List<Node> {
        return nodes.filter { it.name.startsWith(chr) }.sortedBy { it.name }.reversed()
    }

    private fun countAncestors(node: Node): Int {
        var result = -1
        val nodeQueue: Queue<Node> = LinkedList()
        nodeQueue.offer(node)
        while (nodeQueue.isNotEmpty()) {
            val currentNode = nodeQueue.poll()
            result++
            val parents = currentNode.parents

            if (!parents.isNullOrEmpty()) {
                nodeQueue.addAll(parents)
            }
        }
        return result
    }

    private fun findNewAncestors(node: Node, alreadySeen: MutableSet<Node>): MutableSet<Node> {
        val nodeQueue: Queue<Node> = LinkedList()
        nodeQueue.offer(node)
        println("tracing ${node.name}:")

        while (nodeQueue.isNotEmpty()) {
            val currentNode = nodeQueue.poll()
            val parents = currentNode.parents

            if (!parents.isNullOrEmpty()) {
                println("${parents.map{ it.name }} : ${currentNode.logic} -> ${currentNode.name}")
                for (parent in parents) {
                    if (parent !in alreadySeen) {
                        nodeQueue.offer(parent)
                        alreadySeen.add(parent)
                    }
                }
            }
        }

        return alreadySeen
    }

    private fun diagnoseProblems(node: Node, alreadySeen: MutableSet<Node>): MutableSet<Node> {
        val nodeQueue: Queue<Node> = LinkedList()
        val nodeNumber = node.name.substring(1)
        val isRecording = nodeNumber.toInt() > 2
        val record: MutableList<Triple<Set<Node>, String, Node>> = mutableListOf()
        nodeQueue.offer(node)
        println("tracing ${node.name}:")

        while (nodeQueue.isNotEmpty()) {
            val currentNode = nodeQueue.poll()
            val parents = currentNode.parents

            if (!parents.isNullOrEmpty()) {
                record.add(Triple(parents, currentNode.logic!!, currentNode))
                for (parent in parents) {
                    if (parent !in alreadySeen) {
                        nodeQueue.offer(parent)
                        alreadySeen.add(parent)
                    }
                }
            }
        }

        if (!isRecording) return alreadySeen

        val x = nodes.find { it.name == "x$nodeNumber" }!!
        val y = nodes.find { it.name == "y$nodeNumber" }!!

        val prevNodeNumber = String.format("%02d", nodeNumber.toInt() - 1)
        val xx = nodes.find { it.name == "x${prevNodeNumber}"}!!
        val yy = nodes.find { it.name == "y${prevNodeNumber}"}!!

        if (record.size < 5) {
            println("problem with node ${node.name}:")
            for (line in record) {
                println("potential source: ${line.first.map{ it.name }} : ${line.second} -> ${line.third.name}")
            }
            return alreadySeen
        }

        val firstLine = record[0]
        val secondLine = record[1]
        val thirdLine = record[2]
        val fourthLine = record[3]
        val fifthLine = record[4]

        val firstValid = firstLine.first == setOf(secondLine.third, thirdLine.third) && firstLine.second == "XOR" && firstLine.third == node
        val secondValid = (secondLine.first == setOf(x, y) && secondLine.second == "XOR") ||
                (secondLine.first == setOf(fourthLine.third, fifthLine.third) && secondLine.second == "OR")
        val thirdValid = (thirdLine.first == setOf(x, y) && thirdLine.second == "XOR") ||
                (thirdLine.first == setOf(fourthLine.third, fifthLine.third) && thirdLine.second == "OR")
        val fourthValid = (fourthLine.third in secondLine.first || fourthLine.third in thirdLine.first) &&
                (fourthLine.first == setOf(xx, yy) && fourthLine.second == "AND" ||
                fourthLine.first == setOf(memoryA, memoryB) && fourthLine.second == "AND")
        val fifthValid = (fifthLine.third in secondLine.first || fifthLine.third in thirdLine.first) &&
                (fifthLine.first == setOf(xx, yy) && fifthLine.second == "AND" ||
                fifthLine.first == setOf(memoryA, memoryB) && fifthLine.second == "AND")

        memoryA = secondLine.third
        memoryB = thirdLine.third

        if (!firstValid) println("problem with ${node.name} line 1: ${firstLine.first.map{ it.name }} : ${firstLine.second} -> ${firstLine.third.name}")
        if (!secondValid) println("problem with ${node.name} line 2: ${secondLine.first.map{ it.name }} : ${secondLine.second} -> ${secondLine.third.name}")
        if (!thirdValid) println("problem with ${node.name} line 3: ${thirdLine.first.map{ it.name }} : ${thirdLine.second} -> ${thirdLine.third.name}")
        if (!fourthValid) println("problem with ${node.name} line 4: ${fourthLine.first.map{ it.name }} : ${fourthLine.second} -> ${fourthLine.third.name}")
        if (!fifthValid) println("problem with ${node.name} line 5: ${fifthLine.first.map{ it.name }} : ${fifthLine.second} -> ${fifthLine.third.name}")

        return alreadySeen
    }

    private fun traceBackward(node: Node): Set<Node> {
        val initialNodes: MutableSet<Node> = mutableSetOf()

        val nodeQueue: Queue<Node> = LinkedList()
        nodeQueue.offer(node)
        println("tracing ${node.name}:")

        while (nodeQueue.isNotEmpty()) {
            val currentNode = nodeQueue.poll()
            val parents = currentNode.parents

            if (parents.isNullOrEmpty()) {
                initialNodes.add(currentNode)
            } else {
                println("${parents.map { it.name }} : ${currentNode.logic} -> ${currentNode.name}")
                nodeQueue.addAll(parents)
            }
        }

        return initialNodes
    }

//    private fun supplyExpectedValues() {
//        val zNodes = nodesThatStartWith('z')
//
//        for (node in zNodes) {
//            traceBackward(node)
//        }
//    }

//    private fun traceBackward(node: Node) {
//        var currentNode = node
//        val nodeQueue: Queue<Node> = LinkedList()
//        nodeQueue.addAll(currentNode.parents!!)
//
//        var xParent: Node? = null
//        var yParent: Node? = null
//
//        while (nodeQueue.isNotEmpty()) {
//            if (currentNode.name.startsWith('x')) {
//                xParent = currentNode
//            } else if (currentNode.name.startsWith('y')) {
//                yParent = currentNode
//            } else if (currentNode.parents != null) {
//                nodeQueue.addAll(currentNode.parents!!)
//            }
//        }
//    }
}