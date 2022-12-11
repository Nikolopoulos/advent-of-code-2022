import java.io.File

fun loadSums(): List<Int> =
    File("input.txt")
        .readText()
        .split("\n\n")
        .map { subList -> subList.split("\n") }
        .filter{
            subList -> subList.isNotEmpty()
        }
        .map { subList -> subList
            .filter { input -> input.isNotEmpty() }
            .map { input -> Integer.parseInt(input) }
            .reduce{a,b -> a+b}
        }


fun day1Part1() {
    println(loadSums().sorted().reversed()[0])
}

fun day1Part2() {
    println(loadSums().sorted().reversed().subList(0,3).sum())
}

fun main() {
    day1Part1()
    day1Part2()
}