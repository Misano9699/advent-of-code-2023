import kotlin.math.max

fun main() {

    fun determineIdForGame(line: String, red: Int, green: Int, blue: Int): Int {
        val game = line.split(": ")
        val id = game[0].split(" ")[1].toInt()
        val rounds = game[1].split("; ")
        if (rounds.all { round ->
                round.split(", ").all {
                    val color = it.split(" ")
                    when (color[1]) {
                        "red" -> color[0].toInt() <= red
                        "green" -> color[0].toInt() <= green
                        "blue" -> color[0].toInt() <= blue
                        else -> false
                    }
                }
            }) {
            return id
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        return input.map {
            determineIdForGame(it, 12, 13, 14)
        }.filter { it > 0 }.sum()
    }

    fun powerOfMinRedGreenAndBlue(line: String) : Int {
        var red = 0
        var green = 0
        var blue = 0

        line.split(": ")[1].split("; ").forEach { colors ->
                colors.split(", ").forEach {
                    val color = it.split(" ")
                    when (color[1]) {
                        "red" -> red = max(red, color[0].toInt())
                        "green" -> green = max(green, color[0].toInt())
                        "blue" -> blue = max(blue, color[0].toInt())
                    }
                }
        }
        return red*green*blue
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            powerOfMinRedGreenAndBlue(it)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day02_test_1")
    val input = readInput("input/Day02")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 8)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 2286)
    println("Answer part2: " + part2(input))
}