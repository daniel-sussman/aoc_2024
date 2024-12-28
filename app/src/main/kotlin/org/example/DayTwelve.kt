package org.example

import org.example.DayTen.Node
import java.util.*

class DayTwelve(filepath: String) {
    private val input = Importer.extractMatrix(filepath)
    private val rows = input.size
    private val cols = input[0].size
    private val whiteboard: MutableList<MutableList<Char>> = input.map { it.toMutableList() }.toMutableList()

    public fun first(): Int {
        println("getting plots...")
        val plots = getPlots()
        println("getting regions...")
        val regions = getRegions(plots)

        println("calculating prices...")
        return calculatePrice(regions)
    }

    public fun second(): Int {
        var result = 0
        return result
    }

    private fun calculatePrice(regions: Set<Region>): Int {
        var total = 0
        for (region in regions) {
            total += region.area() * region.perimeter()
        }
        return total
    }

    private fun getPlots(): Set<Plot> {
        val plots:MutableSet<Plot> = mutableSetOf()

        for (y in 0 until rows) {
            for (x in 0 until cols) {
                plots.add(Plot(x, y, input))
            }
        }

        return plots
    }

    private fun getRegions(plots: Set<Plot>): Set<Region> {
        val regions: MutableSet<Region> = mutableSetOf()
        val alreadyMapped: MutableSet<Plot> = mutableSetOf()
        for (plot in plots) {
            if (plot in alreadyMapped) continue

            alreadyMapped.add(plot)
            val region = Region(plot)

            val queue: Queue<Plot> = LinkedList()
            val contiguous = plot.getContiguousPlots()
            alreadyMapped.addAll(contiguous)
            contiguous.forEach { cp -> queue.offer(cp) }

            while (queue.isNotEmpty()) {
                val currentPlot = queue.poll()
                region.addPlot(currentPlot)
                val contiguous = currentPlot.getContiguousPlots()
                for (cp in contiguous.filter { e -> e !in alreadyMapped }) {
                    alreadyMapped.add(cp)
                    queue.offer(cp)
                }
            }

            regions.add(region)
        }
        return regions
    }

    class Region(private val firstPlot: Plot) {
        public val plots: MutableSet<Plot> = mutableSetOf(firstPlot)
        public val plant: Char = firstPlot.plant

        public fun addPlot(plot: Plot) {
            plots.add(plot)
        }

        public fun perimeter(): Int {
            var result = 0
            for (plot in plots) {
                result += plot.getPerimeter()
            }
            return result
        }

        public fun area(): Int {
            return plots.size
        }
    }

    class Plot(public val x: Int, public val y: Int, private val garden: List<List<Char>>) {
        public val plant: Char = garden[y][x]
        val gardenRows = garden.size
        val gardenCols = garden[0].size

        public fun getPerimeter(): Int {
            var numberOfAdjacentCells = 0
            if (isContinguous(x - 1, y)) numberOfAdjacentCells++
            if (isContinguous(x + 1, y)) numberOfAdjacentCells++
            if (isContinguous(x, y - 1)) numberOfAdjacentCells++
            if (isContinguous(x, y + 1)) numberOfAdjacentCells++
            return 4 - numberOfAdjacentCells
        }

        public fun getContiguousPlots(): Set<Plot> {
            val contiguous: MutableSet<Plot> = mutableSetOf()
            if (isContinguous(x - 1, y)) contiguous.add(Plot(x - 1, y, garden))
            if (isContinguous(x + 1, y)) contiguous.add(Plot(x + 1, y, garden))
            if (isContinguous(x, y - 1)) contiguous.add(Plot(x, y - 1, garden))
            if (isContinguous(x, y + 1)) contiguous.add(Plot(x, y + 1, garden))
            return contiguous
        }

        public fun isContinguous(xx: Int, yy: Int): Boolean {
            return inGarden(xx, yy) && garden[yy][xx] == plant
        }

        private fun inGarden(xx: Int, yy: Int): Boolean {
            return (xx in 0 until gardenCols && yy in 0 until gardenRows)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Plot) return false

            return x == other.x && y == other.y
        }

        override fun hashCode(): Int {
            return "($x, $y)".hashCode()
        }
    }
}