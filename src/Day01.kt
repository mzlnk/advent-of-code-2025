import kotlin.math.abs

fun main() {

    fun extract(line: String): Pair<Char, Int> = Pair(line.first(), line.drop(1).toInt())

    fun part1(input: List<String>): Int {
        var result = 0

        var current = 50
        for (line in input) {
            val (direction, amount) = extract(line)

            when (direction) {
                'L' -> current = (current - amount) % 100
                'R' -> current = (current + amount) % 100
            }

            if (current == 0) result++
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0

        /** Returns number of hundreds in given number **/
        fun Int.hundreds(): Int = abs(this / 100)

        var current = 50
        for (line in input) {
            val (direction, amount) = extract(line)

            val new = when (direction) {
                'L' -> current - amount
                'R' -> current + amount
                else -> error("Should not happen")
            }

            result += abs(new.hundreds() - current.hundreds())
            if(new == 0) result++
            if(current * new < 0) result++

            current = new % 100
        }
        return result
    }

    val testInput = readInputAsList("Day01_test")
    with(part1(testInput)) { check(this == 3) { "Got instead: $this" } }
    with(part2(testInput)) { check(this == 6) { "Got instead: $this" } }

    // Read the input from the `src/Day01.txt` file.
    val input = readInputAsList("Day01")
    part1(input).println()
    part2(input).println()
}
