fun main() {

    val digits = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)

    fun part1(input: List<String>): Int {
        return input.sumOf {
            ("" + it.find { c -> c.isDigit() } + it.findLast { c -> c.isDigit() }).toInt()
        }
    }

    fun findFirstDigit(it: String): String {
        var first = Pair(Int.MAX_VALUE, "")
        digits.forEach { (t, u) ->
            var i = it.indexOf(t)
            if (i > -1 && i < first.first) {
                first = Pair(i, "$u")
            }
            i = it.indexOf("$u")
            if (i > -1 && i < first.first) {
                first = Pair(i, "$u")
            }
        }
        return first.second
    }

    fun findLastDigit(it: String): String {
        var last = Pair(-1, "")
        digits.forEach { (t, u) ->
            var i = it.lastIndexOf(t)
            if (i > last.first) {
                last = Pair(i, "$u")
            }
            i = it.lastIndexOf("$u")
            if (i > last.first) {
                last = Pair(i, "$u")
            }
        }
        return last.second
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            ("" + findFirstDigit(it) + findLastDigit(it)).toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("input/Day01_test_1")
    val input = readInput("input/Day01")

    println("---- PART 1 ----")
    check(part1(testInput1).also { println("Answer test input part1: $it") } == 142)
    println("Answer part1: " + part1(input))

    val testInput2 = readInput("input/Day01_test_2")
    println("---- PART 2 ----")
    check(part2(testInput2).also { println("Answer test input part2: $it") } == 281)
    println("Answer part2: " + part2(input))
}