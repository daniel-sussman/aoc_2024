package org.example

import java.util.*

class DayNineteen(filepath: String) {
    val patterns: List<String>
    val designs: List<String>
    val cache: MutableMap<String, Long> = mutableMapOf()

    init {
        val (p, d) = Importer.extractText(filepath).split("\n\n")
        patterns = p.split(", ")
        designs = d.split("\n")
    }

    public fun first(): Int {
        var possible = 0
        for (design in designs) {
            if (isPossible(design)) possible++
        }
        return possible
    }

    public fun second(): Long {
        var ways = 0L
        for (design in designs) {
            ways += countWays(design)
        }
        return ways
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

    private fun countWays(design: String): Long {
        if (design.isEmpty()) return 1
        if (design in cache.keys) return cache[design]!!

        var ways = 0L
        for (pattern in patterns) {
            if (design.startsWith(pattern)) {
                ways += countWays(design.substring(pattern.length))
            }
        }
        cache[design] = ways
        return ways
    }

    private fun waysItIsPossible(design: String): Int {
        var ways = 0
        val queue: Queue<List<String>> = LinkedList()
        val visited: MutableSet<List<String>> = mutableSetOf()

        for (towel in nextTowels("", design)) {
            queue.offer(listOf(towel))
            visited.add(listOf(towel))
        }

        while (queue.isNotEmpty()) {
            val partial = queue.poll()
            val partialString = partial.joinToString("")
            if (partialString == design) {
                ways++
                continue
            }

            for (towel in nextTowels(partialString, design)) {
                val nextPartial = partial + towel
                if (nextPartial in visited) continue

                visited.add(nextPartial)
                queue.offer(nextPartial)
            }
        }

        return ways
    }
}