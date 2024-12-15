package com.k4k7us23.io.github.k4k7us23

import org.apache.commons.math3.special.Gamma
import java.util.Random
import kotlin.math.pow
import kotlin.math.sin

class RandomUtils(
    private val rnd: Random
) {

    fun randRange(leftBound: Double, rightBound: Double): Double {
        return leftBound + (rightBound - leftBound) * rnd.nextDouble()
    }

    fun uniformVector(vectorSize: Int, leftBound: Double, rightBound: Double): List<Double> {
        val result = ArrayList<Double>(vectorSize)
        repeat(vectorSize) {
            result.add(randRange(leftBound, rightBound))
        }
        return result
    }

    fun uniformVectorLevy(vectorSize: Int, leftBound: Double, rightBound: Double): List<Double> {
        val result = ArrayList<Double>(vectorSize)
        repeat(vectorSize) {
            val cur = leftBound + singleLevyValue() * (rightBound - leftBound)
            result.add(cur)
        }
        return result
    }

    fun singleLevyValue(): Double {
        val ra = rnd.nextDouble()
        val rb = rnd.nextDouble()
        val beta = 1.5

        var sigma = Gamma.gamma(1.0 + beta) * sin((Math.PI * beta) / 2.0)
        sigma /= Gamma.gamma((1.0 + beta) / 2.0) * beta * (2.0).pow((beta - 1.0) / 2)
        sigma = sigma.pow(1.0 / beta)

        return (0.01 * ra * sigma) / ((rb).pow(1.0 / beta))
    }
}