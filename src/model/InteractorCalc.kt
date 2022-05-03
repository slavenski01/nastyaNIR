package model

import utils.Constants.PI
import utils.Constants.aa1
import utils.Constants.aa2
import utils.Constants.al
import utils.Constants.bet
import utils.Constants.gam
import utils.Constants.ht
import utils.Constants.hz
import utils.Constants.iRange
import utils.Constants.j
import utils.Constants.kRange
import utils.Constants.kk
import utils.Constants.kz
import utils.Constants.ll
import utils.Constants.lll
import utils.Constants.mm
import utils.Constants.u
import utils.Constants.vx
import utils.Constants.vy
import utils.Constants.z0
import extension.addZeroInFirstIndex
import extension.getIntegral
import extension.initZero
import extension.normalisation
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin

class InteractorCalc() : Interactor {
    override val b: MutableList<Double> = mutableListOf()
    override val A: MutableList<MutableList<Double>> = mutableListOf()
    override val h: MutableList<Double> = mutableListOf()
    override val f: MutableList<Double> = mutableListOf()
    override val ks: MutableList<Double> = mutableListOf()
    override val qqx1: MutableList<MutableList<Double>> = mutableListOf()
    override val qqx2: MutableList<MutableList<Double>> = mutableListOf()
    override val qqy1: MutableList<MutableList<Double>> = mutableListOf()
    override val qqy2: MutableList<MutableList<Double>> = mutableListOf()
    override val qqx: MutableList<MutableList<Double>> = mutableListOf()
    override val qqy: MutableList<MutableList<Double>> = mutableListOf()
    override val eeex: MutableList<MutableList<Double>> = mutableListOf()
    override val eeey: MutableList<MutableList<Double>> = mutableListOf()

    override fun initAllStartVariable() {
        b.initZero(0..mm)
        h.initZero(0..kk)
        f.initZero(0..kk)
        ks.initZero(iRange)
        A.addZeroInFirstIndex(0..mm, 0..kk)
        qqx1.addZeroInFirstIndex(0..lll, iRange)
        qqx2.addZeroInFirstIndex(0..lll, iRange)
        qqy1.addZeroInFirstIndex(0..lll, iRange)
        qqy2.addZeroInFirstIndex(0..lll, iRange)
        qqx.addZeroInFirstIndex(0..lll, iRange)
        qqy.addZeroInFirstIndex(0..lll, iRange)
        eeex.addZeroInFirstIndex(0..lll, iRange)
        eeey.addZeroInFirstIndex(0..lll, iRange)
    }

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

    override fun calcB() {
        for (i in 1..mm) {
            b[i] = (cos(PI * (i / mm)))
        }
    }

    override fun ee(x: Double, i: Int) = (1 + 4 * cos(x) * b[i] + 4 * b[i] * b[i]).pow(0.5)

    override fun vv(x: Double, i: Int) = sin(x) * (b[i] / ee(x, i))

    override fun calcA() {
        for (i in 1..mm) {
            for (k in 1..kk) {
                A[i][k] = getIntegral(downBound = -PI, upperBound = PI) { x ->
                    vv(x, i) * sin(k * (x))
                }
            }
        }
    }

    override fun tt(x: Double, i: Int) = 1 / (1 + exp(j * ee(x, i)))

    override fun hh(x: Double, k: Int): Double {
        var sum = 0.0
        for (i in 1..mm) {
            sum += A[i][k] * tt(x, i)
        }
        return sum
    }

    override fun calcH() {
        for (k in 1..kk) {
            h[k] = (getIntegral(downBound = -PI, upperBound = PI) { x ->
                hh(x, k) * cos(k * x)
            })
        }
    }

    override fun calcF() {
        for (k in 2..kk) {
            f[k] = (h[k] / h[1])
        }
    }

    override fun calcKS() {
        for (i in iRange) {
            ks[i] = (z0 + i * hz)
        }
    }

    override fun calcQQX1() {
        for (k in kRange) {
            val tempList = mutableListOf<Double>()
            for (i in iRange) {
                tempList.add(4 * exp(-((ks[i] / gam).pow(2))) * exp(-bet * (k - ll) * (k - ll)))
            }
            qqx1[k] = (tempList)
        }
    }

    override fun calcQQX2() {
        for (k in kRange) {
            val tempList = mutableListOf<Double>()
            for (i in iRange) {
                tempList.add(4 * exp(-((ks[i] - u * ht) / gam).pow(2)) * exp(-bet * (k - ll) * (k - ll)))
            }
            qqx2[k] = (tempList)
        }
    }

    override fun calcQQY1() {
        for (k in kRange) {
            val tempList = mutableListOf<Double>()
            for (i in iRange) {
                tempList.add(0.0)
            }
            qqy1[k] = (tempList)
        }
    }

    override fun calcQQY2() {
        for (k in kRange) {
            val tempList = mutableListOf<Double>()
            for (i in iRange) {
                tempList.add(0.0)
            }
            qqy2[k] = (tempList)
        }
    }

    override fun calcQQX() {
        var nn = 1
        print("[")
        while (nn < 200) {
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
            print("$nn, ")
            nn++
        }
        print("]")
    }

    override fun calcEEEX() {
        for (k in 1..lll) {
            for (i in 2..kz - 2) {
                eeex[k][i] = (-qqx[k][i + 1] + qqx[k][i]) / ht
            }
        }
    }

    override fun calcEEEY(withNormalization: Boolean) {
        for (k in 1..lll) {
            for (i in 2..kz - 2) {
                eeey[k][i] = ((-qqy[k][i + 1] + qqy[k][i]) / ht).pow(2)
            }
        }
        if (withNormalization) {
            eeey.normalisation()
        }
    }

    override fun calcWT(): List<Double> {
        val W = mutableListOf<Double>()
        var nn = 1
        while (nn < 10) {
            var sum = 0.0
            calcAll()
            for (k in 0..lll) {
                for (i in 0..kz) {
                    sum += ht * hz * (eeex[k][i] + eeey[k][i])
                }
            }
            W.add(sum)
            println("iteration nn: $nn")
            nn++
        }
        return W
    }
}