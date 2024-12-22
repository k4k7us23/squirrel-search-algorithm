package com.k4k7us23.io.github.k4k7us23.ssa

import com.k4k7us23.io.github.k4k7us23.ssa.functions.TargetFunction
import kotlin.math.atan
import kotlin.math.tan

class Squirrel(
    private val targetFunction: TargetFunction,
    val position: List<Double>,
    private val params: SquirrelSearchAlgorithm.Params,
) {

    private val dimensions = targetFunction.dimensions
    private val rnd = params.random
    private val rndUtils = RandomUtils(rnd)

    init {
        check(position.size == dimensions) {
            "The squirrel position should consist of $dimensions coordinates"
        }
    }

    fun move(destination: List<Double>): Squirrel {
        check(destination.size == dimensions) {
            "Destination must consist of $dimensions coordinates"
        }
        if (checkPredatorPresent()) {
            return Squirrel(
                targetFunction,
                rndUtils.uniformVector(position.size, targetFunction.leftBound, targetFunction.rightBound),
                params
            )
        } else {
            val glidingDistance = calculateGlidingDistance()
            val moveFactor = (glidingDistance * params.glidingConstant)
            val moveVector = PositionUtils.multiply(moveFactor, PositionUtils.subtract(destination, position))
            val newPosition = PositionUtils.add(position, moveVector)

            return Squirrel(
                targetFunction,
                newPosition,
                params,
            )
        }
    }

    private fun checkPredatorPresent(): Boolean {
        return rnd.nextDouble() < params.predatorProbability
    }

    private fun calculateGlidingDistance(): Double {
        val liftCoefficient = params.liftCoefficientRange.rndValueInRange(rndUtils)
        val dragCoefficient = params.frictionalDragCoefficientRange.rndValueInRange(rndUtils)
        val glideAngle = atan(dragCoefficient / liftCoefficient)

        return params.glidingHeightLoss / tan(glideAngle)
    }
}