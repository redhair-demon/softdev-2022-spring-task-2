package main

import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File

/*
Выделение из текстового файла его конца некоторого размера:
fileN задаёт имя входного файла. Если параметр отсутствует, следует считывать входные данные с консольного ввода.
Если параметров несколько, то перед выводом для каждого файла следует вывести его имя в отдельной строке.
Флаг -o ofile задаёт имя выходного файла (в данном случае ofile).
Если параметр отсутствует, следует выводить результат на консольный вывод.
Флаг -с num, где num это целое число, говорит о том, что из файла нужно извлечь последние num символов.
Флаг -n num, где num это целое число, говорит о том, что из файла нужно извлечь последние num строк.

Command line: tail [-c num|-n num] [-o ofile] file0 file1 file2 …

В случае, когда какое-нибудь из имён файлов неверно или указаны одновременно флаги -c и -n, следует выдать ошибку.
Если ни один из этих флагов не указан, следует вывести последние 10 строк
*/

fun main(args: Array<String>) {
    Tail().
}



class Tail {
    @Option(name = "-c", usage = "Number last chars", forbids = ["-n"])
    private var cnum: Int = -1

    @Option(name = "-n", usage = "Number last lines", forbids = ["-c"])
    private var nnum: Int = -1

    @Option(name = "-o", usage = "Output file name")
    private var outFile: String = ""

    @Argument
    var input: List<String> = emptyList()


}

fun tailCommand(cnum: Int, nnum: Int, outFile: String, inFiles: List<String>) {
    val output: List<String> = when {
        // last cnum characters
        cnum > -1 -> {
            if (inFiles.isEmpty()) {
                listOf(readChars(readln(), cnum))
            } else {
                val result = mutableListOf<String>()
                for (file in inFiles) {
                    result += readChars(File(file).readText(), cnum)
                }
                result
            }
        }

        // last nnum lines
        nnum > -1 -> {
            if (inFiles.isEmpty()) {
                readLines(listOf(readln()), nnum)
            } else {
                val result = mutableListOf<String>()
                for (file in inFiles) {
                    result += readLines(File(file).readLines(), nnum)
                }
                result
            }
        }

        // no -c|-n
        cnum == nnum -> {
            if (inFiles.isEmpty()) {
                readLines(listOf(readln()), 10)
            } else {
                val result = mutableListOf<String>()
                for (file in inFiles) {
                    result += readLines(File(file).readLines(), 10)
                }
                result
            }
        }
        else -> throw IllegalArgumentException("Incorrect number")
    }
    if (outFile.isEmpty())
        for ((i, line) in output.withIndex()) {
            if (inFiles.isNotEmpty()) println(inFiles[i])
            println(line)
        }
    else {
        File(outFile).bufferedWriter().use {
            for ((i, line) in output.withIndex()) {
                if (inFiles.isNotEmpty()) it.write("${inFiles[i]}/n")
                it.write("$line/n")
            }
        }
    }
}

fun readLines(file: List<String>, number: Int): List<String> {
    val result = mutableListOf<String>()
    for (i in 0..number) {
        result += file.reversed().getOrNull(i) ?: break
    }
    return result.reversed()
}

fun readChars(file: String, number: Int): String =
    file.substring((if (number > file.length) 0 else file.length - number)..file.lastIndex)