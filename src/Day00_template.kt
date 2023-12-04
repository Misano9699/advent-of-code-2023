fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day00_test")
    val input = readInput("input/Day00")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 0)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 0)
    println("Answer part2: " + part2(input))
}
