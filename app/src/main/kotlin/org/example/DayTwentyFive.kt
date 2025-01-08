package org.example

class DayTwentyFive(filepath: String) {
    private val locks: List<Lock>
    private val keys: List<Key>

    init {
        val data = Importer.extractText(filepath).split("\n\n")
        val locks: MutableList<Lock> = mutableListOf()
        val keys: MutableList<Key> = mutableListOf()
        for (item in data) {
            val lines = item.split("\n")
            if (lines.first() == "#####") {
                locks.add(Lock(lines))
            } else if (lines.last() == "#####") {
                keys.add(Key(lines))
            }
        }
        this.locks = locks
        this.keys = keys
    }

    open class Tool(lines: List<String>) {
        public val heights: List<Int>

        init {
            val heights = Array(5) { -1 }
            for (line in lines) {
                for (i in line.indices) {
                    if (line[i] == '#') heights[i]++
                }
            }
            this.heights = heights.toList()
        }
    }

    class Key(lines: List<String>) : Tool(lines) {
        public fun fitsIn(lock: Lock): Boolean {
            for (i in heights.indices) {
                if (heights[i] + lock.heights[i] > 5) return false
            }
            return true
        }
    }
    class Lock(lines: List<String>) : Tool(lines) {}

    public fun first(): Int {
        val combosThatFit: MutableSet<Pair<Key, Lock>> = mutableSetOf()
        for (key in keys) {
            for (lock in locks) {
                if (key.fitsIn(lock)) combosThatFit.add(Pair(key, lock))
            }
        }
        return combosThatFit.size
    }
}