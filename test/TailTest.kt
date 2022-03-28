
import main.Tail
import main.main
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals


class TailTest {

    private fun assertFileContent(name: String = "files/output.txt", expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @Test
    fun tailCmdTest() {
        val tsar3 = "Но будет, час расплаты ждёт.\n" +
                "Кто начал царствовать — Ходынкой,\n" +
                "Тот кончит — встав на эшафот."
        val tail = Tail("files/output.txt")
        tail.tailCmd(null, nnum = 3, inFiles = listOf("files/tsar.txt"))
        assertFileContent("files/output.txt", tsar3)
        File("files/output.txt").delete()

        tail.tailCmd(7, 10, inFiles =  listOf("files/tsar.txt"))
        assertFileContent("files/output.txt", "эшафот.")
        File("files/output.txt").delete()
    }

    @Test
    fun tailLauncherTest() {
        val tsar3 = "Но будет, час расплаты ждёт.\n" +
                "Кто начал царствовать — Ходынкой,\n" +
                "Тот кончит — встав на эшафот."
        val toxictsar = "files/tsar.txt\n" +
                "Зловонье пороха и дыма,\n" +
                "В котором разуму — темно.\n" +
                "Наш Царь — убожество слепое,\n" +
                "Тюрьма и кнут, подсуд, расстрел,\n" +
                "Царь-висельник, тем низкий вдвое,\n" +
                "Что обещал, но дать не смел.\n" +
                "Он трус, он чувствует с запинкой,\n" +
                "Но будет, час расплаты ждёт.\n" +
                "Кто начал царствовать — Ходынкой,\n" +
                "Тот кончит — встав на эшафот.\n" +
                "files/toxic.txt\n" +
                "Too high, can't come down\n" +
                "Losing my head, spinnin' 'round and 'round\n" +
                "Do you feel me now?\n" +
                "With a taste of your lips, I'm on a ride\n" +
                "You're toxic, I'm slippin' under\n" +
                "With a taste of a poison paradise\n" +
                "I'm addicted to you\n" +
                "Don't you know that you're toxic?\n" +
                "And I love what you do\n" +
                "Don't you know that you're toxic?"

        main("-n 3 -o files/output.txt files/tsar.txt".split(" ").toTypedArray())
        assertFileContent("files/output.txt", tsar3)
        File("files/output.txt").delete()

        main("-c 7 -o files/output.txt files/tsar.txt".split(" ").toTypedArray())
        assertFileContent("files/output.txt", "эшафот.")
        File("files/output.txt").delete()

        main("-o files/output.txt files/tsar.txt files/toxic.txt".split(" ").toTypedArray())
        assertFileContent("files/output.txt", toxictsar)
        File("files/output.txt").delete()

        //main("-c -n files/tsar.txt files/toxic.txt".split(" ").toTypedArray())
    }
}
