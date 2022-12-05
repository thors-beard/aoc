import days.*

fun readFile(fileName: String): List<String>? = object {}.javaClass.getResourceAsStream(fileName)
    ?.bufferedReader()
    ?.readLines()

fun read(part: String, day: Int): List<String> {
    return readFile("/$day/$part.txt") ?: listOf()
}


fun <T> runDay(day: Day<T>) {
    var result = day.solve1(read("test", day.day()))
    if (result != day.expected().part1) {
        println("Failed part 1 test run. Got '$result', should be '${day.expected().part1}'.")
        return
    } else {
        println("Test OK! ${day.expected().part1}")
    }
    result = day.solve1(read("input", day.day()))
    println("Answer to part 1: $result")

    if (0 == day.expected().part2 || "" == day.expected().part2) {
        return
    }
    result = day.solve2(read("test", day.day()))
    if (result != day.expected().part2) {
        println("Failed part 2 test run. Got '$result', should be '${day.expected().part2}'.")
        return
    } else {
        println("Test OK! ${day.expected().part2}")
    }
    result = day.solve2(read("input", day.day()))
    println("Answer to part 2: $result")
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("No day specified")
    }
    val day = args[0].toInt()
    println("Running day $day")


    when (day) {
        1 -> runDay(Day1())
        2 -> runDay(Day2())
        3 -> runDay(Day3())
        4 -> runDay(Day4())
        5 -> runDay(Day5())
        else -> throw NotImplementedError("Day not implemented")
    }
}