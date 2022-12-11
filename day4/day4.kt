import java.io.File

fun loadFileLines4(): List<String> = File("day4/day4input.txt").readLines()

fun getPairOfRanges(stringRep: String): Pair<IntRange, IntRange> = stringRep
        .split(",")
        .map { r ->
            r.split("-")
                    .map { e -> Integer.parseInt(e) }
        }.map { r -> IntRange(r[0], r[1]) }
        .let { it[0] to it[1] }

fun rangeIsFullyIncluded(value: Pair<IntRange, IntRange>): Boolean =
        value.first.contains(value.second.first) && value.first.contains(value.second.last)
                ||
                value.second.contains(value.first.first) && value.second.contains(value.first.last)

fun rangeIsPartlyIncluded(value: Pair<IntRange, IntRange>): Boolean =
        value.first.contains(value.second.first) || value.first.contains(value.second.last)
                ||
                value.second.contains(value.first.first) || value.second.contains(value.first.last)


fun day4Part1(): Number =
        loadFileLines4().map(::getPairOfRanges).map(::rangeIsFullyIncluded).count { e -> e }

fun day4Part2(): Number =
        loadFileLines4().map(::getPairOfRanges).map(::rangeIsPartlyIncluded).count { e -> e }

fun main() {
    day4Part1().let(::println)
    day4Part2().let(::println)
}