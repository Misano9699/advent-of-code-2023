fun main() {

    fun parseInput(input: List<String>): List<List<String>> {
        val result = mutableListOf<List<String>>()
        var current = mutableListOf<String>()
        input.forEach {
            if (it == "") {
                result.add(current)
                current = mutableListOf<String>()
            } else {
                current.add(it)
            }
        }
        if (current.isNotEmpty()) {
            result.add(current)
        }
        return result
    }

    fun determineMirror(grid: List<String>): Int {
        println(grid)
        var column = 1
        var mirrored = false
        while (!mirrored && column < grid[0].length) {
            mirrored = grid.all {
                val middle = it.length / 2
                if (column <= middle) {
                    it.substring(0, column) == it.substring(column, column + column).reversed()
                } else {
                    it.substring(column - (it.length - column) , column) == it.substring(column, it.length).reversed()
                }
            }
            column++
        }
        return if (mirrored) column-1 else 0
    }

    fun transpose(grid: List<String>): List<String> {
        val transposedGrid = Array(grid[0].length) { Array(grid.size) { '.' } }
        grid.indices.forEach { y ->
            grid[y].indices.forEach { x ->
                transposedGrid[x][y] = grid[y][x]
            }
        }
        return transposedGrid.map {
            it.joinToString("")
        }.toList()
    }

    fun part1(input: List<String>): Int {
        val grids = parseInput(input)
        println(grids)
        return grids.sumOf {grid ->
            determineMirror(grid).also { println(it) } + determineMirror(transpose(grid)).also { println(it) } * 100
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day13_test")
    val testInput1 = readInput("input/Day13_test_1")
    val input = readInput("input/Day13")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 405)
    check(part1(testInput1).also { println("Answer test input part1: $it") } == 1200)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part1: $it") } == 0)
    println("Answer part2: " + part2(input))
}