fun main() {

    val endNodes = listOf('.', 'S')
    val northSymbols = listOf('L', 'J', '|')
    val southSymbols = listOf('|', 'F', '7')
    val eastSymbols = listOf('-', 'L', 'F')
    val westSymbols = listOf('-', 'J', '7')
    val insideSymbols = listOf('|', '7', 'F', 'J', 'L')
    val insideEastSymbols = listOf('L', 'F')
    val insideWestSymbols = listOf('J', '7')

    fun north(x: Int, y: Int, maze: List<String>): Point? {
        val nexty = y - 1
        val symbol = if (nexty >= 0) maze[nexty][x] else null
        return if (symbol != null && (endNodes + southSymbols).contains(symbol)) Point(x, nexty) else null
    }

    fun south(x: Int, y: Int, maze: List<String>): Point? {
        val nexty = y + 1
        val symbol = if (nexty < maze.size) maze[nexty][x] else null
        return if (symbol != null && (endNodes + northSymbols).contains(symbol)) Point(x, nexty) else null
    }

    fun west(x: Int, y: Int, maze: List<String>): Point? {
        val nextx = x - 1
        val symbol = if (nextx >= 0) maze[y][nextx] else null
        return if (symbol != null && (endNodes + eastSymbols).contains(symbol)) Point(nextx, y) else null
    }

    fun east(x: Int, y: Int, maze: List<String>): Point? {
        val nextx = x + 1
        val symbol = if (nextx < maze[y].length) maze[y][nextx] else null
        return if (symbol != null && (endNodes + westSymbols).contains(symbol)) Point(nextx, y) else null
    }

    fun parseMaze(input: List<String>): Map<Point, List<Point>> {
        val destinationsMap = mutableMapOf<Point, List<Point>>()
        input.indices.map { y ->
            input[y].indices.map { x ->
                val destinations: List<Point?> = when (input[y][x]) {
                    '|' -> mutableListOf(north(x, y, input), south(x, y, input))
                    '-' -> mutableListOf(west(x, y, input), east(x, y, input))
                    'L' -> mutableListOf(north(x, y, input), east(x, y, input))
                    'J' -> mutableListOf(north(x, y, input), west(x, y, input))
                    '7' -> mutableListOf(south(x, y, input), west(x, y, input))
                    'F' -> mutableListOf(south(x, y, input), east(x, y, input))
                    else -> mutableListOf<Point>()
                }
                destinationsMap[Point(x, y)] = destinations.filterNotNull()
            }
        }
        return destinationsMap
    }

    fun determineSteps(maze: Map<Point, List<Point>>, startingPoint: Point): MutableList<Point> {
        val start = maze.entries.filter { it.value.contains(startingPoint) }.map { it.key }
        var node = start.first()
        var previous = startingPoint
        val steps = mutableListOf(node)
        while (node != startingPoint) {
            val previousnode = node
            node = maze[previousnode]!!.first()
            if (node == previous) node = maze[previousnode]!!.last()
            previous = previousnode
            steps.add(node)
        }
        return steps
    }

    fun detemineStartingPoint(maze: Map<Point, List<Point>>, input: List<String>) = maze.entries.filter {
        input[it.key.y][it.key.x] == 'S'
    }.map { it.key }.first()

    fun part1(input: List<String>): Int {
        val maze = parseMaze(input)
        val startingPoint = detemineStartingPoint(maze, input)
        return determineSteps(maze, startingPoint).count() / 2
    }

    fun determineStartingSymbol(steps: List<Point>, startingPoint: Point): Char =
        when {
            steps.contains(Point(startingPoint.x, startingPoint.y-1)) && steps.contains(Point(startingPoint.x, startingPoint.y+1)) -> '|'
            steps.contains(Point(startingPoint.x-1, startingPoint.y)) && steps.contains(Point(startingPoint.x+1, startingPoint.y)) -> '-'
            steps.contains(Point(startingPoint.x+1, startingPoint.y)) && steps.contains(Point(startingPoint.x, startingPoint.y-1)) -> 'L'
            steps.contains(Point(startingPoint.x-1, startingPoint.y)) && steps.contains(Point(startingPoint.x, startingPoint.y-1)) -> 'J'
            steps.contains(Point(startingPoint.x+1, startingPoint.y)) && steps.contains(Point(startingPoint.x, startingPoint.y+1)) -> 'F'
            steps.contains(Point(startingPoint.x-1, startingPoint.y)) && steps.contains(Point(startingPoint.x, startingPoint.y+1)) -> '7'
            else -> 'S'
        }

    fun part2(input: List<String>): Int {
        val maze = parseMaze(input)
        val startingPoint = detemineStartingPoint(maze, input)
        val steps = determineSteps(maze, startingPoint)
        val result = mutableListOf<Point>()
        val start = maze.entries.filter { it.value.contains(startingPoint) }.map { it.key }
        val startSymbol = determineStartingSymbol(start, startingPoint)
        println(startSymbol)
        val grid = input.map {
            it.replace('S', startSymbol)
        }
        input.indices.forEach { y ->
            var inside = false
            var insideSymbol: Char? = null
            grid[y].indices.forEach { x ->
                if (steps.contains(Point(x, y))) {
                    val currentSymbol : Char = grid[y][x]
                    if (insideSymbols.contains(currentSymbol)) {
                        when {
                            insideEastSymbols.contains(insideSymbol) -> if (insideWestSymbols.contains(currentSymbol)) {
                                inside = !inside
                                insideSymbol = null
                            }
                            insideWestSymbols.contains(insideSymbol) -> if (insideEastSymbols.contains(currentSymbol)) {
                                inside = !inside
                                insideSymbol = null
                            }
                            else -> {
                                if (!insideEastSymbols.contains(currentSymbol) && insideWestSymbols.contains(currentSymbol)) {
                                    inside = !inside
                                }
                                insideSymbol = currentSymbol
                            }
                        }
                    }
                } else {
                    if (inside) {
                        result.add(Point(x, y))
                    }
                }
            }
        }
        println(result)
        return result.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day10_test")
    val testInput1 = readInput("input/Day10_test_1")
    val input = readInput("input/Day10")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 4)
    check(part1(testInput1).also { println("Answer test input part1: $it") } == 8)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    val testInput2 = readInput("input/Day10_test_2")
    val testInput3 = readInput("input/Day10_test_3")
    val testInput4 = readInput("input/Day10_test_4")
    check(part2(testInput2).also { println("Answer test input part2: $it") } == 4)
    check(part2(testInput3).also { println("Answer test input part2: $it") } == 8)
    check(part2(testInput4).also { println("Answer test input part2: $it") } == 10)
    println("Answer part2: " + part2(input))
}
