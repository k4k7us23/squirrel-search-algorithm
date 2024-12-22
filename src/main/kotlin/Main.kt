package com.k4k7us23

import com.k4k7us23.io.github.k4k7us23.SquirrelSearchAlgorithm
import com.k4k7us23.io.github.k4k7us23.functions.TF1
import com.k4k7us23.io.github.k4k7us23.functions.TF2
import com.k4k7us23.io.github.k4k7us23.functions.TF3
import com.k4k7us23.io.github.k4k7us23.functions.TargetFunction

fun handleFunction(targetFunction: TargetFunction) {
    val squirrelSearchAlgorithm = SquirrelSearchAlgorithm(
        targetFunction,
        SquirrelSearchAlgorithm.Params()
    )
    println("Min point: ${squirrelSearchAlgorithm.run()}")
    println("Min value: ${squirrelSearchAlgorithm.getResultFitness()}")
}

fun main() {
    println("Executing SSA for TF1")
    handleFunction(TF1(30))

    println("Executing SSA for TF2")
    handleFunction(TF2(30))

    println("Executing SSA for TF3")
    handleFunction(TF3(30))
}