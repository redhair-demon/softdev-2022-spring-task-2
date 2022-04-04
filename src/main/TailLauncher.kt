package main

import org.kohsuke.args4j.Option
import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import java.io.File
import java.io.IOException
import java.io.PrintStream

class TailLauncher {

    @Option(name = "-c", metaVar = "CharsNumber", usage = "Number of last chars", forbids = ["-n"])
    var cnum: Int? = null

    @Option(name = "-n", metaVar = "LinesNumber", usage = "Number of last lines", forbids = ["-c"])
    var nnum: Int = 10

    @Option(name = "-o", metaVar = "OutputFile", usage = "Output file")
    var outFile: String? = null

    @Argument(metaVar = "InputFiles", usage = "Input files")
    var inFiles: List<String>? = null

    fun launch(args: Array<String>) {
        val consoleStream = System.out
        val parser = CmdLineParser(this)

        try {
            parser.parseArgument(args.toList())
        } catch (e: CmdLineException) {
            System.err.println(e.message)
            System.err.println("java -jar tail.jar [-c num|-n num] [-o ofile] file0 file1 ...")
            parser.printUsage(System.err)
            return
        }

        val tail = Tail(if (outFile.isNullOrBlank()) consoleStream else File(outFile).outputStream()) // PrintStream(outFile, "UTF-8"))
        try {
            tail.tailCmd(cnum, nnum, inFiles)
        } catch (e: IOException) {
            System.err.println(e.message)
        }
    }
}

fun main(args: Array<String>) {
    TailLauncher().launch(args)
}