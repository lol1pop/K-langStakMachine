import com.google.gson.Gson
import core.StackMachine
import core.model.ActionProps
import core.model.LangProps
import core.model.LangStackMachine
import gui.MainForm
import javafx.application.Application
import tornadofx.*
import java.io.BufferedReader
import java.io.File

class MainApp : App(MainForm::class)

fun gui__main(args: Array<String>) {
    Application.launch(MainApp::class.java, *args)
}


fun readFileJson(): LangStackMachine {
    val lambda = "λ"
    try {
        val bufferedReader: BufferedReader = File("data_file.json").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        println("$inputString\n\n ============================\n")
        val gson = Gson()
        val dataJson = gson.fromJson(inputString, LangStackMachine::class.java)
        return dataJson.copy(chain = dataJson.chain + lambda,
            alphabet = dataJson.alphabet + lambda)
    } catch (e: Exception) {
        println(e.message)
        return LangStackMachine(
            states = "q0,q1",
            alphabet = "ab",
            props = listOf(),
            startState = "q0",
            startStack = "Z",
            endStates = "q1"
        )
    }
}

fun getGrammatically(lang: LangStackMachine)  = "P({${lang.states}}, {${lang.alphabet}}, {${lang.startStack}${lang.alphabet}}, ^, ${lang.startState}, ${lang.startStack}, {${lang.endStates}})"

fun main() {
    val dataReadJson = readFileJson()
    val chain = dataReadJson.chain!!
    val stackMachine = StackMachine(dataReadJson)
    val result = stackMachine.identifyChain(chain)
    println(result.Log)
    if (result.IsRecognized)
        println("The string '$chain' is ACCEPTED")
    else
        println("The string '$chain' is NOT ACCEPTED")
}

//val data = LangStackMachine(
//    states = "q0,q1",
//    alphabet = "ab",
//    props = listOf(
//        LangProps(
//            "q0",
//            'a',
//            'Z',
//            ActionProps(
//                "q0",
//                "xZ"
//            )
//        ), LangProps(
//            "q0",
//            'a',
//            'x',
//            ActionProps(
//                "q0",
//                "xx"
//            )
//        ), LangProps(
//            "q0",
//            'b',
//            'Z',
//            ActionProps(
//                "q0",
//                "yZ"
//            )
//        ), LangProps(
//            "q0",
//            'b',
//            'x',
//            ActionProps(
//                "q0",
//                ""
//            )
//        ), LangProps(
//            "q0",
//            'b',
//            'y',
//            ActionProps(
//                "q0",
//                "yy"
//            )
//        ), LangProps(
//            "q0",
//            'a',
//            'y',
//            ActionProps(
//                "q0",
//                ""
//            )
//        ), LangProps(
//            "q0",
//            'b',
//            'x',
//            ActionProps(
//                "q0",
//                ""
//            )
//        ), LangProps(
//            "q0",
//            'λ',
//            'Z',
//            ActionProps(
//                "q1",
//                ""
//            )
//        )
//    ),
//    startState = "q0",
//    startStack = "Z",
//    endStates = "q1"
//
//)