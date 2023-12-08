import java.math.BigInteger

fun main() {

    fun parseInput(input: List<String>): Map<String, Pair<String, String>> {
        return (2..<input.size).associate { index ->
            val source = input[index].substringBefore(" = ")
            val destination = input[index].substringAfter("(").replace(")", "").split(", ")
            source to Pair(destination[0], destination[1])
        }
    }

    fun navigateMap(steps: String, map: Map<String, Pair<String, String>>, origins: List<String>): Long {
        return origins.map {
            var count = 0L
            var here = it
            while (!here.endsWith("Z")) {
                val direction = steps[count.toInt() % steps.length]
                here = when (direction) {
                    'L' -> map[here]!!.first
                    else -> map[here]!!.second
                }
                count++
            }
            BigInteger.valueOf(count)
        }.fold(BigInteger.ONE) { acc, count ->
            // use lcm to determine least common multiplier for all input
            val gcd = acc.gcd(count)
            val product = acc.multiply(count).abs()
            product.divide(gcd)
        }.toLong()
    }

    fun part1(input: List<String>): Long {
        val steps = input[0]
        val map = parseInput(input)
        return navigateMap(steps, map, listOf("AAA"))
    }

    fun part2(input: List<String>): Long {
        val steps = input[0]
        val map = parseInput(input)
        val origins = map.entries.map { entry -> entry.key }.filter {
            it.endsWith("A")
        }
        return navigateMap(steps, map, origins)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day08_test")
    val testInput1 = readInput("input/Day08_test_1")
    val testInput2 = readInput("input/Day08_test_2")
    val input = readInput("input/Day08")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 2L)
    check(part1(testInput1).also { println("Answer test input part1: $it") } == 6L)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput2).also { println("Answer test input part2: $it") } == 6L)
    println("Answer part2: " + part2(input))
}