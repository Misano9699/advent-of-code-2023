import kotlin.math.pow

fun main() {

    fun determinePlays(input: List<String>): MutableList<Triple<Int,Int,Int>> {
        return input.map { it ->
            val cards = it.split(": ")
            val id = cards[0].split(WHITESPACE)[1].toInt()
            val play = cards[1].trim().split(" | ")
            val winninNumbers = play[0].trim().split(WHITESPACE)
            val numbers = play[1].trim().split(WHITESPACE)
            val wins = numbers.map {
                when (winninNumbers.contains(it)) {
                    true -> 1
                    false -> 0
                }
            }.reduce { acc, number ->
                when (number == 1) {
                    true -> acc + 1
                    false -> acc
                }
            }
            Triple(id, 1, wins)
        }.toMutableList()
    }

    fun determinePoints(play : Triple<Int, Int, Int>) : Int {
        return when {
            play.third > 0 -> 2.toDouble().pow(play.third - 1).toInt()
            else -> 0
        }
    }

    fun part1(input: List<String>): Int {
        val plays = determinePlays(input)
        return plays.sumOf {
            determinePoints(it)
        }
    }

    fun determineCards(plays: MutableList<Triple<Int, Int, Int>>) : Int {
        var i = 0
        while (i < plays.size) {
            val numberOfWins = plays[i].third
            (i + 1 .. i + numberOfWins).forEach {
                plays[it] = Triple(it, plays[it].second + plays[i].second, plays[it].third)
            }
            i++
        }
        return plays.sumOf {
            it.second
        }
    }

    fun part2(input: List<String>): Int {
        val plays = determinePlays(input)
        return determineCards(plays)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day04_test")
    val input = readInput("input/Day04")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 13)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 30)
    println("Answer part2: " + part2(input))
}