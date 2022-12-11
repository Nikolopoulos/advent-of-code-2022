import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class Stacks {
    private var stacksList: MutableList<Stack<Char>> = ArrayList()

    init {
        for (i in 0 until 9)
            stacksList.add(Stack<Char>())
    }

    fun peekAll(): String=
        stacksList.map { e -> e.peek() }.toString()


    fun moveOldModel(move: Move) {
        val src = stacksList[move.src]
        val dest = stacksList[move.dest]

        for (i in 0 until move.quantity)
            dest.push(src.pop())
    }

    fun moveNewModel(move: Move) {
        val src = stacksList[move.src]
        val dest = stacksList[move.dest]
        val tempRotStack = Stack<Char>()


        for (i in 0 until move.quantity){
            tempRotStack.push(src.pop())
        }

        for (i in 0 until move.quantity){
            dest.push(tempRotStack.pop())
        }
    }

    fun pushToStack(char: Char, _dest: Int) {
        stacksList[_dest].push(char)
    }

    companion object {
        fun fromLines(lines: List<String>): Stacks {
            val stacks = Stacks()
            lines.reversed()
                    .map { line ->
                        line.chunked(4)
                                .map { s -> s[1] }
                                .forEachIndexed { ind, c -> if (!c.isWhitespace()) stacks.pushToStack(c, ind) }
                    }
            return stacks
        }
    }
}

data class Move(val quantity: Int, val src: Int, val dest: Int) {
    companion object {
        fun fromString(line: String): Move =
                line
                        .split(" ")
                        .filter { s -> !s.any { c -> c.isLetter() } }
                        .map { s -> Integer.parseInt(s) }
                        .let { Move(it[0], it[1]-1, it[2]-1) }

    }
}


fun loadFileLines5(): List<String> = File("day5/day5input.txt").readLines()

fun splitStartingAndMoves(lines: List<String>): Pair<Stacks, List<Move>> =
        Stacks.fromLines(lines.subList(0, 8)) to lines.subList(10, lines.size).map { l -> Move.fromString(l) }.toList()

fun executeMovesOldModel(input: Pair<Stacks, List<Move>>): Pair<Stacks, List<Move>>{
    input
            .second
            .map { input.first.moveOldModel(it) }

    return input
}

fun executeMovesNewModel(input: Pair<Stacks, List<Move>>): Pair<Stacks, List<Move>>{
    input
            .second
            .map { input.first.moveNewModel(it) }

    return input
}

fun main() {
    loadFileLines5()
            .let(::splitStartingAndMoves)
            .let { executeMovesOldModel(it) }.first.peekAll().let(::println)

    loadFileLines5()
            .let(::splitStartingAndMoves)
            .let { executeMovesNewModel(it) }.first.peekAll().let(::println)

}

