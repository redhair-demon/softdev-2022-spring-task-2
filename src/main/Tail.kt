package main

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintStream
import java.io.RandomAccessFile
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
            val number = cnum ?: nnum
            if (inFiles.isNullOrEmpty()) {
                val text = if (cnum == null) System.`in`.readLastLines(number) else System.`in`.readLastChars(number) // if (cnum == null) listOf(readln()) else readChars(System.`in`, cnum)
                println("text: $text")
                text.forEach { stream.print(it) }
            } else {
                for (file in inFiles) {
                    val inputStream = File(file).inputStream()
                    val text = if (cnum == null) inputStream.readLastLines(number) else inputStream.readLastChars(number)   // readLines(inputStream, number) else readChars(inputStream, number)
                    println("text: $text")
                    if (inFiles.size > 1) stream.println(file)
                    text.forEach { stream.print(it) }
                }
            }
        }
    }

    private fun InputStream.readLastLines(number: Int): List<String> {
        val stringBuilder = StringBuilder()
        val result = mutableListOf<String>()
        var currentChar: Char
        while (this.available() > 0) {
            currentChar = this.read().toChar()
            stringBuilder.append(currentChar)
            if (stringBuilder.contains(System.lineSeparator())) {
                result += stringBuilder.toString()
                stringBuilder.clear()
            }
            if (result.size > number) result.removeAt(0)
        }
        result += stringBuilder.toString()
        if (result.size > number) result.removeAt(0)
        return result
    }

    private fun InputStream.readLastChars(number: Int): List<Char> {
        val stringBuilder = StringBuilder()
        var currentChar: Char
        File("E:/softdev-2022-spring-task-2/testoutput.txt").bufferedWriter(Charsets.ISO_8859_1).use {
        while (this.available() > 0) {
            currentChar = this.read().toChar()
            stringBuilder.append(currentChar)
            it.write(currentChar.toString())
            if (stringBuilder.length > number) stringBuilder.deleteCharAt(0)
        }
        }
        return stringBuilder.toList()
    }
}

