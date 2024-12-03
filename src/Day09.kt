fun main() {


    fun parseInput(input: List<String>): List<List<Int>> =
        input.map { line ->
            line.split(WHITESPACE).map { it.toInt() }
        }

    fun determineExtrapolatedList(history: MutableList<Int>): MutableList<MutableList<Int>> {
        val extrapolatedList = mutableListOf<MutableList<Int>>()
        extrapolatedList.add(history)
        var count = 0
        println(extrapolatedList[count])
        while (!extrapolatedList[count].all { it == 0 }) {
            extrapolatedList.add(extrapolatedList[count]
                .windowed(2, 1)
                .map { (a, b) -> b - a }
                .toMutableList()
            )
            count++
            println(extrapolatedList[count])
        }
        println()
        return extrapolatedList
    }

    fun determineNextValue(history: MutableList<Int>): Int {
        val extrapolatedList = determineExtrapolatedList(history)
        extrapolatedList.indices.reversed().forEach {
            if (it > 0) {
                val currentList = extrapolatedList[it]
                extrapolatedList[it - 1].add(currentList.last() + extrapolatedList[it - 1].last())
                println(extrapolatedList[it-1])
            }
        }
        return extrapolatedList[0].last()
    }

    fun part1(input: List<String>): Int {
        val sensorInput = parseInput(input)
        return sensorInput.sumOf {
            determineNextValue(it.toMutableList())
        }
    }

    fun part2(input: List<String>): Int {
        val sensorInput = parseInput(input).map {
            it.reversed()
        }
        return sensorInput.sumOf {
            determineNextValue(it.toMutableList())
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day09_test")
    val input = readInput("input/Day09")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 114)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 2)
    println("Answer part2: " + part2(input))
}
