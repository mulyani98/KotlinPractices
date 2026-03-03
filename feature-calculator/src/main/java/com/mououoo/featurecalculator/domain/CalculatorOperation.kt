package com.mououoo.featurecalculator.domain

// Defines a contract for all calculator operations
interface CalculatorOperation {

    // Applies a mathematical operation to two Double values
    // Returns a nullable Double to safely handle invalid operations (e.g., divide by zero)
    fun apply(a: Double, b: Double): Double?
}