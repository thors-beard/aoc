package days

import Day

class Day1 : Day {

    override fun day(): Int {
        return 1
    }

    override fun expected(): Day.Expect {
        return Day.Expect(24000, 45000)
    }

    override fun solve1(lines: List<String>): Int {
        var group = 0
        var answer = 0

        for (line in lines) {
            if (line == "") {
                if (group > answer) {
                    answer = group
                }
                group = 0
            } else {
                group += line.toInt()
            }
        }

        return answer
    }

    override fun solve2(lines: List<String>): Int {
        var group = 0
        val groups = mutableListOf<Int>()

        for (line in lines) {
            if (line == "") {
                groups.add(group)
                group = 0
            } else {
                group += line.toInt()
            }
        }
        groups.add(group)
        groups.sort()
        return groups.asReversed().slice(0..2).sum()
    }
}