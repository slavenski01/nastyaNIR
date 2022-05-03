package model

interface Interactor {
    val b: List<Double>
    val A: List<MutableList<Double>>
    val h: List<Double>
    val f: List<Double>
    val ks: List<Double>
    val qqx1: List<MutableList<Double>>
    val qqx2: List<MutableList<Double>>
    val qqy1: List<MutableList<Double>>
    val qqy2: List<MutableList<Double>>
    val qqx: List<MutableList<Double>>
    val qqy: List<MutableList<Double>>
    val eeex: List<MutableList<Double>>
    val eeey: List<MutableList<Double>>
    fun initAllStartVariable()
    fun calcAll()
    fun calcB()
    fun ee(x: Double, i: Int): Double
    fun vv(x: Double, i: Int): Double
    fun calcA()
    fun tt(x: Double, i: Int): Double
    fun hh(x: Double, k: Int): Double
    fun calcH()
    fun calcF()
    fun calcKS()
    fun calcQQX1()
    fun calcQQX2()
    fun calcQQY1()
    fun calcQQY2()
    fun calcQQX()
    fun calcEEEX()
    fun calcEEEY(withNormalization: Boolean)
    fun calcWT(): List<Double>
}