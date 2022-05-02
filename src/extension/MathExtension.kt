package extension


fun getIntegral(
    downBound: Double,
    upperBound: Double,
    stepCount: Int = 1000,
    function: (x: Double) -> Double,
): Double {

    val h = (upperBound - downBound) / stepCount
    var sum = (function.invoke(upperBound) - function(downBound)) / 2

    var x = downBound + h
    for (i in 1 until stepCount) {
        sum += function.invoke(x)
        x += h
    }
    return h * sum
}