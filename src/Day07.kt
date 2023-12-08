var JOKER = '0'

fun main() {

    fun parseInput(input: List<String>): List<Hand> {
        return input.map {
            Hand.of(it.substringBefore(" "), it.substringAfter(" ").toInt())
        }
    }

    fun part1(input: List<String>): Int {
        val hands = parseInput(input)
        val sortedHands = hands.sortedWith { hand1, hand2 -> hand1.compareTo(hand2) }
        return sortedHands.indices.sumOf { sortedHands[it].value * (it + 1) }
    }

    fun part2(input: List<String>): Int {
        JOKER = 'J'
        val hands = parseInput(input)
        val sortedHands = hands.sortedWith { hand1, hand2 -> hand1.compareTo(hand2) }
        return sortedHands.indices.sumOf { sortedHands[it].value * (it + 1) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day07_test")
    val input = readInput("input/Day07")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 6440)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 5905)
    println("Answer part2: " + part2(input))
}

class Hand(private val cards: CharArray, private val type: Type, val value: Int) {

    private fun compareCards(otherCards: CharArray): Int {
        var i = 0
        var difference = getDifference(cards[i], otherCards[i])
        while (difference == 0 && i < 5) {
            i++
            difference = getDifference(cards[i], otherCards[i])
        }
        return difference
    }

    fun compareTo(otherHand: Hand): Int {
        return when (val i = otherHand.type.ordinal - this.type.ordinal) {
            0 -> compareCards(otherHand.cards)
            else -> i
        }
    }

    companion object {
        fun of(cards: String, value: Int): Hand {
            val cardsArray = cards.toCharArray()
            val type = determineType(cardsArray)
            return Hand(cardsArray, type, value)
        }

        private fun determineType(cardsArray: CharArray): Type {
            val occurrencesMap = makeOccurenceMap(cardsArray)
            return if (cardsArray.contains(JOKER)) {
                determineType(swapJoker(occurrencesMap, cardsArray))
            } else {
                determineType(occurrencesMap)
            }
        }

        private fun determineType(occurrencesMap: MutableMap<Char, Int>): Type = when (occurrencesMap.size) {
            1 -> Type.FIVE_OF_A_KIND // no need to swap
            2 -> when (occurrencesMap.maxOf { it.value }) {
                4 -> Type.FOUR_OF_A_KIND
                else -> Type.FULL_HOUSE
            }

            3 -> when (occurrencesMap.maxOf { it.value }) {
                3 -> Type.THREE_OF_A_KIND
                else -> Type.TWO_PAIR
            }

            4 -> Type.ONE_PAIR
            else -> Type.HIGH_CARD
        }

        private fun swapJoker(occurrencesMap: MutableMap<Char, Int>, cardsArray: CharArray): MutableMap<Char, Int> =
            when (occurrencesMap.size) {
                1 -> occurrencesMap // no need to swap
                else -> makeOccurenceMap(cardsArray.joinToString("").replace(JOKER, highestCard(occurrencesMap)).toCharArray())
            }

        private fun highestCard(occurrencesMap: MutableMap<Char, Int>): Char {
            val listOfChars = occurrencesMap.map {
                Pair(it.value, it.key)
            }.sortedWith { pair1, pair2 ->
                when (val difference = pair2.first - pair1.first) {
                    0 -> getDifference(pair2.second, pair1.second)
                    else -> difference
                }
            }
            return if (listOfChars[0].second == JOKER && listOfChars.size > 1) {
                listOfChars[1].second
            } else {
                listOfChars[0].second
            }
        }

        private fun makeOccurenceMap(cardsArray: CharArray): MutableMap<Char, Int> {
            val occurrencesMap = mutableMapOf<Char, Int>()
            cardsArray.forEach {
                occurrencesMap.putIfAbsent(it, 0)
                occurrencesMap[it] = occurrencesMap[it]!! + 1
            }
            return occurrencesMap
        }

        private fun getDifference(card1: Char, card2: Char): Int =
            when (card1) {
                JOKER -> if (card2 == JOKER) 0 else -1
                else -> if (card2 == JOKER) 1 else (getOrdinal(card2) - getOrdinal(card1))
            }

        private fun getOrdinal(card: Char): Int = Card.of(card)!!.ordinal
    }

    override fun toString(): String {
        return "${cards.joinToString("")} - $type - $value"
    }
}


enum class Type {
    FIVE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    THREE_OF_A_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD
}

enum class Card(val card: Char) {
    ACE('A'),
    KING('K'),
    QUEEN('Q'),
    JACK('J'),
    TEN('T'),
    NINE('9'),
    EIGHT('8'),
    SEVEN('7'),
    SIX('6'),
    FIVE('5'),
    FOUR('4'),
    THREE('3'),
    TWO('2');

    companion object {
        fun of(s: Char): Card? =
            entries.find { it.card == s }
    }
}