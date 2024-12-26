package org.example

class DayNine(filepath: String) {
    private val diskmap = Importer.extractText(filepath)

    public fun first(): Long {
        val disk = Disk(diskmap)
        disk.initializeDisk()
        disk.compact()
        return disk.getChecksum() // 800232992
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
}