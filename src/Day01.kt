fun main() {

    val digits = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)

    fun part1(input: List<String>): Int {
        return input.sumOf {
            ("" + it.find { c -> c.isDigit() } + it.findLast { c -> c.isDigit() }).toInt()
        }
    }

    fun findFirstDigit(it: String): String {
        var index = 999999
        var digit = ""
        digits.forEach { (t, u) ->
            var i = it.indexOf(t)
            if (i > -1 && i < index) {
                index = i
                digit = "$u"
            }
            i = it.indexOf("$u")
            if (i > -1 && i < index) {
                index = i
                digit = "$u"
            }
        }
        return digit
    }

    fun findLastDigit(it: String): String {
        var index = -1
        var digit = ""
        digits.forEach { (t, u) ->
            var i = it.lastIndexOf(t)
            if (i > index) {
                index = i
                digit = "$u"
            }
            i = it.lastIndexOf("$u")
            if (i > index) {
                index = i
                digit = "$u"
            }
        }
        return digit
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
