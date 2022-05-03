import presenter.Presenter
import view.CalcView
import view.View

fun main() {
    val view: View = CalcView(Presenter.getInstance())
    view.printInFile()
}