fun main() {

    fun List<String>.asGrid(): Array<IntArray> {
        val grid = Array(size) {
            IntArray(size)
        }

        forEachIndexed { row, line ->
            line.forEachIndexed { column, char ->
                grid[row][column] = char.digitToInt()
            }
        }

        return grid
    }

    fun Array<IntArray>.print() {
        forEach { println(it.joinToString()) }
    }

    fun part1(input: List<String>): Int {
        // simple solution is n^2 where n is total number of trees
        // should be able to work in passes, first from left, then top, then right, etc.
        val edgeSize = input.size
        val visible = Array(edgeSize) {
            IntArray(edgeSize) // 0 == not visible, 1 == visible
        }

        val grid = input.asGrid()
        var totalVisible = 0

        fun checkRowVisibility(progression: IntProgression) {
            for (i in 0 until edgeSize) {
                var maximum = -1
                for (j in progression) {
                    if (grid[i][j] > maximum) {
                        maximum = grid[i][j]
                        if (visible[i][j] == 0) {
                            visible[i][j] = 1
                            totalVisible++
                        }
                    }
                }
            }
        }

        fun checkColumnVisibility(progression: IntProgression) {
            for (i in 0 until edgeSize) {
                var maximum = -1
                for (j in progression) {
                    if (grid[j][i] > maximum) {
                        maximum = grid[j][i]
                        if (visible[j][i] == 0) {
                            visible[j][i] = 1
                            totalVisible++
                        }
                    }
                }
            }
        }

        checkRowVisibility(0..edgeSize - 1)
        checkRowVisibility(edgeSize - 1 downTo 0)
        checkColumnVisibility(0..edgeSize - 1)
        checkColumnVisibility(edgeSize - 1 downTo 0)

        return totalVisible
    }

    fun part2(input: List<String>): Int {

        val edgeSize = input.size
        val visibility = Array(edgeSize) {
            IntArray(edgeSize) { 1 }
        }

        val grid = input.asGrid()

        fun computeVisibleDistances(treeHeights: Iterable<Int>): Iterable<Int> {
            val treeHeightIndexes = MutableList(10) { 0 } // ignore 0 index

            return treeHeights.mapIndexed { index, treeHeight ->
                val closestBlockingTreeIndex = treeHeightIndexes.subList(treeHeight, 10).max()
                val visibleDistance = (index - closestBlockingTreeIndex).coerceAtLeast(1)
                treeHeightIndexes[treeHeight] = index
                if (index == 0) 0 else visibleDistance // ignore edge trees
            }
        }

        fun processRowLeftToRight(row: Int) {
            val treeHeights = grid[row]
            computeVisibleDistances(treeHeights.asIterable())
                .forEachIndexed { index, visibleDistance ->
                    visibility[row][index] *= visibleDistance
                }
        }

        fun processRowRightToLeft(row: Int) {
            val treeHeights = grid[row].asIterable().reversed()
            computeVisibleDistances(treeHeights)
                .forEachIndexed { index, visibleDistance ->
                    visibility[row][edgeSize - index - 1] *= visibleDistance
                }
        }

        fun processColumnTopToBottom(column: Int) {
            val treeHeights = grid.map { it.get(column) }
            computeVisibleDistances(treeHeights)
                .forEachIndexed { index, visibleDistance ->
                    visibility[index][column] *= visibleDistance
                }
        }

        fun processColumnBottomToTop(column: Int) {
            val treeHeights = grid.map { it.get(column) }.reversed()
            computeVisibleDistances(treeHeights)
                .forEachIndexed { index, visibleDistance ->
                    visibility[edgeSize - index - 1][column] *= visibleDistance
                }
        }

        for (i in 0 until edgeSize) {
            processRowLeftToRight(i)
            processRowRightToLeft(i)
            processColumnTopToBottom(i)
            processColumnBottomToTop(i)
        }

        return visibility.maxOf { it.max() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
