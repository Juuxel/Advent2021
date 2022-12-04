package juuxel.advent2021.kotlin

private fun gammaRate(numbers: List<Int>): Int {
    var gammaRate = 0

    for (i in 0 until 16) {
        val bitFlag = 1 shl i
        val oneCount = numbers.count { (it and bitFlag) != 0 }
        // oneCount > numbers.size - oneCount is equivalent
        if (2 * oneCount > numbers.size) {
            gammaRate = gammaRate or bitFlag
        }
    }

    return gammaRate
}

fun part1(lines: Array<String>) {
    val bitWidth = lines.first().length
    val numbers = lines.map { it.toInt(radix = 2) }
    val gammaRate = gammaRate(numbers)
    val epsilonRate = gammaRate.inv() and ((1 shl bitWidth) - 1)
    println(gammaRate * epsilonRate)
}

fun part2(lines: Array<String>) {
    val bitWidth = lines.first().length
    val numbers = lines.map { it.toInt(radix = 2) }
    val oxygenRating = findRating(ArrayList(numbers), bitWidth, true)
    val co2Rating = findRating(ArrayList(numbers), bitWidth, false)
    println(oxygenRating * co2Rating)
}

private fun findRating(numbers: MutableList<Int>, bitWidth: Int, oxygen: Boolean): Int {
    for (i in 1..bitWidth) {
        val mask = 1 shl (bitWidth - i)
        val oneCount = numbers.count { (it and mask) != 0 }
        val zeroCount = numbers.size - oneCount

        val mostCommonBitCriteria = when {
            zeroCount > oneCount -> 0
            oneCount > zeroCount -> 1
            else -> 1
        }
        val removalCriteria = if (oxygen) 1 - mostCommonBitCriteria else mostCommonBitCriteria
        val removalBit = removalCriteria shl (bitWidth - i)
        numbers.removeAll { (it and mask) == removalBit }

        if (numbers.size == 1) {
            return numbers.single()
        }
    }

    throw AssertionError("Could not find rating (oxygen: $oxygen)")
}
