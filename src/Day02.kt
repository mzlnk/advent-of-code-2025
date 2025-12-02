fun main() {

    data class Range(val from: Long, val to: Long)

    fun String.asRange(): Range = this.split("-").let { Range(it[0].toLong(), it[1].toLong()) }

    fun part1(input: String): Long {

        fun isInvalidID(id: Long): Boolean {
            val str = id.toString()

            if (str.length % 2 != 0) return false

            return (0..<str.length / 2).all { str[it] == str[str.length / 2 + it] }
        }

        val ranges = input.split(",").map { it.asRange() }

        return ranges
            .flatMap { it.from..it.to }
            .filter { isInvalidID(it) }
            .sum()
    }

    fun part2(input: String): Long {
        fun isInvalidID(id: Long): Boolean {
            val str = id.toString()

            fun String.hasRepeatingSequence(seqLength: Int): Boolean {
                if(this.length % seqLength != 0) return false

                return (0..seqLength).all { first ->
                    (first..<this.length step seqLength).all { next ->
                        str[first] == str[next]
                    }
                }
            }

            return (1..(str.length / 2)).any { str.hasRepeatingSequence(it) }
        }

        val ranges = input.split(",").map { it.asRange() }

        return ranges
            .flatMap { it.from..it.to }
            .filter { isInvalidID(it) }
            .sum()
    }

    val testInput = readInputAsString("Day02_test")
    with(part1(testInput)) { check(this == 1227775554L) { "Got instead: $this" } }
    with(part2(testInput)) { check(this == 4174379265L) { "Got instead: $this" } }

    // Read the input from the `src/Day01.txt` file.
    val input = readInputAsString("Day02")
    part1(input).println()
    part2(input).println()
}
