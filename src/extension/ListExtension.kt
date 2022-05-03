package extension

import utils.Constants

fun MutableList<Double>.initZero(range: IntRange) {
    for(i in range) {
        this.add(0.0)
    }
}

fun MutableList<MutableList<Double>>.addZeroInFirstIndex(rangeMain: IntRange, rangeSub: IntRange) {
    for (i in rangeMain) {
        val nullList = mutableListOf<Double>()
        for (k in rangeSub) {
            nullList.add(0.0)
        }
        this.add(nullList)
    }
}

fun List<MutableList<Double>>.normalisation() {
    var max = 0.0
    this.forEach { list ->
        if (list.maxOf { it } > max) max = list.maxOf { it }
    }
    for (k in 0..Constants.lll) {
        for (i in 0..Constants.kz) {
            this[k][i] = this[k][i] / max
        }
    }
}