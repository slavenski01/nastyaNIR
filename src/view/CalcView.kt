package view

import presenter.Presenter
import java.io.File

class CalcView(private val presenter: Presenter): View {
    override fun printInFile() {
        println("Hello")
        val wOntList = presenter.calcWonT()
        File("chickenBurger.txt").printWriter().use { out ->
            wOntList.forEach {
                out.println("$it\t")
            }
        }
    }
}