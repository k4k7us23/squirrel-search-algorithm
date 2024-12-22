package com.k4k7us23.io.github.k4k7us23.ssa.functions

class TF1(dimensions: Int): TargetFunction(dimensions) {
    override val leftBound = -5.12
    override val rightBound = 5.12

    override fun evaluate(xArray: List<Double>): Double {
        var result = 0.0
        for (x in xArray) {
            result += (x + 0.5) * (x + 0.5);
        }
        return result
    }
}