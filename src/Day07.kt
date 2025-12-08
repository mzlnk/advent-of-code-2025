fun main() {

    fun part1(input: String): Int {
        val lines = input.lines()
        val sIdx = lines[0].indexOfFirst { it == 'S' }

        val splitters = lines.drop(1).filterIndexed { idx, _ -> idx % 2 == 1 }

        var splits = 0
        var currentBeams = Array(lines[0].length) { false }
        currentBeams[sIdx] = true

        for(splitter in splitters) {
            val newBeams = Array(currentBeams.size) { false }
            for(beamIdx in currentBeams.indices) {
                if(currentBeams[beamIdx] && splitter[beamIdx] == '^') {
                    newBeams[beamIdx - 1] = true
                    newBeams[beamIdx + 1] = true
                    splits++
                } else if(currentBeams[beamIdx]) {
                    newBeams[beamIdx] = true
                }
            }
            currentBeams = newBeams
        }

        return splits
    }

    fun part2(input: String): Long {
        val lines = input.lines()
        val sIdx = lines[0].indexOfFirst { it == 'S' }

        val splitters = lines.drop(1).filterIndexed { idx, _ -> idx % 2 == 1 }

        val memo: MutableMap<Pair<Int, Int>, Long> = mutableMapOf()
        fun timelines(sIdx: Int, level: Int, splitters: List<String>): Long {
            if(splitters.isEmpty()) return 1L

            val splitter = splitters.first()
            val remainingSplitters = splitters.drop(1)

            return if(splitter[sIdx] == '^') {
                if(memo.containsKey(sIdx to level)) {
                    return memo[sIdx to level]!!
                }
                val result = timelines(sIdx - 1, level + 1, remainingSplitters) + timelines(sIdx + 1, level + 1, remainingSplitters)
                memo[sIdx to level] = result
                result
            } else {
                timelines(sIdx, level + 1, remainingSplitters)
            }
        }

        return timelines(sIdx, 0, splitters)
    }

    val testInput = readInputAsString("Day07_test")
    with(part1(testInput)) { check(this == 21) { "Got instead: $this" } }
    with(part2(testInput)) { check(this == 40L) { "Got instead: $this" } }

    val input = readInputAsString("Day07")
    part1(input).println()
    part2(input).println()
}
