import core.StackMachine
import core.model.ActionProps
import core.model.LangProps
import core.model.LangStackMachine
import gui.MainForm
import javafx.application.Application
import tornadofx.*

class MainApp : App(MainForm::class)

fun gui__main(args: Array<String>) {
    Application.launch(MainApp::class.java, *args)
}

fun main() {
    val data = LangStackMachine(
        states = "q0,q1",
        alphabet = "ab",
        props = listOf(
            LangProps(
                "q0",
                'a',
                'Z',
                ActionProps(
                    "q0",
                    "xZ"
                )
            ),LangProps(
                "q0",
                'a',
                'x',
                ActionProps(
                    "q0",
                    "xx"
                )
            ),LangProps(
                "q0",
                'b',
                'Z',
                ActionProps(
                    "q0",
                    "yZ"
                )
            ),LangProps(
                "q0",
                'b',
                'x',
                ActionProps(
                    "q0",
                    ""
                )
            ),LangProps(
                "q0",
                'b',
                'y',
                ActionProps(
                    "q0",
                    "yy"
                )
            ),LangProps(
                "q0",
                'a',
                'y',
                ActionProps(
                    "q0",
                    ""
                )
            ),LangProps(
                "q0",
                'b',
                'x',
                ActionProps(
                    "q0",
                    ""
                )
            ),LangProps(
                "q0",
                'λ',
                'Z',
                ActionProps(
                    "q1",
                    ""
                )
            )
        ),
        startState = "q0",
        startStack = "Z",
        endStates = "q1"

    )

    val stackMachine = StackMachine(data)
    val result = stackMachine.identifyChain("abbabaaabbλ")
    println(result)
}