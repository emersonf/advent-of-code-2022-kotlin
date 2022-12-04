@file:OptIn(ExperimentalStdlibApi::class)

fun main() {
    fun String.toIntRange(): IntRange {
        val (lowerBound, upperBound) = split("-")
        return lowerBound.toInt()..upperBound.toInt()
    }

    fun String.asRangePair(): Pair<IntRange, IntRange> {
        val (first, second) = split(",")
        return Pair(first.toIntRange(), second.toIntRange())
    }

    fun IntRange.contains(other: IntRange): Boolean =
        this.first <= other.first && this.last >= other.last

    fun IntRange.containsOrIsContainedBy(other: IntRange): Boolean =
        this.contains(other) || other.contains(this)

    fun part1(input: List<String>): Int =
        input
            .map { line -> line.asRangePair() }
            .count { pair -> pair.first.containsOrIsContainedBy(pair.second) }

    fun IntRange.overlaps(other: IntRange): Boolean =
        first in other || last in other || other.first in this || other.last in this

    fun part2(input: List<String>): Int =
        input
            .map { line -> line.asRangePair() }
            .count { pair -> pair.first.overlaps(pair.second) }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

