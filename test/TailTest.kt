import main.main
import org.junit.jupiter.api.Test


class TailTest {
    @Test
    fun test() {
        main("tail -c 5 -o output.txt input1.txt input2.txt".split(" ").toTypedArray())
    }
}

//@Throws(IOException::class)
//fun doMain(args: Array<String?>) {
//    val parser = CmdLineParser(this)
//
//    // if you have a wider console, you could increase the value;
//    // here 80 is also the default
//    parser.setUsageWidth(80)
//    try {
//        // parse the arguments.
//        parser.parseArgument(*args)
//
//        // you can parse additional arguments if you want.
//        // parser.parseArgument("more","args");
//
//        // after parsing arguments, you should check
//        // if enough arguments are given.
//        if (arguments.isEmpty()) throw CmdLineException(parser, "No argument is given")
//    } catch (e: CmdLineException) {
//        // if there's a problem in the command line,
//        // you'll get this exception. this will report
//        // an error message.
//        System.err.println(e.message)
//        System.err.println("java SampleMain [options...] arguments...")
//        // print the list of available options
//        parser.printUsage(System.err)
//        System.err.println()
//
//        // print option sample. This is useful some time
//        System.err.println("  Example: java SampleMain" + parser.printExample(ALL))
//        return
//    }
//
//    // this will redirect the output to the specified output
//    System.out.println(out)
//    if (recursive) println("-r flag is set")
//    if (data) println("-custom flag is set")
//    System.out.println("-str was $str")
//    if (num >= 0) System.out.println("-n was $num")
//
//    // access non-option arguments
//    println("other arguments are:")
//    for (s in arguments) println(s)
//}