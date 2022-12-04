package days

import Day
import java.util.HashSet

class Day4 : Day {
    override fun day(): Int {
        return 4
    }

    override fun expected(): Day.Expect {
        return Day.Expect(2, 4)
    }

    fun getBoundary(pair: String): Pair<Int, Int> {
        val boundary = pair.split("-")
        return Pair(boundary[0].toInt(), boundary[1].toInt())
    }

    fun isSubsetOf(first: Pair<Int, Int>, second: Pair<Int, Int>): Boolean {
        return first.first >= second.first && first.second <= second.second
    }

    override fun solve1(lines: List<String>): Int {
        var total = 0;
        for (line in lines) {
            val pairs = line.split(",")
            val first = getBoundary(pairs[0])
            val second = getBoundary(pairs[1])
            total += if (isSubsetOf(first, second) || isSubsetOf(second, first)) {
                1
            } else {
                0
            }
        }
        return total
    }

    fun isBetween(value: Int, pair: Pair<Int, Int>): Boolean {
        return pair.first <= value && pair.second >= value
    }

    override fun solve2(lines: List<String>): Int {
        var total = 0;
        for (line in lines) {
            val pairs = line.split(",")
            val first = getBoundary(pairs[0])
            val second = getBoundary(pairs[1])
            total += if (isBetween(first.first, second) || isBetween(first.second, second) ||
                isBetween(second.first, first) || isBetween(second.second, first)) {
                1
            } else {
                0
            }
        }
        return total}
}