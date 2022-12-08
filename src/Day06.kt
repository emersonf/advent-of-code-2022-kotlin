fun main() {
    fun findMarkers(input: List<String>, windowSize: Int): List<Int> =
        input.map { line ->
            var windows = 0
            line.windowedSequence(windowSize) { window ->
                windows++
                window.toSet().size
            }
                .first { uniqueSize -> uniqueSize == windowSize }

            windowSize + windows - 1;
        }

    fun part1(input: List<String>): List<Int> =
        findMarkers(input, windowSize = 4)

    fun part2(input: List<String>): List<Int> =
        findMarkers(input, windowSize = 14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
