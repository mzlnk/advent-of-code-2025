fun main() {

    fun part1(input: List<String>): Long {
        var result: Long = 0

        for (bank in input) {
            val batteries = bank.map { it.digitToInt() }

            var firstIdx = 0
            for(i in 0..<batteries.lastIndex) {
                if(batteries[i] > batteries[firstIdx]) firstIdx = i
            }

            var secondIdx = firstIdx + 1
            for(i in (firstIdx + 1)..batteries.lastIndex) {
                if(batteries[i] > batteries[secondIdx]) secondIdx = i
            }

            result += batteries[firstIdx] * 10 + batteries[secondIdx]
        }

        return result
    }

    fun part2(input: List<String>): Long {
        var result: Long = 0

        for (bank in input) {
            val batteries = bank.map { it.digitToInt() }

            val digitsIdxs = Array(12) { 0 }

            for(i in 0..digitsIdxs.lastIndex) {
                digitsIdxs[i] = if(i > 0) digitsIdxs[i - 1] + 1 else 0

                for(j in digitsIdxs[i]..(batteries.lastIndex - 12 + i + 1)) {
                    if(batteries[j] > batteries[digitsIdxs[i]]) digitsIdxs[i] = j
                }
            }

            var base: Long = 1
            var joltage: Long = 0
            for(i in digitsIdxs.lastIndex downTo 0) {
                joltage += batteries[digitsIdxs[i]] * base
                base *= 10
            }
            result += joltage
        }

        return result
    }

    val testInput = readInputAsList("Day03_test")
    with(part1(testInput)) { check(this == 357L) { "Got instead: $this" } }
    with(part2(testInput)) { check(this == 3121910778619L) { "Got instead: $this" } }

    val input = readInputAsList("Day03")
    part1(input).println()
    part2(input).println()
}
