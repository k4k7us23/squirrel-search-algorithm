package com.k4k7us23.io.github.k4k7us23.ssa.functions

abstract class TargetFunction(val dimensions: Int) {

    open val leftBound: Double = (-1e9).toDouble()
    open val rightBound: Double = (1e9).toDouble()

    fun call(xArray: List<Double>): Double {
        check(xArray.size == dimensions) {
            "This function accepts $dimensions variables, but got ${xArray.size}"
        }
        return evaluate(xArray)
    }

    protected abstract fun evaluate(xArray: List<Double>): Double
}