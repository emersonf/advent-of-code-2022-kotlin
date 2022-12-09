fun main() {
    data class PathSizeEntry(val path: String, val size: Int)

    fun List<String>.parseInput(): List<PathSizeEntry> {
        val path = ArrayDeque<String>(50)
        path.add("root")

        return flatMap { line ->
            if (line.first() !in '0'..'9') {
                when {
                    line == "$ cd /" -> { path.clear(); path.add("root") }
                    line == "$ cd .." -> path.removeLast()
                    line.startsWith("$ cd ") -> path.addLast(line.substringAfterLast(" "))
                }
                emptyList()
            } else {
                val (size, filename) = line.split(" ")
                path.indices
                    .map { index ->
                        val pathPrefix = path.subList(0, index + 1).joinToString(separator = "/", prefix = "/")
                        PathSizeEntry(pathPrefix, size.toInt())
                    }
            }
        }
    }

    fun getDirectorySizes(input: List<String>) = input.parseInput()
        .groupingBy { it.path }
        .aggregate { _, accumulator: Int?, element, _ ->
            if (accumulator == null) {
                element.size
            } else accumulator + element.size
        }
        .entries
        .map { entry -> PathSizeEntry(entry.key, entry.value ) }

    fun part1(input: List<String>): Int {
        return getDirectorySizes(input)
            .filter { it.size < 100000 }
            .sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val directorySizes = getDirectorySizes(input)
        val totalSize = directorySizes.first { it.path == "/root" }.size
        val unusedSize = 70000000 - totalSize
        val requiredUnusedSize = 30000000 - unusedSize

        return directorySizes
            .map { it.size }
            .filter { size -> size > requiredUnusedSize }
            .minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
