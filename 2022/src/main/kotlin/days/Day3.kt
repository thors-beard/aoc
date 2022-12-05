package days

import Day
import java.util.HashSet

class Day3 : Day<Int> {
    override fun day(): Int {
        return 3
    }

    override fun expected(): Day.Expect<Int> {
        return Day.Expect(157, 70)
    }

    fun prio(collection: Collection<Char>): Int {
        return collection.sumOf { c ->
            if (c.isUpperCase()) {
                c.code - 'A'.code + 27
            } else {
                c.code - 'a'.code + 1
            }
        }
    }

    override fun solve1(lines: List<String>): Int {
        var total = 0
        for (line in lines) {
            val parts = HashSet<Char>()
            val duplicates = HashSet<Char>()
            val first = line.slice(0 until (line.length / 2))
            val second = line.slice((line.length / 2) until line.length)
            first.forEach { c -> parts.add(c) }
            second.forEach { c -> if (parts.contains(c)) duplicates.add(c) }
            total += prio(duplicates)
        }
        return total
    }

    override fun solve2(lines: List<String>): Int {
        var total = 0
        for (chunk in lines.chunked(3)) {
            val uniques = Array(3) { HashSet<Char>() }
            chunk.forEachIndexed { index, line ->
                line.forEach { c ->
                    uniques[index].add(c)
                }
            }
            val parts = HashMap<Char, Int>()
            uniques.forEach { unique ->
                unique.forEach { c ->
                    val count = parts.getOrPut(c) { 0 }
                    parts[c] = count + 1
                }
            }
            val oneOf = parts.filter { entry -> entry.value == 3 }.keys
            total += prio(oneOf)
        }
        return total
    }
}