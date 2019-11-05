import gui.MainForm
import javafx.application.Application
import tornadofx.*

class MainApp : App(MainForm::class)

fun gui__main(args: Array<String>) {
    Application.launch(MainApp::class.java, *args)
}

fun main() {

}