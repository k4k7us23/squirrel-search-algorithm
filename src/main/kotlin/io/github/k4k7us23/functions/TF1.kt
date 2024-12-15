package com.k4k7us23.io.github.k4k7us23.functions

class TF1(dimensions: Int): TargetFunction(dimensions) {
    override val leftBound = -100.0
    override val rightBound = 100.0

    override fun evaluate(xArray: List<Double>): Double {
        var result = 0.0
        for (x in xArray) {
            result += (x) * (x);
        }
        return result
    }
}