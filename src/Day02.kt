typealias Round = Pair<Char, Char>

fun toRound(line: String): Round {
    val segments = line.split(" ")
    return segments[0].first() to segments[1].first()
}

fun Round.scoreWithSecondColumnAsHand(): Int {
    // A == X == Rock
    // B == Y == Paper
    // C == Z == Scissors
    return when {
        first == 'A' && second == 'X' -> 1 + 3
        first == 'A' && second == 'Y' -> 2 + 6
        first == 'A' && second == 'Z' -> 3
        first == 'B' && second == 'X' -> 1
        first == 'B' && second == 'Y' -> 2 + 3
        first == 'B' && second == 'Z' -> 3 + 6
        first == 'C' && second == 'X' -> 1 + 6
        first == 'C' && second == 'Y' -> 2
        first == 'C' && second == 'Z' -> 3 + 3
        else -> 0
    }
}

fun Round.scoreWithSecondColumnAsResult(): Int {
    // A == Rock == 1 points if played
    // B == Paper == 2 points if played
    // C == Scissors == 3 points if played
    // X == Lose
    // Y == Draw
    // Z == Win
    return when {
        first == 'A' && second == 'X' -> 3
        first == 'B' && second == 'X' -> 1
        first == 'C' && second == 'X' -> 2
        first == 'A' && second == 'Y' -> 1 + 3
        first == 'B' && second == 'Y' -> 2 + 3
        first == 'C' && second == 'Y' -> 3 + 3
        first == 'A' && second == 'Z' -> 2 + 6
        first == 'B' && second == 'Z' -> 3 + 6
        first == 'C' && second == 'Z' -> 1 + 6
        else -> 0
    }
}

fun main() {
    fun part1(input: List<String>): Int =
        input
            .map { line -> toRound(line) }
            .sumOf { it.scoreWithSecondColumnAsHand() }

    fun part2(input: List<String>): Int =
        input
            .map { line -> toRound(line) }
            .sumOf { it.scoreWithSecondColumnAsResult() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
