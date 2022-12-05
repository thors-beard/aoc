package days

import Day
import java.util.HashSet

class Day5 : Day<String> {
    override fun day(): Int {
        return 5
    }

    override fun expected(): Day.Expect<String> {
        return Day.Expect("CMZ", "MCD")
    }

    override fun solve1(lines: List<String>): String {
        return solve(lines, false)
    }

    private fun solve(lines: List<String>, keepOrder: Boolean): String {
        val split = lines.indexOf("")
        val board = createBoard(lines.slice(0 until split))
        val moves = lines.slice(split + 1 until lines.size)
        for (move in moves) {
            val parts = move.split(" ")
            val count = parts[1].toInt()
            val from = parts[3].toInt() - 1
            val to = parts[5].toInt() - 1
            val removed = mutableListOf<Char>()
            IntRange(0, count - 1).forEach{ _ ->
                removed.add(board[from].removeLast())
            }
            board[to].addAll(if (keepOrder) removed.reversed() else removed)
        }
        return board.filter { b -> !b.isEmpty() }.map { b -> b.removeLast() }.joinToString("")
    }

    private fun createBoard(input: List<String>): Array<ArrayDeque<Char>> {
        val buckets = input.last()
        val size = buckets.split(" ").last { c -> c != "" }.toInt()
        val board = Array<ArrayDeque<Char>>(size) { ArrayDeque() }
        input.reversed().slice(1 until input.size).forEach { line ->
            IntRange(0, size - 1).forEach { bucket ->
                val position = (bucket * 4) + 1
                var crate = ' ';
                if (position < line.length)
                    crate = line[position]
                if (crate != ' ')
                    board[bucket].add(crate)
            }
        }
        return board
    }

    override fun solve2(lines: List<String>): String {
        return solve(lines, true)
    }
}