package days

import Day

class Day2 : Day {
    override fun day(): Int {
        return 2
    }

    override fun expected(): Day.Expect {
        return Day.Expect(15, 12)
    }

    fun drawPoint(draw: String): Int {
        return when (draw) {
            "A", "X" -> 1 // Rock
            "B", "Y" -> 2 // Paper
            "C", "Z" -> 3 // Scissor
            else -> 0
        }
    }

    fun draw(opponent: String, counter: String): Int {
        return when (opponent) {
            "A" -> when (counter) {
                "X" -> 0
                "Y" -> 1
                "Z" -> -1
                else -> 0
            }
            "B" -> when (counter) {
                "X" -> -1
                "Y" -> 0
                "Z" -> 1
                else -> 0
            }
            "C" -> when (counter) {
                "X" -> 1
                "Y" -> -1
                "Z" -> 0
                else -> 0
            }
            else -> 0
        }
    }

    override fun solve1(lines: List<String>): Int {
        var total = 0
        for (line in lines) {
            val split = line.split(" ")
            val opponent = split[0]
            val counter = split[1]
            val battle = draw(opponent, counter)
            if (battle < 0) {
                total += drawPoint(counter)
            } else if (battle > 0) {
                total += 6 + drawPoint(counter)
            } else {
                total += 3 + drawPoint(counter)
            }
        }
        return total
    }

    fun counter(opponent: String, result: String): String {
        return when (opponent) {
            "A" -> when (result) { // Rock
                "X" -> "C" // Loose
                "Y" -> "A" // Tie
                "Z" -> "B" // Win
                else -> ""
            }
            "B" -> when (result) { // Paper
                "X" -> "A"
                "Y" -> "B"
                "Z" -> "C"
                else -> ""
            }
            "C" -> when (result) { // Scissor
                "X" -> "B"
                "Y" -> "C"
                "Z" -> "A"
                else -> ""
            }
            else -> ""
        }
    }

    override fun solve2(lines: List<String>): Int {
        var total = 0
        for (line in lines) {
            val split = line.split(" ")
            val opponent = split[0]
            val result = split[1]
            val battle = counter(opponent, result)
            if (result == "X") {
                total += drawPoint(battle)
            } else if (result == "Y") {
                total += 3 + drawPoint(battle)
            } else if (result == "Z") {
                total += 6 + drawPoint(battle)
            }
        }
        return total
    }
}