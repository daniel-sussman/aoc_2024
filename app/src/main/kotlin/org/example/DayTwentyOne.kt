package org.example

class DayTwentyOne(filepath: String) {
    val codes = Importer.extractText(filepath).split("\n")

    val numericKeypad = NumericKeypad()
    val directionalKeypad = DirectionalKeypad(numericKeypad)
    val masterKeypad = DirectionalKeypad(directionalKeypad)

    abstract class Keypad(val rows: Int, val cols: Int) {
        abstract val array: List<List<Key?>>
        abstract val keys: Map<Char, Key>
        abstract var pointingAt: Key

        abstract fun pathFrom(start: Key, end: Key): String

        val cache: MutableMap<Pair<Key?, Key>, Set<String>> = mutableMapOf()

        public fun getKey(x: Int, y: Int): Key? {
            if (x in array[0].indices && y in array.indices) {
                return if (array[y][x]!!.value == '_') null else array[y][x]
            } else {
                return null
            }
        }

        public fun type(chr: Char): Set<String> {
//            println("calling #type with key $chr: the value of key is ${keys[chr]}")
            val key = keys[chr]!!
            val result = setOf(pathFrom(pointingAt, key) + 'A', pathFrom(pointingAt, key).reversed() + 'A')
            pointingAt = key
            return result
        }

        public fun pathsFrom(start: Key?, end: Key): Set<String> {
            val cacheKey = Pair(start, end)
            if (start == null) return setOf()
            if (cacheKey in cache.keys) return cache[cacheKey]!!
            if (start == end) return setOf("A")

            val result: MutableSet<String> = mutableSetOf()

            if (start.x < end.x) pathsFrom(start.right(), end).forEach { result.add(">$it") }
            if (start.x > end.x) pathsFrom(start.left(), end).forEach { result.add("<$it") }
            if (start.y > end.y) pathsFrom(start.up(), end).forEach { result.add("^$it") }
            if (start.y < end.y) pathsFrom(start.down(), end).forEach { result.add("v$it") }

            cache[cacheKey] = result
            return result
        }
    }

    class DirectionalKeypad(private val child: Keypad) : Keypad(2, 3) {
        override val keys: Map<Char, Key> = mapOf(
            '_' to Key(this, '_', Pair(0, 0)),
            '^' to Key(this, '^', Pair(1, 0)),
            'A' to Key(this, 'A', Pair(2, 0)),
            '<' to Key(this, '<', Pair(0, 1)),
            'v' to Key(this, 'v', Pair(1, 1)),
            '>' to Key(this, '>', Pair(2, 1))
        )
        override val array = listOf(
            listOf(keys['_'], keys['^'], keys['A']),
            listOf(keys['<'], keys['v'], keys['>'])
        )
        override var pointingAt: Key = keys['A']!!

        override fun pathFrom(start: Key, end: Key): String {
            val result = StringBuilder()

            if (start.x < end.x) {
                result.append(">").append(pathFrom(start.right()!!, end))
            } else if (start.y < end.y) {
                result.append("v").append(pathFrom(start.down()!!, end))
            } else if (start.x > end.x && start.left() != null) {
                result.append("<").append(pathFrom(start.left()!!, end))
            } else if (start.y > end.y && start.up() != null) {
                result.append("^").append(pathFrom(start.up()!!, end))
            }

            return result.toString()
        }

        public fun sequences(chr: Char): Set<String> {
            // return all keypad sequences that result in a given character
//            println("child is $child")
            val childStrings = child.type(chr)
//            println("childStrings are: $childStrings")
            val allSequences: MutableSet<String> = mutableSetOf()
            for (str in childStrings) {
                val result: MutableList<Set<String>> = mutableListOf()
                for (c in str) {
                    result.add(type(c))
                }
                allSequences.addAll(combinations(result))
            }
//            println("all sequences are: $allSequences")
            return allSequences
        }

        private fun combinations(list: List<Set<String>>): Set<String> {
            // return all iterations of a list of sets of string arrangements
            val first = list.first()

            if (list.size == 1) return first

            val result: MutableSet<String> = mutableSetOf() 
            for (start in first) {
                for (end in combinations(list.slice(1 until list.size))) {
                    result.add(start + end)
                }
            }
            return result
        }
    }

    class NumericKeypad() : Keypad(4, 3) {
        override val keys: Map<Char, Key> = mapOf(
            '7' to Key(this, '7', Pair(0, 0)),
            '8' to Key(this, '8', Pair(1, 0)),
            '9' to Key(this, '9', Pair(2, 0)),
            '4' to Key(this, '4', Pair(0, 1)),
            '5' to Key(this, '5', Pair(1, 1)),
            '6' to Key(this, '6', Pair(2, 1)),
            '1' to Key(this, '1', Pair(0, 2)),
            '2' to Key(this, '2', Pair(1, 2)),
            '3' to Key(this, '3', Pair(2, 2)),
            '_' to Key(this, '_', Pair(0, 3)),
            '0' to Key(this, '0', Pair(1, 3)),
            'A' to Key(this, 'A', Pair(2, 3)),
        )
        override val array = listOf(
            listOf(keys['7'], keys['8'], keys['9']),
            listOf(keys['4'], keys['5'], keys['6']),
            listOf(keys['1'], keys['2'], keys['3']),
            listOf(keys['_'], keys['0'], keys['A']),
        )
        override var pointingAt: Key = keys['A']!!

        override fun pathFrom(start: Key, end: Key): String {
            val result = StringBuilder()

            if (start.x < end.x) {
                result.append(">").append(pathFrom(start.right()!!, end))
            } else if (start.y > end.y) {
                result.append("^").append(pathFrom(start.up()!!, end))
            } else if (start.x > end.x && start.left() != null) {
                result.append("<").append(pathFrom(start.left()!!, end))
            } else if (start.y < end.y && start.down() != null) {
                result.append("v").append(pathFrom(start.down()!!, end))
            }

            return result.toString()
        }
    }

    class Key(private val keypad: Keypad, public val value: Char, private val coords: Pair<Int, Int>) {
        public val x = coords.first
        public val y = coords.second

        public fun left() = keypad.getKey(x - 1, y)
        public fun right() = keypad.getKey(x + 1, y)
        public fun up() = keypad.getKey(x, y - 1)
        public fun down() = keypad.getKey(x, y + 1)
    }

    public fun first(): Int {
        val tracking: MutableSet<Set<String>> = mutableSetOf()
        val paths = directionalKeypad.sequences('0')
        println("Now we'll try with masterKeypad...")
        for (path in paths) {
            val sequence: MutableSet<String> = mutableSetOf()
            for (chr in path) {
                sequence.addAll(masterKeypad.sequences(chr))
            }
//            println("The sequence is: $sequence")
            tracking.add(sequence)
        }
        val result = tracking.minBy { it.size }
        println("the shortest sequence is: $result")
        return 0
    }
}