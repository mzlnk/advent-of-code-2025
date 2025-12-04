fun main() {

    infix fun IntRange.cartesian(other: IntRange): List<Pair<Int, Int>> =
        this.flatMap { a -> other.map { b -> a to b } }

    fun part1(input: List<String>): Long {
        var result: Long = 0

        val map: Array<CharArray> = input
            .map { it.toCharArray() }
            .toTypedArray()

        val height = map.size
        val width = map[0].size

        for (i in map.indices) {
            for (j in map[i].indices) {
                var rolls = 0

                for ((di, dj) in ((-1..1) cartesian (-1..1))) {
                    if (
                        (i + di) in 0..<width &&
                        (j + dj) in 0..<height &&
                        !(di == 0 && dj == 0) &&
                        map[i + di][j + dj] == '@'
                    ) {
                        rolls++
                    }
                }

                if (map[i][j] == '@' && rolls < 4) result++
            }
        }

        return result
    }

    fun part2(input: List<String>): Long {
        fun evaluateRollsToRemove(map: Array<CharArray>): List<Pair<Int, Int>> {
            val rollsToRemove = mutableListOf<Pair<Int, Int>>()

            val height = map.size
            val width = map[0].size

            for (i in map.indices) {
                for (j in map[i].indices) {
                    var rolls = 0

                    for ((di, dj) in ((-1..1) cartesian (-1..1))) {
                        if (
                            (i + di) in 0..<width &&
                            (j + dj) in 0..<height &&
                            !(di == 0 && dj == 0) &&
                            map[i + di][j + dj] == '@'
                        ) {
                            rolls++
                        }
                    }

                    if (map[i][j] == '@' && rolls < 4) rollsToRemove.add(i to j)
                }
            }

            return rollsToRemove
        }

        fun Array<CharArray>.removeRolls(rolls: List<Pair<Int, Int>>) =
            rolls.forEach { (x, y) -> this[x][y] = '.' }

        val map: Array<CharArray> = input
            .map { it.toCharArray() }
            .toTypedArray()

        var result: Long = 0
        var rollsToRemove: List<Pair<Int, Int>>

        do {
            rollsToRemove = evaluateRollsToRemove(map)
            map.removeRolls(rollsToRemove)

            result += rollsToRemove.size
        } while (rollsToRemove.isNotEmpty())

        return result
    }

    val testInput = readInputAsList("Day04_test")
    with(part1(testInput)) { check(this == 13L) { "Got instead: $this" } }
    with(part2(testInput)) { check(this == 43L) { "Got instead: $this" } }

    val input = readInputAsList("Day04")
    part1(input).println()
    part2(input).println()
}
