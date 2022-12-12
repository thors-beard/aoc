package days

import Day
import java.math.BigInteger
import kotlin.math.abs

class Monkey(
    items: List<Int>,
    val multiplier: Int = 0,
    val adder: Int = 0,
    val divider: Int = 0,
    val nextTrue: Int = 0,
    val nextFalse: Int = 0,
) {

    val items = ArrayDeque(items.map { item -> BigInteger.valueOf(item.toLong()) })
    var inspectionCount = 0L

    fun inspect(old: BigInteger, veryWorried: Boolean = false): BigInteger {
        inspectionCount++
        val level = if (multiplier != 0) {
            old * multiplier.toBigInteger()
        } else if (adder != 0) {
            old + adder.toBigInteger()
        } else {
            old * old
        }
        return if (veryWorried) {
            if (modulo(level) == 0) {
                divider.toBigInteger()
            } else {
                level
            }
        } else {
            level / 3L.toBigInteger()
        }
    }

    private fun modulo(item: BigInteger): Int {
        return (item % divider.toBigInteger()).toInt()
    }

    fun test(item: BigInteger): Int {
        return if (modulo(item) == 0) {
            nextTrue
        } else {
            nextFalse
        }
    }

    override fun toString(): String {
        return items.toString()
    }
}


class Day11 : Day<Long> {
    override fun day(): Int {
        return 11
    }

    override fun expected(): Day.Expect<Long> {
        return Day.Expect(10605, 2713310158)
    }


    override fun solve1(lines: List<String>): Long {

        val monkeys = monkeys(lines)

        (0..19).forEach { round ->
            monkeys.forEachIndexed { index, monkey ->
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    val inspected = monkey.inspect(item)
                    val next = monkey.test(inspected)
                    monkeys[next].items.add(inspected)
                }
            }
        }
        val mostActive = monkeys
            .map { m -> m.inspectionCount }
            .sortedDescending()
            .subList(0, 2)
        println("Most active: $mostActive")
        return mostActive[0] * mostActive[1]
    }

    override fun solve2(lines: List<String>): Long {

        val monkeys = monkeys(lines)

        val checkpoints = listOf(1, 20, 1000, 2000, 10000)
        (0 until 10000).forEach { round ->
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    val inspected = monkey.inspect(item, true)
                    val next = monkey.test(inspected)
                    monkeys[next].items.add(inspected)
                }
            }
            if (checkpoints.contains(round + 1)) {
                println("== After round ${round + 1} ==")
                monkeys.forEachIndexed { index, monkey ->
                    println("Monkey $index inspected items ${monkey.inspectionCount} times.")
                }

            }
        }
        val mostActive = monkeys
            .map { m -> m.inspectionCount }
            .sortedDescending()
            .subList(0, 2)
        println("Most active: $mostActive")
        return mostActive[0] * mostActive[1]
    }

    private fun monkeys(lines: List<String>): List<Monkey> {
        val monkeys = if (lines.size == 27) {
            listOf(
                Monkey(
                    listOf(79, 98), 19, 0, 23, 2, 3
                ),
                Monkey(
                    listOf(54, 65, 75, 74), 0, 6, 19, 2, 0
                ),
                Monkey(
                    listOf(79, 60, 97), 0, 0, 13, 1, 3
                ),
                Monkey(
                    listOf(74), 0, 3, 17, 0, 1
                )
            )
        } else {
            listOf(
                Monkey(
                    listOf(99, 63, 76, 93, 54, 73), 11, 0, 2, 7, 1
                ),
                Monkey(
                    listOf(91, 60, 97, 54), 0, 1, 17, 3, 2
                ),
                Monkey(
                    listOf(65), 0, 7, 7, 6, 5
                ),
                Monkey(
                    listOf(84, 55), 0, 3, 11, 2, 6
                ),
                Monkey(
                    listOf(86, 63, 79, 54, 83), 0, 0, 19, 7, 0
                ),
                Monkey(
                    listOf(96, 67, 56, 95, 64, 69, 96), 0, 4, 5, 4, 0
                ),
                Monkey(
                    listOf(66, 94, 70, 93, 72, 67, 88, 51), 5, 0, 13, 4, 5
                ),
                Monkey(
                    listOf(59, 59, 74), 0, 8, 3, 1, 3
                )
            )
        }
        return monkeys
    }
}