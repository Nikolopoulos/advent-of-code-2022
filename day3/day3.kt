import java.io.File

fun loadFileLines(): List<String> = File("day3input.txt").readLines()
fun linesToNumbers(): List<List<Int>> =
    loadFileLines().stream().map { inputString ->
        inputString
            .toCharArray()
            .map { c -> getNumberFromLetter(c) }
    }.toList()

fun getNumberFromLetter(value: Char): Int =
    if (value.code in 65..90) {
        value.code - 38
    } else {
        value.code - 96
    }

fun splitListInCompartments(value: List<Int>): Pair<List<Int>, List<Int>> =
    Pair(value.subList(0, value.size / 2), value.subList(value.size / 2, value.size))

fun findCommonElementInCompartments(value: Pair<List<Int>, List<Int>>): Int =
    value.first.find { num -> value.second.contains(num) } ?: 0

fun splitInGroups(value: List<List<Int>>): List<Triple<List<Int>, List<Int>, List<Int>>> {
    val iterator = value.iterator()
    val list = ArrayList<Triple<List<Int>, List<Int>, List<Int>>>()
    while (iterator.hasNext())
        list.add(Triple(iterator.next().sorted(), iterator.next().sorted(), iterator.next().sorted()))
    return list
}

fun findCommonElementInGroup(value: Triple<List<Int>, List<Int>, List<Int>>): Int {
    var index1 = 0
    var index2 = 0
    var index3 = 0

    while (
        (
                value.first[index1] != value.second[index2] ||
                        value.second[index2] != value.third[index3] ||
                        value.first[index1] != value.third[index3]
                ) &&
        index1 < value.first.size &&
        index2 < value.second.size &&
        index3 < value.third.size
    ) {
        when (minOf(value.first[index1], value.second[index2], value.third[index3])) {
            value.first[index1] -> {
                index1++
                continue
            }

            value.second[index2] -> {
                index2++
                continue
            }

            value.third[index3] -> {
                index3++
                continue
            }
        }
    }

    return if (value.first[index1] == value.second[index2] && value.second[index2] == value.third[index3]) {
        value.first[index1]
    } else {
        0
    }
}

fun day3Part1(): Int {
    return linesToNumbers().stream()
        .map { splitListInCompartments(it) }
        .map { findCommonElementInCompartments(it) }
        .toList().sum()
}

fun day3Part2(): Int =
    splitInGroups(linesToNumbers()).stream()
        .map { findCommonElementInGroup(it) }
        .toList()
        .sum()


fun main() {
    println(day3Part1())
    println(day3Part2())
}