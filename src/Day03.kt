typealias Item = Char

val Item.priority: Int
    get() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> throw IllegalStateException()
    }

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf {
            val (sack1, sack2) = it.chunked(it.length / 2)
            // n^2 to get this working for now, let's see what the next part brings
            var priority = 0
            outerLoop@ for (item1 in sack1) {
                for (item2 in sack2) {
                    if (item1 == item2) {
                        priority = item1.priority
                        break@outerLoop
                    }
                }
            }
            priority
        }

    fun part2(input: List<String>): Int =
        input.chunked(3)
            .sumOf { group ->
                group
                    .map { sack -> sack.toSet() }
                    .reduce { commonItems, nextSack -> commonItems.intersect(nextSack) }
                    .first()
                    .priority
            }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
