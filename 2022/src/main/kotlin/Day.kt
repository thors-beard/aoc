interface Day<T> {

    data class Expect<T>(val part1: T, val part2: T)

    fun day(): Int
    fun expected(): Expect<T>
    fun solve1(lines: List<String>): T
    fun solve2(lines: List<String>): T
}