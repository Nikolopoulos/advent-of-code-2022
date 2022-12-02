import Selection.Companion.fromLetter
import java.io.File

enum class Selection {
    ROCK, PAPER, SCISSORS, UNKNOWN;

    companion object {
        fun fromLetter(letter: Char): Selection =
            when (letter) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> {
                    println("error in parsing $letter")
                    UNKNOWN
                }
            }
    }
}

enum class Outcome {
    WIN, LOSE, DRAW, UNKNOWN;

    companion object {
        fun toPoints(round: Pair<Selection, Selection>): Int {
            var points = getSelectionPoints(round.second)
            points += getOutcomePoints(roundOutcome(round))
            return points
        }

        fun toSelections(round: Pair<Selection, Outcome>): Pair<Selection, Selection> =
            Pair(round.first, toSelection(round))

        private fun toSelection(round: Pair<Selection, Outcome>): Selection =
            when (round.first) {
                Selection.PAPER -> {
                    when (round.second) {
                        WIN -> Selection.SCISSORS
                        LOSE -> Selection.ROCK
                        DRAW -> Selection.PAPER
                        UNKNOWN -> Selection.UNKNOWN
                    }
                }

                Selection.ROCK -> {
                    when (round.second) {
                        WIN -> Selection.PAPER
                        LOSE -> Selection.SCISSORS
                        DRAW -> Selection.ROCK
                        UNKNOWN -> Selection.UNKNOWN
                    }
                }

                Selection.SCISSORS -> {
                    when (round.second) {
                        WIN -> Selection.ROCK
                        LOSE -> Selection.PAPER
                        DRAW -> Selection.SCISSORS
                        UNKNOWN -> Selection.UNKNOWN
                    }
                }

                else -> {
                    Selection.UNKNOWN
                }
            }



        fun fromLetter(letter: Char): Outcome =
            when (letter) {
                'X' -> LOSE
                'Y' -> DRAW
                'Z' -> WIN
                else -> {
                    println("error in parsing $letter")
                    UNKNOWN
                }
            }

        private fun getSelectionPoints(selection: Selection): Int =
            when (selection) {
                Selection.ROCK -> 1
                Selection.PAPER -> 2
                Selection.SCISSORS -> 3
                Selection.UNKNOWN -> {
                    println("error in parsing $selection")
                    -1
                }
            }

        private fun getOutcomePoints(outcome: Outcome): Int =
            when (outcome) {
                WIN -> 6
                LOSE -> 0
                DRAW -> 3
                UNKNOWN -> {
                    println("error in parsing $outcome")
                    -1
                }
            }

        private fun roundOutcome(round: Pair<Selection, Selection>): Outcome =
            when (round.second) {
                Selection.PAPER -> when (round.first) {
                    Selection.ROCK -> WIN
                    Selection.SCISSORS -> LOSE
                    Selection.UNKNOWN -> UNKNOWN
                    else -> {
                        DRAW
                    }
                }

                Selection.ROCK -> when (round.first) {
                    Selection.SCISSORS -> WIN
                    Selection.PAPER -> LOSE
                    Selection.UNKNOWN -> UNKNOWN
                    else -> {
                        DRAW
                    }
                }

                Selection.SCISSORS -> when (round.first) {
                    Selection.PAPER -> WIN
                    Selection.ROCK -> LOSE
                    Selection.UNKNOWN -> UNKNOWN
                    else -> {
                        DRAW
                    }
                }

                else -> {
                    UNKNOWN
                }
            }

    }
}

fun loadInputPart1(): List<Pair<Selection, Selection>> =
    File("day2input.txt")
        .readLines()
        .map { line -> Pair(fromLetter(line.toCharArray()[0]), fromLetter(line.toCharArray()[2])) }
        .toList()

fun loadInputPart2(): List<Pair<Selection, Selection>> =
    File("day2input.txt")
        .readLines()
        .map { line -> Pair(fromLetter(line.toCharArray()[0]), Outcome.fromLetter(line.toCharArray()[2])) }
        .map {round -> Outcome.toSelections(round)}
        .toList()

fun part1() {
    println(loadInputPart1().sumOf { round -> Outcome.toPoints(round) })
}

fun part2() {
    println(loadInputPart2().sumOf { round -> Outcome.toPoints(round) })
}

fun main() {
    part1()
    part2()
}
