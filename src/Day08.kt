import kotlin.math.pow
import kotlin.math.sqrt

fun main() {

    infix fun <T> List<T>.cartesian(other: List<T>): List<Pair<T, T>> =
        this.flatMap { a -> other.map { b -> Pair(a, b) } }

    data class JunctionBox(val id: Int, val x: Int, val y: Int, val z: Int) {

        override fun toString(): String = id.toString()

        fun distance(other: JunctionBox): Double {
            return sqrt(
                (1.0 * other.x - this.x).pow(2) + (1.0 * other.y - this.y).pow(2) + (1.0 * other.z - this.z).pow(2)
            )
        }

    }

    fun MutableList<MutableSet<JunctionBox>>.merged(): MutableList<MutableSet<JunctionBox>> {
        val merged: MutableList<MutableSet<JunctionBox>> = mutableListOf()

        for(c in this) {
            val existingIdxInMerged = merged.mapIndexed { idx, elements -> idx to elements }
                .find { (_, elements) -> c.any { elements.contains(it) } }
                ?.let { (idx, _) -> idx }

            if(existingIdxInMerged == null) {
                merged.add(c)
            } else {
                merged[existingIdxInMerged].addAll(c)
            }
        }

        return merged
    }

    fun part1(input: String, amount: Int): Long {
        val junctionBoxes = input.lines().mapIndexed { idx, line ->
            val coords = line.split(",")
            JunctionBox(idx, coords[0].toInt(), coords[1].toInt(), coords[2].toInt())
        }

        val sortedConnections = (junctionBoxes cartesian junctionBoxes)
            .filterNot { (a, b) -> a.id >= b.id }
            .map { (a, b) -> Triple(a, b, a.distance(b)) }
            .sortedBy { (_, _, dist) -> dist }

        var circuits: MutableList<MutableSet<JunctionBox>> = mutableListOf()
        for (i in 0..<amount) {
            val (a, b, _) = sortedConnections[i]

            val currentConnections = circuits.firstOrNull { it.contains(a) || it.contains(b) }

            when {
                currentConnections == null -> circuits.add(mutableSetOf(a, b))
                currentConnections.contains(a) -> currentConnections.add(b)
                currentConnections.contains(b) -> currentConnections.add(a)
            }

            circuits = circuits.merged()
        }

        val sortedCircuits = circuits.sortedByDescending { it.size }
        return sortedCircuits[0].size.toLong() * sortedCircuits[1].size.toLong() * sortedCircuits[2].size.toLong()
    }

    fun part2(input: String): Long {
        val junctionBoxes = input.lines().mapIndexed { idx, line ->
            val coords = line.split(",")
            JunctionBox(idx, coords[0].toInt(), coords[1].toInt(), coords[2].toInt())
        }

        val sortedConnections = (junctionBoxes cartesian junctionBoxes)
            .filterNot { (a, b) -> a.id >= b.id }
            .map { (a, b) -> Triple(a, b, a.distance(b)) }
            .sortedBy { (_, _, dist) -> dist }

        var circuits: MutableList<MutableSet<JunctionBox>> = mutableListOf()
        var i = 0
        do {
            val (a, b, _) = sortedConnections[i]

            val currentConnections = circuits.firstOrNull { it.contains(a) || it.contains(b) }

            when {
                currentConnections == null -> circuits.add(mutableSetOf(a, b))
                currentConnections.contains(a) -> currentConnections.add(b)
                currentConnections.contains(b) -> currentConnections.add(a)
            }

            circuits = circuits.merged()
            i++
        } while(!(circuits.size == 1 && circuits[0].size == junctionBoxes.size))

        val (a, b, _) = sortedConnections[i - 1]

        return a.x.toLong() * b.x.toLong()
    }

    val testInput = readInputAsString("Day08_test")
    with(part1(testInput, 10)) { check(this == 40L) { "Got instead: $this" } }
    with(part2(testInput)) { check(this == 25272L) { "Got instead: $this" } }

    val input = readInputAsString("Day08")
    part1(input, 1000).println()
    part2(input).println()
}
