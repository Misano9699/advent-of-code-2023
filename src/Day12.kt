import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    fun replaceFirstQuestionMark(springs: String, replacement: String) = springs.replaceFirst("?", replacement)

    fun springsMatchesConfiguration(springs: String, configuration: List<Int>): Boolean {
        val split = springs.replace(".", " ").trim().split(WHITESPACE)
        if (split.size == configuration.size) {
            return split.indices.all {
                split[it].length == configuration[it]
            }
        }
        return false
    }

    fun configure(springs: String, configuration: List<Int>): Long {
        var count = 0L
        when (springs.contains("?")) {
            true -> {
                count += configure(replaceFirstQuestionMark(springs, "."), configuration)
                count += configure(replaceFirstQuestionMark(springs, "#"), configuration)
            }

            false -> if (springsMatchesConfiguration(springs, configuration)) {
                count += 1L
            }
        }
        return count
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val springs = line.substringBefore(" ")
            val configuration = line.substringAfter(" ").split(",").map { it.toInt() }
            configure(springs, configuration)
        }
    }


    fun unfold(springs: String): String {
        return (1..5).map {
            "$springs?"
        }.joinToString("")
    }

    fun unfold(configuration: List<Int>): List<Int> {
        return configuration + configuration + configuration + configuration + configuration
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val springs = line.substringBefore(" ")
            val configuration = line.substringAfter(" ").split(",").map { it.toInt() }
            configure(springs, configuration).also {
                println(it)
            }
            configure("?" + springs, configuration).also {
                println(it)
            }
            configure(springs + "?" , configuration).also {
                println(it)
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day12_test")
    val input = readInput("input/Day12")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 21L)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part1: $it") } == 525152L)
    println("Answer part2: " + part2(input))
}