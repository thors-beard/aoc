package days

import Day
import kotlin.math.abs

class Day10 : Day<Int> {
    override fun day(): Int {
        return 10
    }

    override fun expected(): Day.Expect<Int> {
        return Day.Expect(13140, 1)
    }

    override fun solve1(lines: List<String>): Int {
        val checkpoints = listOf(20, 60, 100, 140, 180, 220)
        val results = mutableListOf<Int>()
        var register = 1
        val instructions = ArrayDeque(lines)
        var time = 0
        var instruction = ""
        val noop = { command: String ->  command.startsWith("noop")}
        val addx = { command: String -> command.split(" ")[1].toInt()}
        (1..220).forEach { cycle ->
            if (time == 0) {
                if (instruction != "" && !noop(instruction)) {
                    val value = addx(instruction)
                    register += value
                }
                instruction = instructions.removeFirst()
                time = if (noop(instruction)) {
                    1
                } else {
                    2
                }
            }
            time--
            if (checkpoints.contains((cycle))) {
                results.add(cycle * register)
            }
        }
        println(results)
        return results.sum()
    }

    override fun solve2(lines: List<String>): Int {
        var register = 1
        val instructions = ArrayDeque(lines)
        var time = 0
        var cycle = 1;
        var row = 0;
        var instruction = ""
        val noop = { command: String ->  command.startsWith("noop")}
        val addx = { command: String -> command.split(" ")[1].toInt()}
        val screen = mutableListOf<String>()
        screen.add("")
        while(instructions.isNotEmpty()) {
            if (instruction == "") {
                instruction = instructions.removeFirst()
                time = if (noop(instruction)) {
                    0
                } else {
                    1
                }
                println("Start cycle \t$cycle: begin executing $instruction")
            }

            val pixel = (cycle - 1) % 40
            val visible = (register - 1) <= pixel && pixel <= (register + 1)
            screen[row] = screen[row] + if (visible) {
                "#"
            } else {
                "."
            }
            println("During cycle \t$cycle: CRT draws pixel in position $pixel")
            println("Current CRT row: ${screen[row]}")
            if (screen[row].length == 40) {
                screen.add("")
                row++
            }
            if (time == 0) {
                if (!noop(instruction)) {
                    val value = addx(instruction)
                    register += value
                }
                println("End of cycle \t$cycle: finish executing $instruction (Register X is now $register)")
                instruction = ""
            }

            time--
            cycle++
            println()
        }
        println()
        screen.forEach{ line -> println(line) }
        println()
        return 1
    }
}