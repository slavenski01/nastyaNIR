import Constants.MAX_W_FOR_T
import Constants.PI
import Constants.aa1
import Constants.aa2
import Constants.al
import Constants.bet
import Constants.gam
import Constants.ht
import Constants.hz
import Constants.iRange
import Constants.j
import Constants.kRange
import Constants.kk
import Constants.kz
import Constants.ll
import Constants.lll
import Constants.mm
import Constants.u
import Constants.vx
import Constants.vy
import Constants.z0
import extension.addZeroInFirstIndex
import extension.getIntegral
import extension.normalisation
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin

class Presenter private constructor() : CalcFacade {
    private val b = mutableListOf<Double>()
    private val A = mutableListOf<MutableList<Double>>()
    private val h = mutableListOf<Double>()
    private val f = mutableListOf<Double>()
    private val ks = mutableListOf<Double>()
    private val qqx1 = mutableListOf<MutableList<Double>>()
    private val qqx2 = mutableListOf<MutableList<Double>>()
    private val qqy1 = mutableListOf<MutableList<Double>>()
    private val qqy2 = mutableListOf<MutableList<Double>>()
    private val qqx = mutableListOf<MutableList<Double>>()
    private val qqy = mutableListOf<MutableList<Double>>()
    private val eeex = mutableListOf<MutableList<Double>>()
    private val eeey = mutableListOf<MutableList<Double>>()

    companion object {
        private var uniqueInstance: Presenter = Presenter()

        fun getInstance(): Presenter {
            return uniqueInstance
        }
    }

    override fun getEeex(): List<MutableList<Double>> = eeex

    override fun getEeey(): List<MutableList<Double>> = eeey

    override fun calcAll() {
        calcB()
        calcA()
        calcH()
        calcF()
        calcKS()
        calcQQX1()
        calcQQX2()
        calcQQY1()
        calcQQY2()
        calcQQX()
        calcEEEX()
        calcEEEY(false)
    }

    override fun calcWonT(): List<Double> {
        var t = 0
        val w = mutableListOf<Double>()
        while (t < MAX_W_FOR_T) {
            var tempSum = 0.0
            for (k in 0..lll) {
                for (i in 0..kz) {
                    tempSum += t * (ht) * (hz) * (eeex[k][i] + eeey[k][i])
                }
            }
            w.add(tempSum)
            t++
        }
        return w
    }

    init {
        initAllStartVariable()
    }

    private fun initAllStartVariable() {
        b.add(0.0)
        h.add(0.0)
        f.add(0.0)
        f.add(1.0)
        A.addZeroInFirstIndex(0..mm, 0..kk)
        eeex.addZeroInFirstIndex(0..lll, iRange)
        eeey.addZeroInFirstIndex(0..lll, iRange)
    }

    private fun calcB() {
        for (i in 1..mm) {
            b.add(cos(PI * (i / mm)))
        }
    }

    private fun ee(x: Double, i: Int) = (1 + 4 * cos(x) * b[i] + 4 * b[i] * b[i]).pow(0.5)

    private fun vv(x: Double, i: Int): Double {
        return sin(x) * (b[i] / ee(x, i))
    }

    private fun calcA() {
        for (i in 1..mm) {
            for (k in 1..kk) {
                A[i][k] = getIntegral(downBound = -PI, upperBound = PI) { x ->
                    vv(x, i) * sin(k * (x))
                }
            }
        }
    }

    private fun tt(x: Double, i: Int) = 1 / (1 + exp(j * ee(x, i)))

    private fun hh(x: Double, k: Int): Double {
        var sum = 0.0
        for (i in 1..mm) {
            sum += A[i][k] * tt(x, i)
        }
        return sum
    }

    private fun calcH() {
        for (k in 1..kk) {
            h.add(
                getIntegral(downBound = -PI, upperBound = PI) { x ->
                    hh(x, k) * cos(k * x)
                }
            )
        }
    }

    private fun calcF() {
        for (k in 2..kk) {
            f.add(h[k] / h[1])
        }
    }

    private fun calcKS() {
        for (i in iRange) {
            ks.add(z0 + i * hz)
        }
    }

    private fun calcQQX1() {
        val nullList = mutableListOf<Double>()
        for (i in iRange) {
            nullList.add(0.0)
        }
        qqx1.add(nullList)

        for (k in kRange) {
            val tempList = mutableListOf<Double>()
            for (i in iRange) {
                tempList.add(4 * exp(-((ks[i] / gam).pow(2))) * exp(-bet * (k - ll) * (k - ll)))
            }
            qqx1.add(tempList)
        }
    }

    private fun calcQQX2() {
        val nullList = mutableListOf<Double>()
        for (i in iRange) {
            nullList.add(0.0)
        }
        qqx2.add(nullList)

        for (k in kRange) {
            val tempList = mutableListOf<Double>()
            for (i in iRange) {
                tempList.add(4 * exp(-((ks[i] - u * ht) / gam).pow(2)) * exp(-bet * (k - ll) * (k - ll)))
            }
            qqx2.add(tempList)
        }
    }

    private fun calcQQY1() {
        val nullList = mutableListOf<Double>()
        for (i in iRange) {
            nullList.add(0.0)
        }
        qqy1.add(nullList)

        for (k in kRange) {
            val tempList = mutableListOf<Double>()
            for (i in iRange) {
                tempList.add(0.0)
            }
            qqy1.add(tempList)
        }
    }

    private fun calcQQY2() {
        val nullList = mutableListOf<Double>()
        for (i in iRange) {
            nullList.add(0.0)
        }
        qqy2.add(nullList)

        for (k in kRange) {
            val tempList = mutableListOf<Double>()
            for (i in iRange) {
                tempList.add(0.0)
            }
            qqy2.add(tempList)
        }
    }

    private fun calcQQX() {
        for (k in 0..lll) {
            val nullList = mutableListOf<Double>()
            for (i in iRange) {
                nullList.add(0.0)
            }
            qqx.add(nullList)
        }

        for (k in 0..lll) {
            val nullList = mutableListOf<Double>()
            for (i in iRange) {
                nullList.add(0.0)
            }
            qqy.add(nullList)
        }

        var nn = 1
        while (nn < 1000) {
            for (k in kRange) {
                qqx[k][0] = (4 * qqx2[k][1] - qqx2[k][2]) / 3
                qqx[k][kz] = (4 * qqx2[k][kz - 1] - qqx2[k][kz - 2]) / 3
                qqy[k][0] = (4 * qqy2[k][1] - qqy2[k][2]) / 3
                qqy[k][kz] = (4 * qqy2[k][kz - 1] - qqy2[k][kz - 2]) / 3
            }

            for (i in 1 until kz) {
                var p = 2
                var sum1 = 0.0
                var sum2 = 0.0
                while (p < kk) {
                    sum1 += f[p] * sin(p * (qqx2[1][i] * cos(al) + qqy[1][i] * sin(al)))
                    sum2 += f[p] * sin(p * (qqx2[lll][i] * cos(al) + qqy[lll][i] * sin(al)))
                    p++
                }

                qqx[1][i] = 2 * qqx2[1][i] - qqx1[1][i] + aa1 * vx *
                        (qqx2[1][i + 1] - 2 * qqx2[1][i] + qqx2[1][i - 1]) +
                        1.5 * aa1 * (qqx2[2][i] - qqx2[1][i]) +
                        aa2 * cos(al) * (sin(qqx2[1][i] * cos(al) + qqy[1][i] * sin(al)) + sum1)

                qqx[lll][i] = 2 * qqx2[lll][i] - qqx1[lll][i] + aa1 * vx *
                        (qqx2[lll][i + 1] - 2 * qqx2[lll][i] + qqx2[lll][i - 1]) +
                        aa1 * (qqx2[lll][i] - qqx2[lll - 1][i]) * ((-lll + 0.5) / lll) +
                        aa2 * cos(al) * (sin(qqx2[lll][i]) * cos(al) + qqy[lll][i] * sin(al) + sum2)

                qqy[1][i] = 2 * qqy2[1][i] - qqy1[1][i] + aa1 * vy *
                        (qqy2[1][i + 1] - 2 * qqy2[1][i] + qqy2[1][i - 1]) +
                        1.5 * aa1 * (qqy2[2][i] - qqy2[1][i]) +
                        aa2 * sin(al) * (sin(qqx2[1][i] * cos(al) + qqy[1][i] * sin(al)) + sum1)

                qqy[lll][i] = 2 * qqy2[lll][i] - qqy1[lll][i] + aa1 * vy *
                        (qqy2[lll][i + 1] - 2 * qqy2[lll][i] + qqy2[lll][i - 1]) +
                        aa1 * (qqy2[lll][i] - qqy2[lll - 1][i]) * ((-lll + 0.5) / lll) +
                        aa2 * sin(al) * (sin(qqx2[lll][i]) * cos(al) + qqy[lll][i] * sin(al) + sum2)
            }

            for (k in 2 until lll) {
                for (i in 1 until kz) {
                    var p = 2
                    var sum1 = 0.0
                    while (p < kk) {
                        sum1 += f[p] * sin(p * (qqx2[k][i] * cos(al) + qqy2[k][i] * sin(al)))
                        p++
                    }
                    qqx[k][i] = 2 * qqx2[k][i] - qqx1[k][i] + aa1 * vx *
                            (qqx2[k][i + 1] - 2 * qqx2[k][i] + qqx2[k][i - 1]) +
                            aa1 * (((k + 0.5) * (qqx2[k + 1][i] - qqx2[k][i]) -
                            (k - 0.5) * (qqx2[k][i] - qqx2[k - 1][i])) / k) +
                            aa2 * cos(al) * (sin(qqx2[k][i] * cos(al) + qqy2[k][i] * sin(al)) + sum1)

                    qqy[k][i] = 2 * qqy2[k][i] - qqy1[k][i] + aa1 * vy *
                            (qqy2[k][i + 1] - 2 * qqy2[k][i] + qqy2[k][i - 1]) +
                            aa1 * (((k + 0.5) * (qqy2[k + 1][i] - qqy2[k][i]) -
                            (k - 0.5) * (qqy2[k][i] - qqy2[k - 1][i])) / k) +
                            aa2 * sin(al) * (sin(qqx2[k][i] * cos(al) + qqy2[k][i] * sin(al)) + sum1)
                }
            }

            for (k in 1..lll) {
                for (i in 0..kz) {
                    qqx1[k][i] = qqx2[k][i]
                    qqx2[k][i] = qqx[k][i]
                    qqy1[k][i] = qqy2[k][i]
                    qqy2[k][i] = qqy[k][i]
                }
            }
            println("$nn")
            nn++
        }
    }

    private fun calcEEEX() {
        for (k in 1..lll) {
            for (i in 2..kz - 2) {
                eeex[k][i] = (-qqx[k][i + 1] + qqx[k][i]) / ht
            }
        }
    }

    private fun calcEEEY(withNormalization: Boolean) {
        for (k in 1..lll) {
            for (i in 2..kz - 2) {
                eeey[k][i] = ((-qqy[k][i + 1] + qqy[k][i]) / ht).pow(2)
            }
        }
        if (withNormalization) {
            eeey.normalisation()
        }
    }
}