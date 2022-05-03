package utils

import kotlin.math.pow

object Constants {
    const val j = 5
    const val PI = Math.PI
    const val mm = 13
    const val kk = 15
    const val bet = 0.03
    const val hz = 0.06
    const val z0 = -10.0
    const val kz = 800
    const val ht = 0.005
    const val lll = 200
    const val ll = 0
    const val u = 0.93
    const val al = 0.9
    const val vx = 1
    const val vy = 0.5
    val gam = (1 - u * u).pow(0.5)
    val iRange = 0..kz
    val kRange = 1..lll
    val aa1 = (ht / hz).pow(2)
    val aa2 = ht * ht
}