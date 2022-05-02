import java.io.File

fun main() {
    print("Hello")

    val presenter = Presenter()
    val eeex = presenter.printResults()
    val wOntList = presenter.calcWOnT()
    File("chickenBurger.txt").printWriter().use { out ->
        wOntList.forEach {
            out.println("$it\t")
        }
    }
}