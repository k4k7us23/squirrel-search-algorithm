package com.k4k7us23.io.github.k4k7us23.ssa

import java.util.ArrayList
import kotlin.math.sqrt

object PositionUtils {

    fun add(a: List<Double>, b: List<Double>): List<Double> {
        checkSizesAreSame(a, b)
        val result = ArrayList<Double>(a.size)
        for (i in a.indices) {
            result.add(a[i] + b[i])
        }
        return result
    }

    fun subtract(a: List<Double>, b: List<Double>): List<Double> {
        checkSizesAreSame(a, b)
        val result = ArrayList<Double>(a.size)
        for (i in a.indices) {
            result.add(a[i] - b[i])
        }
        return result
    }

    fun multiply(factor: Double, a: List<Double>): List<Double> {
        return a.map { value ->
            value * factor
        }
    }

    fun getClosest(point: List<Double>, targets: List<List<Double>>): List<Double> {
        var result = targets[0]
        fun costFunc(target: List<Double>): Double {
            checkSizesAreSame(target, point)
            var cost = 0.0
            for (i in 0 until target.size) {
                cost += (target[i] - point[i]) * (target[i] - point[i])
            }
            return cost
        }

        for (target in targets) {
            if (costFunc(target) < costFunc(result)) {
                result = target
            }
        }
        return result
    }

    fun getDistance(a: List<Double>, b: List<Double>): Double {
        checkSizesAreSame(a, b)
        var result = 0.0
        for (i in 0 until a.size) {
            result += (a[i] - b[i]) * (a[i] - b[i])
        }
        return sqrt(result)
    }

    private fun checkSizesAreSame(a: List<Double>, b: List<Double>) {
        check(a.size == b.size) {
            "Sizes of passed positions are expected to be same"
        }
    }
}