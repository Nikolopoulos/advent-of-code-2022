import java.io.File

fun loadFileLines6(): CharArray = File("day6/day6input.txt").readText().toCharArray()

fun isBufferStartPacket(input: CharArray): Boolean =
    input.sumOf { c -> input.count { a -> a == c } } == input.size

fun charsNeededForStartPacket(input: CharArray, size:Int): Int{
    var range: IntRange = IntRange(0,size -1)

    while(!isBufferStartPacket(input.sliceArray(range))){
        range = IntRange(range.first.inc(), range.last.inc())
        println(""+range.first+","+range.last+" "+String(input.sliceArray(range)))
    }

    return range.last+1
}

fun main(){
    charsNeededForStartPacket(loadFileLines6(),4).let (::println)
    charsNeededForStartPacket(loadFileLines6(),14).let (::println)
}