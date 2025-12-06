fun main() {

    data class Problem(
        val operation: String,
        val numbers: List<Long>
    ) {

        val value: Long
            get() = when (operation) {
                "+" -> numbers.reduce { a, b -> a + b }
                "*" -> numbers.reduce { a, b -> a * b }
                else -> error("Unknown operation")
            }

    }

    fun part1(input: String): Long {
        val regex = Regex("""( )+""")
        val sheet = input.lines().map { it.split(regex).filterNot { it.isBlank() } }

        val problems = mutableListOf<Problem>()
        for (i in 0..sheet[0].lastIndex) {
            val operation = sheet[sheet.lastIndex][i]
            val numbers = mutableListOf<Long>()
            for (j in 0..<sheet.lastIndex) {
                numbers.add(sheet[j][i].toLong())
            }
            problems.add(Problem(operation, numbers))
        }

        return problems.sumOf { it.value }
    }

    fun part2(input: String): Long {
        val (operations, sheet) = run {
            val operations = input.lines().last().split(Regex(" +"))
            val width = operations.size
            val height = input.lines().size - 1

            val sheet = Array(height) { Array(width) { "" } }

            val operationsLine = input.lines().last()
            val sheetLines = input.lines().dropLast(1)
            for(i in 0..<height) {
                var numberStr = ""
                var numberIdx = 0
                for(j in 0..<sheetLines[i].length) {
                    if(j + 1 <= operationsLine.lastIndex && !operationsLine[j+1].isWhitespace()) {
                        sheet[i][numberIdx] = numberStr
                        numberIdx++
                        numberStr = ""
                    } else {
                        numberStr += sheetLines[i][j]
                    }
                }
                // last number:
                sheet[i][numberIdx] = numberStr
            }

            return@run Pair(operations, sheet)
        }

        val problems = mutableListOf<Problem>()
        for(i in 0..sheet[0].lastIndex) {
            val operation = operations[i]
            val numbers = mutableListOf<Long>()

            val max = run {
                var result = 0
                for(j in 0..sheet.lastIndex) {
                    if(sheet[j][i].length > result) result = sheet[j][i].length
                }
                return@run result
            }

            for(k in 0..<max) {
                var number = 0L
                for(j in 0..sheet.lastIndex) {
                    // last column does not have empty spaces at the end - therefore 1st condition
                    if(k > sheet[j][i].lastIndex || sheet[j][i][k] == ' ') continue
                    number = number * 10 + sheet[j][i][k].digitToInt()
                }
                numbers.add(number)
            }
            problems.add(Problem(operation, numbers))
        }

        return problems.sumOf { it.value }
    }

    val testInput = readInputAsString("Day06_test")
    with(part1(testInput)) { check(this == 4277556L) { "Got instead: $this" } }
    with(part2(testInput)) { check(this == 3263827L) { "Got instead: $this" } }

    val input = readInputAsString("Day06")
    part1(input).println()
    part2(input).println()
}
