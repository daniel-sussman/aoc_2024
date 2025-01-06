package org.example

class DayTwentyOneB(filepath: String) {
    private val codes = Importer.extractText(filepath).split("\n")
    private val cache: MutableMap<Char, String> = mutableMapOf()

    abstract class Keypad(val rows: Int, val cols: Int) {
        abstract val array: List<List<Key?>>
        abstract val keys: Map<Char, Key>

        abstract fun sequences(chr: Char): Set<String>

        public fun getKey(x: Int, y: Int): Key? {
            if (x in 0 until cols && y in 0 until rows) {
                return if (array[y][x]!!.value == '_') null else array[y][x]
            } else {
                return null
            }
        }

        public fun pathsFrom(start: Key?, end: Key): Set<String> {
            if (start == null) return setOf()
            if (start == end) return setOf("A")

            val result: MutableSet<String> = mutableSetOf()

            if (start.x < end.x) pathsFrom(start.right(), end).forEach { result.add(">$it") }
            if (start.x > end.x) pathsFrom(start.left(), end).forEach { result.add("<$it") }
            if (start.y > end.y) pathsFrom(start.up(), end).forEach { result.add("^$it") }
            if (start.y < end.y) pathsFrom(start.down(), end).forEach { result.add("v$it") }

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
        private var pointingAt: Key = keys['A']!!

        override fun sequences(chr: Char): Set<String> {
            val strings = child.sequences(chr)
            println("child.sequences for $chr is $strings")
            val result: MutableSet<String> = mutableSetOf()
            for (str in strings) {
                for (c in str) {
                    val nextKey = keys[c]!!
                    println("getting paths from ${pointingAt.value} to ${nextKey.value}...")
                    result.addAll(pathsFrom(pointingAt, nextKey))
                    println("the paths are ${pathsFrom(pointingAt, nextKey)}")
                    println("result is now ${result}")
                    pointingAt = nextKey
                }
            }
            return result
        }
//        public override fun shortestSequence(string: String): String {
//            val outputString = child.shortestSequence(string)
//            val result = StringBuilder()
//            for (chr in outputString) {
//                val nextKey = keys[chr]!!
//                result.append(pathFrom(pointingAt, nextKey))
//                result.append("A")
//                pointingAt = nextKey
//            }
//            return result.toString()
//        }
//
//        private fun pathFrom(start: Key, end: Key): String {
//            val result = StringBuilder()
//
//            if (start.x < end.x) {
//                result.append(">").append(pathFrom(start.right()!!, end))
//            } else if (start.y < end.y) {
//                result.append("v").append(pathFrom(start.down()!!, end))
//            } else if (start.x > end.x && start.left() != null) {
//                result.append("<").append(pathFrom(start.left()!!, end))
//            } else if (start.y > end.y && start.up() != null) {
//                result.append("^").append(pathFrom(start.up()!!, end))
//            }
//
//            return result.toString()
//        }
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
        private var pointingAt: Key = keys['A']!!

        override fun sequences(chr: Char): Set<String> {
            val result: MutableSet<String> = mutableSetOf()

            val nextKey = keys[chr]!!
            result.addAll(pathsFrom(pointingAt, nextKey))
            pointingAt = nextKey

            return result
        }

//        public override fun shortestSequence(string: String): String {
//            val result = StringBuilder()
//            for (chr in string) {
//                val nextKey = keys[chr]!!
//                result.append(pathFrom(pointingAt, nextKey))
//                result.append("A")
//                pointingAt = nextKey
//            }
//            return result.toString()
//        }
//
//        private fun pathFrom(start: Key, end: Key): String {
//            val result = StringBuilder()
//
//            if (start.x < end.x) {
//                result.append(">").append(pathFrom(start.right()!!, end))
//            } else if (start.y > end.y) {
//                result.append("^").append(pathFrom(start.up()!!, end))
//            } else if (start.x > end.x && start.left() != null) {
//                result.append("<").append(pathFrom(start.left()!!, end))
//            } else if (start.y < end.y && start.down() != null) {
//                result.append("v").append(pathFrom(start.down()!!, end))
//            }
//
//            return result.toString()
//        }
    }

    class Key(private val keypad: Keypad, public val value: Char, private val coords: Pair<Int, Int>) {
        public val x = coords.first
        public val y = coords.second

        public fun isLeftOf(otherKey: Key) = keypad.getKey(x + 1, y) == otherKey
        public fun isRightOf(otherKey: Key) = keypad.getKey(x - 1, y) == otherKey
        public fun isAbove(otherKey: Key) = keypad.getKey(x, y + 1) == otherKey
        public fun isBelow(otherKey: Key) = keypad.getKey(x, y - 1) == otherKey

        public fun left() = keypad.getKey(x - 1, y)
        public fun right() = keypad.getKey(x + 1, y)
        public fun up() = keypad.getKey(x, y - 1)
        public fun down() = keypad.getKey(x, y + 1)

        public fun adjacent(): List<Key> {
            val left = keypad.getKey(x - 1, y)
            val right = keypad.getKey(x + 1, y)
            val up = keypad.getKey(x, y - 1)
            val down = keypad.getKey(x, y + 1)
            return listOf(left, right, up, down).filterNotNull()
        }
    }

    public fun first(): Long {
        var result = 0L
        val numericKeypad = NumericKeypad()
        val directionalKeypad = DirectionalKeypad(numericKeypad)
        val masterKeypad = DirectionalKeypad(directionalKeypad)
        for (code in codes) {
            val buttonString = StringBuilder()
            for (chr in code) {
                if (cache[chr] != null) {
                    buttonString.append(cache[chr])
                    continue
                }
                val shortestSequence = masterKeypad.sequences(chr).minBy { it.length }
                buttonString.append(shortestSequence)
                cache[chr] = shortestSequence
            }
            val sequence = buttonString.toString()

            val numeric = code.slice(0 until code.length - 1).toInt()
            val lengthOfShortestSequence = sequence.length
            result += numeric * lengthOfShortestSequence
            println("for code $code: $numeric * $lengthOfShortestSequence")
        }
        return result
    }
}