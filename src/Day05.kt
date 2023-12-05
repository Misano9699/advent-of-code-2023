fun main() {

    val seedsToSoil = mutableListOf<Triple<Long, Long, Long>>()
    val soilToFertilizer = mutableListOf<Triple<Long, Long, Long>>()
    val fertilizerToWater = mutableListOf<Triple<Long, Long, Long>>()
    val waterToLight = mutableListOf<Triple<Long, Long, Long>>()
    val lightToTemperature = mutableListOf<Triple<Long, Long, Long>>()
    val temepratureToHumidity = mutableListOf<Triple<Long, Long, Long>>()
    val humidityToLocation = mutableListOf<Triple<Long, Long, Long>>()

    fun reset() {
        seedsToSoil.clear()
        soilToFertilizer.clear()
        fertilizerToWater.clear()
        waterToLight.clear()
        lightToTemperature.clear()
        temepratureToHumidity.clear()
        humidityToLocation.clear()
    }

    fun fillMap(map: MutableList<Triple<Long, Long, Long>>, line: String) {
        val destination = line.substringBefore(" ").toLong()
        val source = line.substringAfter(" ").substringBefore(" ").toLong()
        val amount = line.substringAfter(" ").substringAfter(" ").toLong()
        map.add(Triple(source, destination, amount))
    }

    fun parseMap(input: List<String>, i: Int, map: MutableList<Triple<Long, Long, Long>>): Int {
        var index = i
        while (index < input.size && input[index] != "") {
            fillMap(map, input[index])
            index++
        }
        return index
    }

    fun parseInput(input: List<String>) {
        var index = 3
        index = parseMap(input, index, seedsToSoil)
        index = parseMap(input, index + 2, soilToFertilizer)
        index = parseMap(input, index + 2, fertilizerToWater)
        index = parseMap(input, index + 2, waterToLight)
        index = parseMap(input, index + 2, lightToTemperature)
        index = parseMap(input, index + 2, temepratureToHumidity)
        parseMap(input, index + 2, humidityToLocation)
    }

    fun getDestination(map: MutableList<Triple<Long, Long, Long>>, index: Long): Long {
        val result = map.firstOrNull {
            it.first <= index && index <= it.first + it.third - 1
        }

        return when (result) {
            null -> index
            else -> result.second + (index - result.first)
        }
    }

    fun getLocation(seed: Long): Long {
        var destination = getDestination(seedsToSoil, seed)
        destination = getDestination(soilToFertilizer, destination)
        destination = getDestination(fertilizerToWater, destination)
        destination = getDestination(waterToLight, destination)
        destination = getDestination(lightToTemperature, destination)
        destination = getDestination(temepratureToHumidity, destination)
        return getDestination(humidityToLocation, destination)
    }

    fun part1(input: List<String>): Long {
        reset()
        parseInput(input)
        val seeds = input[0].substringAfter(":").trim().split(" ").map { it.toLong() }
        return seeds.minOf { getLocation(it) }
    }

    fun part2(input: List<String>): Long {
        reset()
        parseInput(input)

        return input[0].substringAfter(":").trim().split(" ")
            .asSequence()
            .map { it.toLong() }
            .windowed(size = 2, step = 2)
            .map {
                Pair(it[0], it[1])
            }.map { pair ->
                pair.first.rangeUntil(pair.first + pair.second)
            }.minOf { seeds ->
                seeds.minOf { seed ->
                    getLocation(seed)
                }
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day05_test")
    val input = readInput("input/Day05")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 35L)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 46L)
    println("Answer part2: " + part2(input))
}
