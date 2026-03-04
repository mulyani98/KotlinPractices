package com.mououoo.featurecalculator.domain

class CalculatorUseCase(
    private val add: CalculatorOperation, // dependency-injected operation for addition
    private val subtract: CalculatorOperation,
    private val multiply: CalculatorOperation,
    private val divide: CalculatorOperation
) {

    // Main calculate function that chooses the correct operation based on operator
    fun calculate(
        operator: String, // operator
        a: Double, // first operand
        b: Double // second operand
    ): Double? { // return result or null if operator invalid / unsafe
        return when (operator) {
            "+" -> add.apply(a, b) // perform addition
            "-" -> subtract.apply(a, b)
            "*" -> multiply.apply(a, b)
            "/" -> divide.apply(a, b)
            else -> null
        }
    }
}