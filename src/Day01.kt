
fun main() {
    fun part1(input: List<String>): Int {
        var maxTotal = 0
        var currentTotal = 0
        for (calories in input) {
            if (calories.isBlank()) {
                if (currentTotal > maxTotal) {
                    maxTotal = currentTotal
                }
                currentTotal = 0
            } else {
                currentTotal += calories.toInt()
            }
        }

        return maxTotal
    }

    data class ElfCalories(val elfId: Int, val calories: Int)

    fun part2(input: List<String>): Int {
        var elfId = 1

        return input
            .map { calories ->
                if (calories.isBlank()) {
                    elfId++
                    ElfCalories(-1,0)
                }
                else {
                    ElfCalories(elfId, calories.toInt())
                }
            }
            .filter { it.elfId > 0 }
            .groupingBy { it.elfId }
            .aggregate { _, accumulator: Int?, element, _ ->
                if (accumulator == null) {
                    element.calories
                }
                else accumulator + element.calories
            }
            .entries
            .sortedByDescending { it.value }
            .take(3)
            .sumOf { it.value }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24_000)
    check(part2(testInput) == 45_000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
