package org.example

import java.util.*

class DayNineteen(filepath: String) {
    val patterns: List<String>
    val designs: List<String>

    init {
        val (p, d) = Importer.extractText(filepath).split("\n\n")
        this.patterns = p.split(", ")
        this.designs = d.split("\n")
    }

    public fun first(): Int {
        var possible = 0
        for (design in designs) {
            if (isPossible(design)) possible++
        }
        return possible
    }

    private fun nextTowels(partial: String, design: String): List<String> {
        val remaining = design.substring(partial.length)
        return patterns.filter { remaining.startsWith(it) }
    }

    private fun isPossible(design: String): Boolean {
        val queue: Queue<String> = LinkedList()
        val visited: MutableSet<String> = mutableSetOf()

        for (towel in nextTowels("", design)) {
            queue.offer(towel)
            visited.add(towel)
        }

        while (queue.isNotEmpty()) {
            val partial = queue.poll()
            if (partial == design) return true

            for (towel in nextTowels(partial, design)) {
                val nextPartial = partial + towel
                if (nextPartial in visited) continue

                visited.add(nextPartial)
                queue.offer(nextPartial)
            }
        }

        return false
    }
}