import com.example.anastasia.consants.Constants.MAX_W_FOR_T
import com.example.anastasia.consants.Constants.PI
import com.example.anastasia.consants.Constants.aa1
import com.example.anastasia.consants.Constants.aa2
import com.example.anastasia.consants.Constants.al
import com.example.anastasia.consants.Constants.bet
import com.example.anastasia.consants.Constants.gam
import com.example.anastasia.consants.Constants.ht
import com.example.anastasia.consants.Constants.hz
import com.example.anastasia.consants.Constants.iRange
import com.example.anastasia.consants.Constants.j
import com.example.anastasia.consants.Constants.kRange
import com.example.anastasia.consants.Constants.kk
import com.example.anastasia.consants.Constants.kz
import com.example.anastasia.consants.Constants.ll
import com.example.anastasia.consants.Constants.lll
import com.example.anastasia.consants.Constants.mm
import com.example.anastasia.consants.Constants.u
import com.example.anastasia.consants.Constants.vx
import com.example.anastasia.consants.Constants.vy
import com.example.anastasia.consants.Constants.z0
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin

class Presenter() {
    private val b = mutableListOf<Double>()
    private val A = mutableListOf<MutableList<Double>>()
    private val h = mutableListOf<Double>()
    private val f = mutableListOf<Double>()

    private fun calcB() {
        b.add(0.0)
        for (i in 1..mm) {
            b.add(cos(PI * (i / mm)))
        }
    }

    private fun ee(x: Double, i: Int) = (1 + 4 * cos(x) * b[i] + 4 * b[i] * b[i]).pow(0.5)

    private fun vv(x: Double, i: Int): Double {
        return sin(x) * (b[i] / ee(x, i))
    }

    private fun integralAFun(i: Int, k: Int): Double {
        val stepCount = 1000
        val step = (2 * PI) / (1.0 * stepCount)
        var sum = 0.0
        for (j in 0..stepCount) {
            val temp = -PI + j * step
            sum += vv(temp, i) * sin(k * temp)
        }
        sum += (vv(-PI, i) * sin(k * (-PI)) + vv(PI, i) * sin(k * (PI))) / 2
        sum *= step
        return sum
    }

    private fun calcA() {
        for (i in 0..mm) {
            val listNull = mutableListOf<Double>()
            for (k in 0..kk) {
                listNull.add(0.0)
            }
            A.add(listNull)
        }
        for (i in 1..mm) {
            for (k in 1..kk) {
                A[i][k] = integralAFun(i, k) / (2 * PI)
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

    private fun integralHFun(k: Int): Double {
        val stepCount = 1000
        val step = (2 * PI) / (1.0 * stepCount)
        var sum = 0.0
        for (j in 0..stepCount) {
            val temp = -PI + j * step
            sum += hh(temp, k) * cos(k * temp)
        }
        sum += (hh(-PI, k) * cos(k * (-PI)) + hh(PI, k) * cos(k * (PI))) / 2
        sum *= step
        return sum
    }

    private fun calcH() {
        h.add(0.0)
        for (k in 1..kk) {
            h.add(integralHFun(k))
        }
    }

    private fun calcF() {
        f.add(0.0)
        f.add(1.0)
        for (k in 2..kk) {
            f.add(h[k] / h[1])
        }
    }

    private val ks = mutableListOf<Double>()
    private fun calcKS() {
        for (i in iRange) {
            ks.add(z0 + i * hz)
        }
    }

    private val qqx1 = mutableListOf<MutableList<Double>>()
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

    private val qqx2 = mutableListOf<MutableList<Double>>()
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

    private val qqy1 = mutableListOf<MutableList<Double>>()
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

    private val qqy2 = mutableListOf<MutableList<Double>>()
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

    private val qqx = mutableListOf<MutableList<Double>>()
    private val qqy = mutableListOf<MutableList<Double>>()
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
        while (nn < 4500) {
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

    private val eeex = mutableListOf<MutableList<Double>>()
    private fun calcEEEX() {
        for (k in 0..lll) {
            val nullList = mutableListOf<Double>()
            for (i in iRange) {
                nullList.add(0.0)
            }
            eeex.add(nullList)
        }
        for (k in 1..lll) {
            for (i in 2..kz - 2) {
                eeex[k][i] = (-qqx[k][i + 1] + qqx[k][i]) / ht
            }
        }
    }

    private val eeey = mutableListOf<MutableList<Double>>()
    private fun calcEEEY(withNormalization: Boolean) {
        for (k in 0..lll) {
            val nullList = mutableListOf<Double>()
            for (i in iRange) {
                nullList.add(0.0)
            }
            eeey.add(nullList)
        }
        for (k in 1..lll) {
            for (i in 2..kz - 2) {
                eeey[k][i] = ((-qqy[k][i + 1] + qqy[k][i]) / ht).pow(2)
            }
        }
        if (withNormalization) {
            var maxEEEY = 0.0
            eeey.forEach { list ->
                if (list.maxOf { it } > maxEEEY) maxEEEY = list.maxOf { it }
            }
            for (k in 0..lll) {
                for (i in 0..kz) {
                    eeey[k][i] = eeey[k][i] / maxEEEY
                }
            }
        }
    }

    fun printResults(): List<MutableList<Double>> {
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
        return eeex
    }

    fun calcWOnT(): List<Double> {
        var t = 0
        val w = mutableListOf<Double>()
        while (t < MAX_W_FOR_T) {
            var tempSum = 0.0
            for(k in 0..lll) {
                for(i in 0..kz) {
                    tempSum += t * (ht) * (hz) * (eeex[k][i] + eeey[k][i])
                }
            }
            w.add(tempSum)
            t++
        }
        return w
    }
}

