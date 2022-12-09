package days

import Day
import kotlin.math.max

class Day8 : Day<Int> {
    override fun day(): Int {
        return 8
    }

    override fun expected(): Day.Expect<Int> {
        return Day.Expect(21, 8)
    }

    private fun compare(max: Int, other: Int, row: Int, column: Int, values: List<List<Int>>, visible: HashSet<String>): Int {
        val value = values[row][column]
        if (value > other && value > max) {
            val key = "$row,$column"
            visible.add(key)
        }
        return max(max, value)
    }

    private fun down(column: Int, values: List<List<Int>>): HashSet<String> {
        val height = values.size
        val visible = HashSet<String>()
        var max = values[0][column]
        for (row in 1 until height - 1) {
            val other = values[row-1][column]
            max = compare(max, other, row, column, values, visible)
        }
        return visible
    }

    private fun up(column: Int, values: List<List<Int>>): HashSet<String> {
        val height = values.size
        val visible = HashSet<String>()
        var max = values[height - 1][column]
        for (row in (1 until height - 1).reversed()) {
            val other = values[row+1][column]
            max = compare(max, other, row, column, values, visible)
        }
        return visible
    }

    private fun right(row: Int, values: List<List<Int>>): HashSet<String> {
        val width = values.first().size
        val visible = HashSet<String>()
        var max = values[row][0]
        for (column in 1 until width - 1) {
            val other = values[row][column-1]
            max = compare(max, other, row, column, values, visible)
        }
        return visible
    }

    private fun left(row: Int, values: List<List<Int>>): HashSet<String> {
        val width = values.first().size
        val visible = HashSet<String>()
        var max = values[row][width - 1]
        for (column in (1 until width - 1).reversed()) {
            val other = values[row][column+1]
            max = compare(max, other, row, column, values, visible)
        }
        return visible
    }

    override fun solve1(lines: List<String>): Int {
        val height = lines.size
        val width = lines.first().length
        val values = lines.map{line -> line.map{c -> c.digitToInt()}}
        var visible = HashSet<String>()

        for (column in 1 until width - 1) {
            val result = down(column, values)
            visible.addAll(result)
        }
        for (column in 1 until width - 1) {
            val result = up(column, values)
            visible.addAll(result)
        }
        for (row in 1 until height - 1) {
            val result = right(row, values)
            visible.addAll(result)
        }
        for (row in 1 until height - 1) {
            val result = left(row, values)
            visible.addAll(result)
        }

        return visible.size + ((width - 1) * 2) + ((height - 1) * 2)
    }

    private fun step(row: Int, column: Int, origin: Int, values: List<List<Int>>, dir: Char, step: Int): Int {
        val height = values.size
        val width = values.first().size
        var r = row
        var c = column
        when (dir) {
            'N' -> r++
            'S' -> r--
            'E' -> c++
            'W' -> c--
            else -> {}
        }
        if (r < 0 || r >= height
            || c < 0 || c >= width) {
            return step
        }
        val value = values[r][c]
        if (value >= origin) {
            return step + 1
        }
        return step(r, c, origin, values, dir, step) + 1
    }


    override fun solve2(lines: List<String>): Int {
        val height = lines.size
        val width = lines.first().length
        val values = lines.map{line -> line.map{c -> c.digitToInt()}}
        var max = 0
        for (row in 0 until width) {
            for (column in 0 until height) {
                val north = step(row, column, values[row][column], values, 'N', 0)
                val south = step(row, column, values[row][column], values, 'S', 0)
                val east = step(row, column, values[row][column], values, 'E', 0)
                val west = step(row, column, values[row][column], values, 'W', 0)
                val score = north * south * east * west
                max = max(max, score)
            }
        }
        return max
    }
}