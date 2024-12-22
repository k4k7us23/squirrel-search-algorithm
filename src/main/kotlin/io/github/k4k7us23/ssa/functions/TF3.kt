package com.k4k7us23.io.github.k4k7us23.ssa.functions

class TF3(dimensions: Int): TargetFunction(dimensions) {
    override val leftBound = -10.0
    override val rightBound = 10.0

    override fun evaluate(xArray: List<Double>): Double {
        var result = 0.0
        for ((index, x) in xArray.withIndex()) {
            result += (index + 1) * (x) * (x);
        }
        return result
    }
}