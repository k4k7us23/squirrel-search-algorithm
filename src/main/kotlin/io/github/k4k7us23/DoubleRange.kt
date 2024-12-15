package com.k4k7us23.io.github.k4k7us23


class DoubleRange(
    val leftValue: Double,
    val rightValue: Double
) {

    init {
        check(rightValue >= leftValue) {
            "rightValue must be >= leftValue"
        }
    }

    fun rndValueInRange(rndUtils: RandomUtils): Double {
        return rndUtils.randRange(leftValue, rightValue)
    }
}