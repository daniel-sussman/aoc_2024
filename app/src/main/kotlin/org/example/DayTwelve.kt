package org.example

import org.example.DayTen.Node
import java.util.*

class DayTwelve(filepath: String) {
    private val input = Importer.extractMatrix(filepath)
    private val rows = input.size
    private val cols = input[0].size
    private val whiteboard: MutableList<MutableList<Char>> = input.map { it.toMutableList() }.toMutableList()

    public fun first(): Int {
        val plots = getPlots()
        val regions = getRegions(plots)
        return calculatePrice(regions)
    }

    public fun second(): Int {
        val plots = getPlots()
        val regions = getRegions(plots)
        return calculateSpecialPrice(regions)
    }

    private fun calculatePrice(regions: Set<Region>): Int {
        var total = 0
        for (region in regions) {
            total += region.area() * region.perimeter()
        }
        return total
    }

    private fun calculateSpecialPrice(regions: Set<Region>): Int {
        var total = 0
        for (region in regions) {
//            println("A region of ${region.plant} plants with price ${region.area()} * ${region.numberOfSides()} = ${region.area() * region.numberOfSides()}")
            total += region.area() * region.numberOfSides()
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
        public var topmost: Int = firstPlot.y
        public var bottommost: Int = firstPlot.y
        public var leftmost: Int = firstPlot.x
        public var rightmost: Int = firstPlot.x

        public fun addPlot(plot: Plot) {
            plots.add(plot)
            if (plot.y < topmost) topmost = plot.y
            if (plot.y > bottommost) bottommost = plot.y
            if (plot.x < leftmost) leftmost = plot.x
            if (plot.x > rightmost) rightmost = plot.x
        }

        public fun perimeter(): Int {
            var result = 0
            for (plot in plots) {
                result += plot.getPerimeter()
            }
            return result
        }

        private fun rows(): Array<MutableList<Plot>> {
            val rows = Array(bottommost - topmost + 1) { mutableListOf<Plot>() }
            for (plot in plots) {
                val index = plot.y - topmost
                rows[index].add(plot)
            }
            return rows
        }

        private fun columns(): Array<MutableList<Plot>> {
            val columns = Array(rightmost - leftmost + 1) { mutableListOf<Plot>() }
            for (plot in plots) {
                val index = plot.x - leftmost
                columns[index].add(plot)
            }
            return columns
        }

        public fun numberOfSides(): Int {
            var sides = 0
            sides += countTopAndBottomSides()
            sides += countLeftAndRightSides()
            return sides
        }

        private fun countLeftAndRightSides(): Int {
            var sides = 0
            for (col in columns()) {
                col.sortBy { plot -> plot.y }
                var leftContiguous = false
                var rightContiguous = false
                var expectedY = -1

                for (plot in col) {
                    if (plot.y != expectedY) {
                        expectedY = plot.y
                        leftContiguous = false
                        rightContiguous = false
                    }

                    if (plot.hasExteriorLeft() && !leftContiguous) {
                        leftContiguous = true
                        sides++
                    } else if (!plot.hasExteriorLeft() && leftContiguous) {
                        leftContiguous = false
                    }
                    if (plot.hasExteriorRight() && !rightContiguous) {
                        rightContiguous = true
                        sides++
                    } else if (!plot.hasExteriorRight() && rightContiguous) {
                        rightContiguous = false
                    }
                    expectedY++
                }
            }

            return sides
        }

        private fun countTopAndBottomSides(): Int {
            var sides = 0
            for (row in rows()) {
                row.sortBy { plot -> plot.x }
                var topContiguous = false
                var bottomContiguous = false
                var expectedX = -1

                for (plot in row) {
                    if (plot.x != expectedX) {
                        expectedX = plot.x
                        topContiguous = false
                        bottomContiguous = false
                    }
                    if (plot.hasExteriorTop() && !topContiguous) {
                        topContiguous = true
                        sides++
                    } else if (!plot.hasExteriorTop() && topContiguous) {
                        topContiguous = false
                    }
                    if (plot.hasExteriorBottom() && !bottomContiguous) {
                        bottomContiguous = true
                        sides++
                    } else if (!plot.hasExteriorBottom() && bottomContiguous) {
                        bottomContiguous = false
                    }
                    expectedX++
                }
            }

            return sides
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
            if (isContiguous(x - 1, y)) numberOfAdjacentCells++
            if (isContiguous(x + 1, y)) numberOfAdjacentCells++
            if (isContiguous(x, y - 1)) numberOfAdjacentCells++
            if (isContiguous(x, y + 1)) numberOfAdjacentCells++
            return 4 - numberOfAdjacentCells
        }

        public fun hasExteriorTop() = !isContiguous(x, y - 1)
        public fun hasExteriorBottom() = !isContiguous(x, y + 1)
        public fun hasExteriorLeft() = !isContiguous(x - 1, y)
        public fun hasExteriorRight() = !isContiguous(x + 1, y)

        public fun getContiguousPlots(): Set<Plot> {
            val contiguous: MutableSet<Plot> = mutableSetOf()
            if (isContiguous(x - 1, y)) contiguous.add(Plot(x - 1, y, garden))
            if (isContiguous(x + 1, y)) contiguous.add(Plot(x + 1, y, garden))
            if (isContiguous(x, y - 1)) contiguous.add(Plot(x, y - 1, garden))
            if (isContiguous(x, y + 1)) contiguous.add(Plot(x, y + 1, garden))
            return contiguous
        }

        public fun isContiguous(xx: Int, yy: Int): Boolean {
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