package com.mououoo.featurecalculator.domain

class CalculatorUseCase(
    private val add: CalculatorOperation,
    private val subtract: CalculatorOperation,
    private val multiply: CalculatorOperation,
    private val divide: CalculatorOperation
) {

    fun calculate(
        operator: String,
        a: Double,
        b: Double
    ): Double? {
        return when (operator) {
            "+" -> add.apply(a, b)
            "-" -> subtract.apply(a, b)
            "*" -> multiply.apply(a, b)
            "/" -> divide.apply(a, b)
            else -> null
        }
    }
}