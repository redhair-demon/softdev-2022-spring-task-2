package main

import org.kohsuke.args4j.Option
import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import java.io.IOException

class TailLauncher {
    @Option(name = "-c", metaVar = "CharsNumber", usage = "Number of last chars", forbids = ["-n"])
    var cnum: Int? = null

    @Option(name = "-n", metaVar = "LinesNumber", usage = "Number of last lines", forbids = ["-c"])
    var nnum: Int = 10

    @Option(name = "-o", metaVar = "OutputFile", usage = "Output file")
    var outFile: String? = null

    @Argument(metaVar = "InputFiles", usage = "Input files")
    var inFiles: List<String>? = null

    fun main(args: Array<String>) {
        if (args[0] != "tail") throw IOException("Incorrect command")
        TailLauncher().launch(args.sliceArray(1..args.lastIndex))
    }

    private fun launch(args: Array<String>) {
        val parser = CmdLineParser(this)

        try {
            parser.parseArgument(args.toList())
        } catch (e: CmdLineException) {
            System.err.println(e.message)
            System.err.println("java -jar tail.jar tail [-c num|-n num] [-o ofile] file0 file1 ...")
            parser.printUsage(System.err)
            return
        }

        val tail = Tail(outFile)
        try {
            tail.tailCmd(cnum, nnum, inFiles)
            println("Done.")
        } catch (e: IOException) {
            System.err.println(e.message)
        }
    }
}