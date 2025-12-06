import kotlin.math.max

data class Database(
    val ranges: List<Range>,
    val ingeredients: List<Long>,
) {

    data class Range(val min: Long, val max: Long) {

        override fun toString(): String = "[$min, $max]"

    }

    companion object {

        fun fromString(str: String): Database {
            val ranges = str.split("\n")
                .takeWhile { it.isNotEmpty() }
                .map { it.split("-") }
                .map { Range(it[0].toLong(), it[1].toLong()) }
                .toList()

            val ingeredients = str.split("\n")
                .dropWhile { it.isNotEmpty() }
                .drop(1)
                .map { it.toLong() }
                .toList()

            return Database(ranges, ingeredients)
        }

    }

}

fun main() {

    fun part1(input: String): Int {
        val database = Database.fromString(input)

        fun Database.isInRange(ingredient: Long): Boolean =
            this.ranges.any { ingredient in it.min..it.max }

        return database.ingeredients.count { database.isInRange(it) }
    }

    fun part2(input: String): Long {
        val database = Database.fromString(input)

        val sortedRanges = database.ranges.sortedBy { it.min }

        val merged: MutableList<Database.Range> = mutableListOf()

        for(range in sortedRanges) {
//            println("range: $range")
            if(merged.size == 0) {
                merged.add(range)
                continue
            }

            val last = merged.last()
            if(range.min in last.min..last.max) {
                merged.removeLast()
                merged.add(Database.Range(last.min, max(last.max, range.max)))
            } else {
                merged.add(range)
            }

//            println("merged: $merged")
        }

        return merged.sumOf { it.max - it.min + 1}
    }

    val testInput = readInputAsString("Day05_test")
    with(part1(testInput)) { check(this == 3) { "Got instead: $this" } }
    with(part2(testInput)) { check(this == 14L) { "Got instead: $this" } }

    val input = readInputAsString("Day05")
    part1(input).println()
    part2(input).println()
}
