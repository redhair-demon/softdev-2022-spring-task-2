package main

import java.io.File
import java.io.IOException
import java.io.PrintStream
import kotlin.jvm.Throws

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

class Tail(private val outputStream: PrintStream) {

    @Throws(IOException::class)
    fun tailCmd(cnum: Int?, nnum: Int, inFiles: List<String>?) {
        outputStream.use { stream ->
            when {
                inFiles.isNullOrEmpty() -> {
                    val text = if (cnum == null) readLines(listOf(readln()), nnum) else readChars(readln(), cnum)
                    text.forEach { stream.print(it) }
                }

                inFiles.size == 1 -> {
                    val text = if (cnum == null) readLines(File(inFiles[0]).readLines(), nnum)
                    else readChars(File(inFiles[0]).readText(), cnum)
                    text.forEach { stream.print(it) }
                }

                else -> {   // inFiles.size > 1
                    for (file in inFiles) {
                        val text = if (cnum == null) readLines(File(file).readLines(), nnum)
                        else readChars(File(file).readText(), cnum)
                        stream.println(file)
                        text.forEach { stream.print(it) }
                    }
                }
            }
        }
    }

    private fun readLines(file: List<String>, number: Int): List<String> {
        //file.forEach { name -> File(name).useLines { lines ->  } }
        val result = mutableListOf<String>()
        file.forEach { result += it + "\n" }
        return result.subList((if (number > file.size) 0 else file.size - number), file.size)
    }

    private fun readChars(file: String, number: Int): List<String> =
        file.substring((if (number > file.length) 0 else file.length - number)..file.lastIndex).split("") + "\n"
}

