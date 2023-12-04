import kotlin.math.max
import kotlin.math.min

fun main() {

    fun createMatrix(input: List<String>): MutableList<MutableList<Char>> {
        val matrix: MutableList<MutableList<Char>> = MutableList(input.size) { mutableListOf() }
        input.indices.forEach { y ->
            input[y].map {
                matrix[y].add(it)
            }
        }
        return matrix
    }

    fun createListOfDigits(matrix: MutableList<MutableList<Char>>): MutableList<Pair<Int, Triple<Int, Int, Int>>> {
        val digits = mutableListOf<Pair<Int, Triple<Int, Int, Int>>>()
        var digit = Triple("", Int.MAX_VALUE, 0)
        matrix.indices.forEach { y ->
            if (digit.first != "") {
                digits.add(Pair(digit.first.toInt(), Triple(digit.second, digit.third, y)))
                digit = Triple("", Int.MAX_VALUE, 0)
            }
            matrix[y].indices.forEach { x ->
                if (matrix[y][x].isDigit()) {
                    digit = Triple(digit.first + matrix[y][x], min(digit.second, x), max(digit.third, x))
                } else {
                    if (digit.first != "") {
                        digits.add(Pair(digit.first.toInt(), Triple(digit.second, digit.third, y)))
                        digit = Triple("", Int.MAX_VALUE, 0)            }
                }
            }
        }
        return digits
    }

    fun containsSymbol(matrix: MutableList<MutableList<Char>>, pair: Pair<Int, Triple<Int, Int, Int>>): Boolean {
        val minX = max(0, pair.second.first - 1)
        val maxX = min(matrix[0].size - 1, pair.second.second + 1)
        val minY = max(0, pair.second.third - 1)
        val maxY = min(matrix.size - 1, pair.second.third + 1)
        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if (!matrix[y][x].isDigit() && matrix[y][x] != '.') {
                    return true
                }
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val matrix = createMatrix(input)
        val digits = createListOfDigits(matrix)
        return digits.filter { pair ->
            containsSymbol(matrix, pair)
        }.sumOf { pair ->
            pair.first
        }
    }

    fun createListOfStars(matrix: MutableList<MutableList<Char>>): List<Pair<Int, Int>> {
        val stars = mutableListOf<Pair<Int, Int>>()
        matrix.indices.forEach { y ->
            matrix[y].indices.forEach { x ->
                if (matrix[y][x] == '*')
                    stars.add(Pair(x, y))
            }
        }
        return stars
    }

    fun containsStar(triple: Triple<Int, Int, Int>, stars: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        val pairs = mutableListOf<Pair<Int, Int>>()
        val minX = max(0, triple.first - 1)
        val maxX = triple.second + 1
        val minY = max(0, triple.third - 1)
        val maxY = triple.third + 1
        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if (stars.contains(Pair(x, y))) {
                    pairs.add(Pair(x, y))
                }
            }
        }
        return pairs
    }

    fun createMapOfStarsWithDigits(digits: MutableList<Pair<Int, Triple<Int, Int, Int>>>, stars: List<Pair<Int, Int>>)
            : Map<Pair<Int, Int>, List<Int>> {
        val mapOfStars = mutableMapOf<Pair<Int, Int>, List<Int>>()
        digits.forEach { digit ->
            val star = containsStar(digit.second, stars)
            star.forEach {
                mapOfStars[it] = mapOfStars[it] ?: mutableListOf()
                mapOfStars[it] = mapOfStars[it]!!.plus(digit.first)
            }
        }
        return mapOfStars
    }

    fun part2(input: List<String>): Int {
        val matrix = createMatrix(input)
        val digits = createListOfDigits(matrix)
        val stars = createListOfStars(matrix)
        return createMapOfStarsWithDigits(digits, stars)
            .filter { map ->
                map.value.size == 2
            }.map { map ->
                map.value[0] * map.value[1]
            }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day03_test")
    val input = readInput("input/Day03")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 4361)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 467835)
    println("Answer part2: " + part2(input))
}