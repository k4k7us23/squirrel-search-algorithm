package com.k4k7us23

import com.k4k7us23.io.github.k4k7us23.SquirrelSearchAlgorithm
import com.k4k7us23.io.github.k4k7us23.functions.TF1

fun main() {
    val squirrelSearchAlgorithm = SquirrelSearchAlgorithm(
        TF1(30),
        SquirrelSearchAlgorithm.Params()
    )
    println(squirrelSearchAlgorithm.run())
    println(squirrelSearchAlgorithm.getResultFitness())
}