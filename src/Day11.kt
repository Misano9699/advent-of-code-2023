import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    fun transpose(firstPass: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
        val secondPass = Array(firstPass[0].size) { Array(firstPass.size) { '.' } }
        firstPass.indices.forEach { y ->
            firstPass[y].indices.forEach { x ->
                secondPass[x][y] = firstPass[y][x]
            }
        }
        return secondPass.map {
            it.toMutableList()
        }.toMutableList()
    }

    fun determineGalaxies(grid: List<String>): List<Point> {
        val galaxies = mutableListOf<Point>()
        grid.indices.forEach { y ->
            grid[y].indices.forEach { x ->
                if (grid[y][x] == '#') galaxies.add(Point(x, y))
            }
        }
        return galaxies
    }

    fun detemineEmptyRows(input: List<String>): List<Int> {
        val rows = mutableListOf<Int>()
        input.indices.forEach { y ->
            if (!input[y].contains('#')) {
                rows.add(y)
            }
        }
        return rows
    }

    fun detemineEmptyColuns(input: List<String>): List<Int> {
        val transposedInput = transpose(input.map {
            it.toMutableList()
        }.toMutableList())
        val columns = mutableListOf<Int>()
        transposedInput.indices.forEach { y ->
            if (!transposedInput[y].contains('#')) {
                columns.add(y)
            }
        }
        return columns
    }

    fun determineDistance(point: Point, point1: Point, emptyRows: List<Int>, emptyColumns: List<Int>, multiplier: Long): BigInteger {
        val countColumns = emptyColumns.count { it > min(point.x, point1.x) && it < max(point.x, point1.x) }
        val countRows = emptyRows.count { it > min(point.y, point1.y) && it < max(point.y, point1.y) }
        return BigInteger.valueOf(abs(point.x - point1.x).toLong() + abs(point.y - point1.y).toLong())
            .plus(BigInteger.valueOf((countColumns + countRows) * (multiplier - 1)))
    }

    fun shortestPath(input: List<String>, multiplier: Long): Long {
        val emptyRows = detemineEmptyRows(input)
        val emptyColumns = detemineEmptyColuns(input)
        val galaxies = determineGalaxies(input)

        var sumOfShortestPaths = BigInteger.ZERO
        (0..<galaxies.size - 1).forEach { i ->
            (i + 1..<galaxies.size).forEach { j ->
                sumOfShortestPaths += determineDistance(galaxies[i], galaxies[j], emptyRows, emptyColumns, multiplier)
            }
        }
        return sumOfShortestPaths.toLong()
    }

    fun part1(input: List<String>): Long {
        return shortestPath(input, 2L)
    }

    fun part2a(input: List<String>): Long {
        return shortestPath(input, 10L)
    }

    fun part2b(input: List<String>): Long {
        return shortestPath(input, 100L)
    }

    fun part2(input: List<String>): Long {
        return shortestPath(input, 1_000_000L)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day11_test")
    val input = readInput("input/Day11")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 374L)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2a(testInput).also { println("Answer test input part2: $it") } == 1030L)
    check(part2b(testInput).also { println("Answer test input part2: $it") } == 8410L)
    println("Answer part2: " + part2(input))
}