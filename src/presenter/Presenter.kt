package presenter

import model.Interactor
import model.InteractorCalc

class Presenter private constructor(
    private val interactor: Interactor
) : CalcFacade {

    companion object {
        private var uniqueInstance: Presenter = Presenter(InteractorCalc())

        fun getInstance(): Presenter {
            return uniqueInstance
        }
    }

    override fun getEeex(): List<MutableList<Double>> = interactor.eeex

    override fun getEeey(): List<MutableList<Double>> = interactor.eeey

    override fun calcAll() {
        interactor.calcAll()
    }

    override fun calcWonT(): List<Double> = interactor.calcWT()

    init {
        initAllStartVariable()
    }

    private fun initAllStartVariable() {
        interactor.initAllStartVariable()
    }
}