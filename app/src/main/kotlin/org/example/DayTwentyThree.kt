package org.example

class DayTwentyThree(filepath: String) {
    private val computers: Set<Computer>
    private val connections: MutableSet<Set<Computer>> = mutableSetOf()

    init {
        val data = Importer.extractText(filepath).split('\n')
        val computers: MutableSet<Computer> = mutableSetOf()
        for (pair in data) {
            val names = pair.split('-')
            val firstName = names.first()
            val secondName = names.last()
            val computerA = computers.find { it.name == firstName } ?: Computer(firstName)
            val computerB = computers.find { it.name == secondName } ?: Computer(secondName)

            computerA.connections.add(computerB)
            computerB.connections.add(computerA)
            computers.add(computerA)
            computers.add(computerB)
        }
        this.computers = computers
    }

    class Computer(public val name: String) {
        public val connections: MutableSet<Computer> = mutableSetOf()

        public fun isConnectedTo(other: Computer): Boolean {
            return other in connections
        }
    }

    private fun computePermutationsOf(list: List<Computer>, n: Int): Set<Set<Computer>> {
        val result: MutableSet<Set<Computer>> = mutableSetOf()
        for (computer in list) {
            val permutations = permutations(computer, computer.connections)
            result.addAll(permutations)
        }
        return result
    }

    private fun permutations(thisOne: Computer, others: Set<Computer>): Set<Set<Computer>> {
        if (others.size < 2) return setOf()

        val result: MutableSet<Set<Computer>> = mutableSetOf()

        val a = others.first()
        for (b in others - a) {
            if (thisOne.name.startsWith('t') || a.name.startsWith('t') || b.name.startsWith('t')) {
                if (a.isConnectedTo(b)) result.add(setOf(thisOne, a, b))
            }
        }
        return result + permutations(thisOne, others - a)
    }

    public fun first(): Int {
        val connectedComputers = computers.filter { it.connections.size >= 2 }

        val result = computePermutationsOf(connectedComputers, 3)
//        for (r in result) {
//            println(r.map { it.name } )
//        }
//        println(result.size)
//
//        for (computer in computers) {
//            val connectionsList = computer.connections.toList()
//            println("building connections for: ${computer.name}")
//            if (computer.name in listOf("co", "ka", "de")) println("${computer.name} connection list: ${connectionsList.map { it.name }}")
//            if (connectionsList.size < 2) continue
//            for (i in 0 until connectionsList.size - 1) {
//                val a = connectionsList[i]
//                val b = connectionsList[i + 1]
//                if (computer.name in listOf("co", "ka", "de")) println("${a.name} is connected to ${b.name}: ${a.isConnectedTo(b)}")
//                if (a.isConnectedTo(b)) {
//                    val set = setOf(computer, connectionsList[i], connectionsList[i + 1])
//                    if (computer.name in listOf("co", "ka", "de")) println("adding the following set: ${set.map{ it.name }}")
//                    connections.add(set)
//                }
//            }
//        }

//        val co = computers.find { it.name == "co" }!!
//        val ka = computers.find { it.name == "ka" }!!
//        val de = computers.find { it.name == "de" }!!
//        println("co connections: ${co.connections.map { it.name } }")
//        println("ka is connected to co: ${ka.isConnectedTo(co)}")
//        println("ka is connected to de: ${ka.isConnectedTo(de)}")
//        println("de is connected to co: ${de.isConnectedTo(co)}")
//        println("de is connected to ka: ${de.isConnectedTo(ka)}")
//        println("co is connected to de: ${co.isConnectedTo(de)}")
//        println("co is connected to ka: ${co.isConnectedTo(ka)}")
//        for (connection in connections.map { c -> c.sortedBy { it.name } } .sortedBy { c -> c.first().name }) println(connection.map { c -> c.name })
        return result.size
    }
}