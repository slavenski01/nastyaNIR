import java.io.File

fun main() {
    print("Hello")

    val presenter = Presenter.getInstance()
    presenter.calcAll()
    val eeex = presenter.getEeex()
    val eeey = presenter.getEeey()
    val wOntList = presenter.calcWonT()


    File("chickenBurger.txt").printWriter().use { out ->
        eeex.forEach {
            out.println("$it\t")
        }
    }
}