interface Day {

    data class Expect(val part1: Int = 0, val part2: Int = 0)

    fun day(): Int
    fun expected(): Expect
    fun solve1(lines: List<String>): Int
    fun solve2(lines: List<String>): Int
}