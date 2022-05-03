package presenter

interface CalcFacade {
    fun getEeex(): List<MutableList<Double>>
    fun getEeey(): List<MutableList<Double>>
    fun calcAll()
    fun calcWonT(): List<Double>
}