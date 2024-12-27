package org.example

class DayNine(filepath: String) {
    private val diskmap = Importer.extractText(filepath)

    public fun first(): Long {
        val disk = Disk(diskmap)
        disk.initializeDisk()
        disk.compact()
        return disk.getChecksum()
    }

    public fun second(): Long {
        val disk = UpgradedDisk(diskmap)
        disk.initializeDisk()
        disk.compact()
        return disk.getChecksum()
    }

    class Disk(private val diskMap: String) {
        private val disk = mutableListOf<Int>()
        private val emptyBlocks = mutableListOf<Int>()

        public fun initializeDisk() {
            var id = 0
            var diskIndex = 0

            for (i in 0 until diskMap.length) {
                val chr = diskMap[i]
                var fileSize = chr.code - 48
                val writeEmptyBlocks = (i % 2 != 0)
                val writeValue = when (writeEmptyBlocks) {
                    false -> id
                    true -> -1
                }

                while (fileSize > 0) {
                    disk.add(writeValue)
                    if (writeEmptyBlocks) emptyBlocks.add(diskIndex)
                    diskIndex++
                    fileSize--
                }

                if (!writeEmptyBlocks) id++
            }
        }

        public fun compact() {
            var i = disk.size - 1

            while (emptyBlocks.size > 0) {
                val value = disk[i]
                if (value < 0) {
                    disk[i] = 0
                    i--
                    emptyBlocks.removeLast()
                    continue
                }

                disk[i] = 0
                disk[emptyBlocks.first()] = value
                emptyBlocks.removeFirst()
                i--
            }
        }

        public fun getChecksum(): Long {
            var result = 0L
            for (i in 0 until disk.size) {
                result += i * disk[i]
            }
            return result
        }

        public fun printDisk() {
            println(disk)
            println(emptyBlocks)
        }
    }

    class UpgradedDisk(private val diskMap: String) {
        private var disk = mutableListOf<Int>()
        private val files = mutableListOf<File>()

        class File(public val id: Int, public val indices: IntArray) {
            public fun firstIndex() = indices.first()
            public fun size() = indices.size
        }

        class EmptyBlock(public val indices: IntArray) {
            public fun lastIndex() = indices.last()
            public fun size() = indices.size
        }

        public fun initializeDisk() {
            var id = 0
            var diskIndex = 0

            for (i in 0 until diskMap.length) {
                val chr = diskMap[i]
                val fileSize = chr.code - 48
                val writeToFile = (i % 2 == 0)

                if (fileSize == 0) continue

                val indices = IntArray(fileSize) { diskIndex + it }

                if (writeToFile) {
                    files.add(File(id, indices))
                    indices.forEach { _ -> disk.add(id) }
                    id++
                } else {
                    indices.forEach { _ -> disk.add(-1) }
                }
                diskIndex += fileSize
            }
        }

        private fun getEmptyBlocks(): MutableList<EmptyBlock> {
            val blocks = mutableListOf<EmptyBlock>()
            var expectingEmpty = false
            val currentBlockIndices = mutableListOf<Int>()

            for (i in 0 until disk.size) {
                val value = disk[i]
                if (!expectingEmpty && value >= 0) {
                    continue
                } else if (!expectingEmpty) {
                    currentBlockIndices.add(i)
                    expectingEmpty = true
                } else if (value < 0) {
                    currentBlockIndices.add(i)
                } else {
                    blocks.add(EmptyBlock(currentBlockIndices.toIntArray()))
                    currentBlockIndices.clear()
                    expectingEmpty = false
                }
            }
            return blocks
        }

        public fun compact() {
            for (file in files.reversed()) {
                val emptyBlocks = getEmptyBlocks()

                val block = emptyBlocks.firstOrNull { it.size() >= file.size() && it.lastIndex() < file.firstIndex() }
                if (block == null) continue

                for (i in 0 until file.size()) {
                    disk[file.indices[i]] = -1
                    disk[block.indices[i]] = file.id
                    file.indices[i] = block.indices[i]
                }
            }
        }

        public fun getChecksum(): Long {
            var result = 0L
            for (i in 0 until disk.size) {
                if (disk[i] < 0) continue
                result += i * disk[i]
            }
            return result
        }

        public fun printDisk() {
            for (file in files) {
                println("File id is: ${file.id} and indices are ${file.indices}")
            }
            println(disk)
        }
    }
}