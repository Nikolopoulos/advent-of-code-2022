import java.io.File

val rootDir: CustomFile = CustomFile("/", 0, true, null)
var currentDir: CustomFile = rootDir
var part1Size = 0
var directorySizes = ArrayList<CustomFile>()
var totalDiskAvailable = 70000000
var updateSize = 30000000
var freeSpace = 0;

enum class Command() {
    CD, LS;
}

data class ParamsWithData(val params: Params, val data: String)
enum class Params() {
    UP,
    ROOT,
    SAME,
    DIR;

    companion object {
        fun fromString(s: String): ParamsWithData = when (s) {
            ".." -> ParamsWithData(UP,s)
            "/" -> ParamsWithData(ROOT,s)
            "." -> ParamsWithData(SAME,s)
            else -> ParamsWithData(DIR,s)
        }

    }
}

class CustomFile(val name: String, var size: Int, val directory: Boolean, val parent: CustomFile?) {
    var contents: MutableMap<String, CustomFile> = HashMap()

    companion object {
        fun fromString(input: String): CustomFile {
            val parts = input.split(" ")
            val isDir = parts[0].startsWith("dir")
            val size = when (isDir) {
                true -> 0
                false -> Integer.parseInt(parts[0])
            }

            val ret = CustomFile(parts[1], size, isDir, currentDir)
            currentDir.addFile(ret)
            return ret
        }
    }

    fun calculateSize(): Int {
        return when (directory) {
            true -> {
                size = contents.values.sumOf { it.calculateSize() }
                directorySizes.add(this)
                if(size < 100001){
                    println("foldersize: $size")
                    part1Size += size
                }
                size
            }
            false -> {
                this.size
            }
        }
    }

    fun addFile(f: CustomFile){
        contents.putIfAbsent(f.getMapName(), f)
    }

    fun getMapName():String{
        return name+"_"+directory
    }

    fun getPath(): String {
        return if (parent == null) {
            name
        } else {
            parent.getPath() + name + "/"
        }
    }
}

data class CommandParams(val command: Command, val params: List<ParamsWithData>) {
    companion object {
        fun fromString(input: String): CommandParams {
            val parts = input.split(" ")
            val com = Command.valueOf(parts[1].toUpperCase())

            return if (parts.size > 2) {
                val params = parts.subList(2, parts.size).map {
                    Params.fromString(it)
                }.toList()
                CommandParams(com, params)
            } else {
                CommandParams(com, listOf())
            }
        }
    }
}

data class CommandWithOutput(val com: CommandParams, val output: MutableList<String> = ArrayList())

fun loadFileLines7(): List<String> = File("day7/day7input.txt").readLines()

fun executeCommands(input: List<CommandWithOutput>) {
    input.stream().forEach {

        println("CurrentDir: "+currentDir.getPath())
        println("CurrentCommand: "+it.com.command.name+" "+it.com.params.toString())
        println("CurrentOutput: "+it.output.toString())

        if(it.com.command == Command.CD){
            when (it.com.params[0].params){
                Params.UP -> {
                    currentDir = currentDir.parent!!
                }
                Params.ROOT -> {
                    currentDir = rootDir
                }
                Params.DIR -> {
                    currentDir = currentDir.contents[it.com.params[0].data+"_"+true]!!
                } else -> {}
            }
        }
        else if(it.com.command == Command.LS){
            for (s in it.output) {
                CustomFile.fromString(s)
            }
        }
    }
}

fun separateCommandBlocks(input: List<String>): List<CommandWithOutput> {
    val blocksList = ArrayList<CommandWithOutput>()
    var tempCommandWithOutput: CommandWithOutput? = null

    for (line in input) {
        if (line.startsWith("$")) {
            tempCommandWithOutput = CommandWithOutput(CommandParams.fromString(line))
            blocksList.add(tempCommandWithOutput)
        } else {
            tempCommandWithOutput?.output?.add(line)
        }
    }

    return blocksList
}

fun main() {
    executeCommands(
            separateCommandBlocks(loadFileLines7()))
    println(rootDir.calculateSize())
    println("part1size $part1Size")
    freeSpace = totalDiskAvailable - rootDir.size
    directorySizes
            .sortBy { e -> e.size }
    val spaceNeeded = updateSize - freeSpace
    directorySizes.find { dir -> dir.size > spaceNeeded }.let { println(it?.size) }
}