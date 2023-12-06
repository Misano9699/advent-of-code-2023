fun main() {

    fun part1(input: List<String>): Int {
        val milliseconds = input[0].substringAfter(":").trim().split(WHITESPACE)
        val distance = input[1].substringAfter(":").trim().split(WHITESPACE)
        return milliseconds.indices.map {
            Pair(milliseconds[it].toInt(), distance[it].toInt())
        }.map { pair ->
            (0..pair.first).map { millisecond ->
                (pair.first - millisecond) * millisecond
            }.count { distance ->
                distance > pair.second
            }
        }.reduce { acc, count -> acc * count }
    }

    fun part2(input: List<String>): Int {
        val milliseconds = input[0].substringAfter(":").replace(WHITESPACE, "").toLong()
        val distance = input[1].substringAfter(":").replace(WHITESPACE, "").toLong()
        return (0..milliseconds).map { millisecond ->
            (milliseconds - millisecond) * millisecond
        }.count { it > distance }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day06_test")
    val input = readInput("input/Day06")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 288)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 71503)
    println("Answer part2: " + part2(input))
}
