package org.example

class DayTwentyThree(filepath: String) {
    private val computers: Set<Computer>
    private val connections: MutableSet<Set<Computer>> = mutableSetOf()
    private val pairs: Set<Set<Computer>>

    init {
        val data = Importer.extractText(filepath).split('\n')
        val computers: MutableSet<Computer> = mutableSetOf()
        val pairs: MutableSet<Set<Computer>> = mutableSetOf()
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
            pairs.add(setOf(computerA, computerB))
        }
        this.computers = computers
        this.pairs = pairs
    }

    class Computer(public val name: String) {
        public val connections: MutableSet<Computer> = mutableSetOf()
        public val networks: MutableSet<LAN> = mutableSetOf()

        public fun isConnectedTo(other: Computer): Boolean {
            return other in connections
        }
    }

    class LAN(a: Computer, b: Computer) {
        public val members: MutableSet<Computer> = mutableSetOf(a, b)

        public fun isMember(computer: Computer) = computer in members
        public fun isEligible(c: Computer) = members.all { it.isConnectedTo(c) }
        public fun addMember(c: Computer) = members.add(c)
    }

    public fun first(): Int {
        val connectedComputers = computers.filter { it.connections.size >= 2 }

        val result = computePermutationsOf(connectedComputers, 3)
        return result.size
    }

    public fun second(): String {
        val largestSet = findLargestSet()
        return largestSet.members.map{ it.name }.sorted().joinToString(",")
    }

    private fun findLargestSet(): LAN {
        val networks: MutableSet<LAN> = mutableSetOf()
        for (pair in pairs) {
            val a = pair.first()
            val b = pair.last()

            var joinedToExisting = false
            for (network in networks) {
                if (network.isMember(a) && network.isEligible(b)) {
                    joinedToExisting = true
                    network.addMember(b)
                } else if (network.isMember(b) && network.isEligible(a)) {
                    joinedToExisting = true
                    network.addMember(a)
                }
            }
            if (!joinedToExisting) networks.add(LAN(a, b))
        }
        return networks.maxBy { it.members.size }
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
}