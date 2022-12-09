package days

import Day
import kotlin.math.abs

class Day9 : Day<Int> {
    override fun day(): Int {
        return 9
    }

    override fun expected(): Day.Expect<Int> {
        return Day.Expect(13, 1)
    }

    data class Pos(var x: Int, var y: Int)

    private fun go(pos: Pos, dir: String) {
        when (dir) {
            "U" -> pos.y++
            "D" -> pos.y--
            "L" -> pos.x++
            "R" -> pos.x--
            else -> {}
        }
    }

    private fun follow(
        head: Pos,
        tail: Pos,
        visited: MutableSet<String>
    ) {
        val diffY = abs(head.y - tail.y)
        val diffX = abs(head.x - tail.x)
        val headAbove = head.y > tail.y
        val headBelow = head.y < tail.y
        val headLeft = head.x > tail.x
        val headRight = head.x < tail.x
        if (diffY > 1 && head.x == tail.x) {
            // Same column
            if (headAbove) go(tail, "U")
            else if (headBelow) go(tail, "D")
        } else if (diffX > 1 && head.y == tail.y) {
            // Same row
            if (headLeft) go(tail, "L")
            else if (headRight) go(tail, "R")
        } else if (diffY > 1 || diffX > 1) {
            // Diagonal
            if (headAbove) {
                go(tail, "U")

                if (headLeft) go(tail, "L")
                else if (headRight) go(tail, "R")
            } else if (headBelow) {
                go(tail, "D")

                if (headLeft) go(tail, "L")
                else if (headRight) go(tail, "R")
            }
        }
        visited.add("$tail")
    }

    override fun solve1(lines: List<String>): Int {
        return moveRope(lines, 2)
    }

    override fun solve2(lines: List<String>): Int {
        val result = moveRope(lines, 10)
        if (result == expected().part2) {
            if (moveRope(test2(), 10) == 36) {
                println("Test 2b OK!")
            } else {
                println("Failed at test 2b")
            }
        }
        return result
    }

    private fun moveRope(lines: List<String>, ropeLength: Int): Int {
        val visited = (Array(ropeLength) { mutableSetOf<String>() }).toList()
        val knots = (Array(ropeLength) { Pos(0, 0) }).asList()
        visited[ropeLength - 1].add("${knots[ropeLength - 1]}")
        lines.map { line -> """(\w) (\d+)""".toRegex().find(line)?.groupValues ?: listOf() }
            .forEach { move ->
                val dir = move[1]
                val distance = move[2].toInt()
                move(distance, knots, dir, visited)
            }
        return visited[ropeLength - 1].size
    }

    private fun move(
        distance: Int,
        knots: List<Pos>,
        dir: String,
        visited: List<MutableSet<String>>
    ) {
        (0 until distance).reversed().forEach { _ ->
            go(knots[0], dir)
            knots.windowed(2).withIndex().forEach { (index, pair) ->
                follow(pair[0], pair[1], visited[index + 1])
            }
        }
    }

    private fun test2(): List<String> {
        return listOf(
            "R 5",
            "U 8",
            "L 8",
            "D 3",
            "R 17",
            "D 10",
            "L 25",
            "U 20"
        )
    }
}