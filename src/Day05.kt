import java.util.*

data class Instruction(val count: Int, val from: Int, val to: Int)

typealias Stack = LinkedList<Char>

fun main() {
    fun readStacksAndInstructions(input: List<String>): Pair<Array<Stack>, List<Instruction>> {

        val (instructionLines, stackLines) = input.partition { line -> line.startsWith("m") }
        val instructions = instructionLines.asInstructions()
        val stacks = stackLines
            .dropLast(1) // blank line
            .asStacks()

        return Pair(stacks, instructions)
    }

    fun part1(input: List<String>): String {
        val (stacks, instructions) = readStacksAndInstructions(input)
        instructions
            .forEach { instruction ->
                repeat(instruction.count) {
                    stacks[instruction.to].push(stacks[instruction.from].pop())
                }
            }

        return stacks.map { it.first() }
            .joinToString(separator = "")
    }

    fun part2(input: List<String>): String {
        val (stacks, instructions) = readStacksAndInstructions(input)

        instructions
            .forEach { instruction ->
                val fromStack = stacks[instruction.from]
                val toStack = stacks[instruction.to]

                for (index in instruction.count downTo 1) {
                    toStack.push(fromStack[index - 1])
                }

                repeat(instruction.count) {
                    fromStack.pop()
                }
            }

        return stacks.map { it.first() }
            .joinToString(separator = "")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.asStacks(): Array<Stack> {
    val stackCount = last().substringAfterLast(" ").toInt()
    val stacks = Array(stackCount) {
        Stack()
    }

    for (lineIndex in size - 2 downTo 0) {
        val line = get(lineIndex)
        for (stack in 0 until stackCount) {
            val stackCharIndex = stack * 4 + 1
            if (stackCharIndex > line.length) {
                break
            }
            val stackChar = line[stackCharIndex]
            if (stackChar == ' ') {
                continue
            }
            stacks[stack].push(stackChar)
        }
    }
    return stacks
}

private fun List<String>.asInstructions(): List<Instruction> {
    val regex = """move (\d+) from (\d+) to (\d+)\n?""".toRegex()
    return mapNotNull { line ->
        regex.matchEntire(line)
            ?.groupValues
            ?.subList(1, 4)
            ?.map(String::toInt)
    }
        .map { numbers -> Instruction(numbers[0], numbers[1] - 1, numbers[2] - 1) }
}

private fun print(stacks: Array<Stack>) {
    for (index in stacks.indices) {
        println("${index + 1}: " + stacks.get(index).joinToString())
    }
}

