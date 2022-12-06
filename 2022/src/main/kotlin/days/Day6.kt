package days

import Day
import java.util.HashSet
import kotlin.streams.toList

class Day6 : Day<Int> {
    override fun day(): Int {
        return 6
    }

    override fun expected(): Day.Expect<Int> {
        return Day.Expect(7, 19)
    }

    override fun solve1(lines: List<String>): Int {
        val line = lines.first()
        return solve(line, 4)
   }

    private fun solve(line: String, windowSize: Int): Int {
        var result = 0;
        line.windowed(windowSize).forEachIndexed{ index, window ->
            val chars = HashSet(window.toList())
            if (chars.size == windowSize && result == 0) {
                result = index + windowSize
            }
        }
        return result
    }

    override fun solve2(lines: List<String>): Int {
        val line = lines.first()
        return solve(line, 14)
    }
}