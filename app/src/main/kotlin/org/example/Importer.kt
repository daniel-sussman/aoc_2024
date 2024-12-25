package org.example

import java.io.File
import java.io.InputStreamReader

class Importer {
    companion object {
        fun extract(filepath: String): List<List<Int>> {
            val csvFile = ClassLoader.getSystemResourceAsStream(filepath) ?: throw IllegalArgumentException("Cannot find $filepath")
            val rows = csvFile.bufferedReader().readLines()

            val firstRow = rows[0].split(",")
            val numColumns = firstRow.size
            val numRows = rows.size

            val result = List(numColumns) { ArrayList<Int>(numRows) }
            for (row in rows) {
                val values = row.split(",")

                values.forEachIndexed { index, value ->
                    result[index].add(value.toInt())
                }
            }

            return result
        }

        fun extractRows(filepath: String): List<List<Int>> {
            val csvFile = ClassLoader.getSystemResourceAsStream(filepath) ?: throw IllegalArgumentException("Cannot find $filepath")
            return csvFile.bufferedReader().useLines { lines ->
                lines.mapIndexed { index, row ->
                    row.split(",").mapIndexed { columnIndex, value ->
                        value.trim().toInt()
                    }
                }.toList()
            }
        }

        fun extractText(filepath: String): String {
            val inputStream = ClassLoader.getSystemResourceAsStream(filepath) ?: throw IllegalArgumentException("Cannot find $filepath")
            return InputStreamReader(inputStream).use { reader ->
                reader.readText()
            }
        }
    }
}