package days

import Day

class Day7 : Day<Long> {
    override fun day(): Int {
        return 7
    }

    override fun expected(): Day.Expect<Long> {
        return Day.Expect(95437, 24933642)
    }

    data class Dir(val name: String, val top: Dir?, var size: Long = 0, val folders: MutableList<Dir> = mutableListOf())

    private fun smallSizes(dir: Dir, depth: Int, small: MutableList<Dir>): Long {
        val size = dir.folders.sumOf { smallSizes(it, depth + 1, small) }
        val total = size + dir.size
        if (total < 100000) {
            small.add(Dir(dir.name, null, total))
        }
        for (i in 0 until depth) {
            print("\t")
        }
        println("- ${dir.name} (dir=${dir.size}, size=${total}")
        return total
    }

    override fun solve1(lines: List<String>): Long {
        val root = Dir("/", null)
        var current = root
        for (line in lines.subList(1, lines.size)) {
            val cd = """\$ cd ([\w/.]+)""".toRegex().find(line)
            val size = """([0-9]+) """.toRegex().find(line)
            if (cd?.groupValues != null && cd.groupValues.isNotEmpty()) {
                val value = cd.groupValues[1]
                println("cd $value")
                printCurrentPath(current)
                println("/$value")
                if (value == "..") {
                    current = current.top!!
                } else {
                    val next = current.folders.find{folder -> folder.name == value} ?: Dir(value, current)
                    current.folders.add(next)
                    current = next
                }
            } else if (size?.groupValues != null && size.groupValues.isNotEmpty()) {
                val value = size.groupValues[1].toLong()
                current.size += value
            }
        }
        val small = mutableListOf<Dir>()
        smallSizes(root, 1, small)
        return small.sumOf { it.size }
   }

    private fun printCurrentPath(current: Dir) {
        if (current.top != null) {
            printCurrentPath(current.top)
        }
        if (current.top == null) {
            print("${current.name}")
        } else {
            print("${current.name}/")
        }
    }

    private fun size(dir: Dir, depth: Int, sizes: MutableList<Dir>): Long {
        val size = dir.folders.sumOf { size(it, depth + 1, sizes) }
        val total = size + dir.size
        sizes.add(Dir(dir.name, null, total))
        for (i in 0 until depth) {
            print("\t")
        }
        println("- ${dir.name} (dir=${dir.size}, size=${total}")
        return total
    }

    override fun solve2(lines: List<String>): Long {
            val root = Dir("/", null)
        var current = root
        for (line in lines.subList(1, lines.size)) {
            val cd = """\$ cd ([\w/.]+)""".toRegex().find(line)
            val size = """([0-9]+) """.toRegex().find(line)
            if (cd?.groupValues != null && cd.groupValues.isNotEmpty()) {
                val value = cd.groupValues[1]
                println("cd $value")
                printCurrentPath(current)
                println("$value")
                if (value == "..") {
                    current = current.top!!
                } else {
                    val next = current.folders.find{folder -> folder.name == value} ?: Dir(value, current)
                    current.folders.add(next)
                    current = next
                }
            } else if (size?.groupValues != null && size.groupValues.isNotEmpty()) {
                val value = size.groupValues[1].toLong()
                current.size += value
            }
        }
        val sizes = mutableListOf<Dir>()
        val total = size(root, 1, sizes)
        val free = 70000000 - total
        val required = 30000000 - free
        println("total=$total, free=$free, required=$required")
        return sizes.filter { it.size >= required }.minOf { it.size }}
}