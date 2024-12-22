package com.k4k7us23.io.github.k4k7us23.ssa

import com.k4k7us23.io.github.k4k7us23.ssa.functions.TargetFunction
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

class SquirrelSearchAlgorithm(
    private val targetFunction: TargetFunction,
    private val params: Params,
) {

    class Params(
        val predatorProbability: Double = 0.1,
        val glidingConstant: Double = 1.9,
        val random: Random = Random(),
        val liftCoefficientRange: DoubleRange = DoubleRange(0.675, 1.5),
        val frictionalDragCoefficientRange: DoubleRange = DoubleRange(0.60, 0.60),
        val glidingHeightLoss: Double = 0.4,
        val iterationCount: Int = 10000,
        val squirrelsCount: Int = 50,
    ) {
        init {
            check(predatorProbability in 0.0..1.0) {
                "predatorProbability should be in [0.0; 1.0]"
            }
        }
    }

    private val rndUtils = RandomUtils(params.random)
    private var squirrels = ArrayList<SortableSquirrel>()

    private var resultFitness: Double? = null
    private var resultPoint: List<Double>? = null
    private var iterationNumber = 0

    fun run(): List<Double> {
        squirrels.clear()
        resultFitness = null
        iterationNumber = 0
        repeat(params.squirrelsCount) {
            val initialSquirrel = Squirrel(
                targetFunction = targetFunction,
                position = rndUtils.uniformVector(
                    targetFunction.dimensions,
                    targetFunction.leftBound,
                    targetFunction.rightBound
                ),
                params = params,
            )
            squirrels.add(SortableSquirrel(initialSquirrel))
        }
        repeat(params.iterationCount) {
            iteration()
        }
        return requireNotNull(resultPoint)
    }

    fun getResultFitness(): Double? {
        return resultFitness
    }

    private fun relaxResult(curResultFitness: Double, curPoint: List<Double>) {
        val localResultFitness = resultFitness
        if (localResultFitness == null || localResultFitness > curResultFitness) {
            resultFitness = curResultFitness
            resultPoint = curPoint
        }
    }

    private fun iteration() {
        squirrels.sort()
        val hickoryTreeSquirrel = squirrels[0]
        val acornTreeSquirrels = squirrels.subList(1, 4)
        val normalTreesSquirrel = squirrels.subList(4, squirrels.size)
        relaxResult(hickoryTreeSquirrel.fitness(), hickoryTreeSquirrel.squirrel.position)

        val newSquirrels = ArrayList<SortableSquirrel>()
        newSquirrels.add(hickoryTreeSquirrel)

        // acorn -> hickory (case 1 in section 4.4 of the paper)
        acornTreeSquirrels.forEach { acornTreeSquirrel ->
            val newSquirrel = acornTreeSquirrel.squirrel.move(hickoryTreeSquirrel.squirrel.position)
            newSquirrels.add(SortableSquirrel(newSquirrel))
        }

        // normal -> accorn or hickory (cases 2 and 3)
        normalTreesSquirrel.forEach { normalTreeSquirrel ->
            val moveToHickory = params.random.nextBoolean()
            if (moveToHickory) { // case 3
                val newSquirrel = normalTreeSquirrel.squirrel.move(hickoryTreeSquirrel.squirrel.position)
                newSquirrels.add(SortableSquirrel(newSquirrel))
            } else { // case 2
                val nearestAcornTreePosition = PositionUtils.getClosest(
                    normalTreeSquirrel.squirrel.position,
                    normalTreesSquirrel.map { it.squirrel.position }
                )
                val newSquirrel = normalTreeSquirrel.squirrel.move(nearestAcornTreePosition)
                newSquirrels.add(SortableSquirrel(newSquirrel))
            }
        }

        // check for season change
        val seasonalConstants = acornTreeSquirrels.map { accornTreeSquirrel ->
            PositionUtils.getDistance(accornTreeSquirrel.squirrel.position, hickoryTreeSquirrel.squirrel.position)
        }

        val winterIsOver = seasonalConstants.min() < getSeasonalMinValue()
        if (winterIsOver) {
            relocateSquirrelsUsingLevyDistribution()
        } else {
            squirrels = newSquirrels
        }

        iterationNumber++
    }

    private fun relocateSquirrelsUsingLevyDistribution() {
        squirrels.clear()
        repeat(params.squirrelsCount) {
            val initialSquirrel = Squirrel(
                targetFunction = targetFunction,
                position = rndUtils.uniformVectorLevy(
                    targetFunction.dimensions,
                    targetFunction.leftBound,
                    targetFunction.rightBound
                ),
                params = params,
            )
            squirrels.add(SortableSquirrel(initialSquirrel))
        }
    }

    private fun getSeasonalMinValue(): Double {
        val power = iterationNumber / (params.iterationCount / 2.5)
        return (1e-6) / (365.0.pow(power))
    }

    private inner class SortableSquirrel(
        val squirrel: Squirrel
    ) : Comparable<SortableSquirrel> {

        private var fitnessCache: Double? = null

        fun fitness(): Double {
            val cache = fitnessCache
            return if (cache == null) {
                val result = targetFunction.call(squirrel.position)
                fitnessCache = result
                result
            } else {
                cache
            }
        }

        override fun compareTo(other: SortableSquirrel): Int {
            val selfFitness = fitness()
            val otherFitness = other.fitness()
            return selfFitness.compareTo(otherFitness)
        }
    }

}