package com.mououoo.featurecalculator.domain

// Concrete implementation of CalculatorOperation for multiplication
class MultiplyOperation : CalculatorOperation {
    // Overrides the apply function defined in the interface
    override fun apply(a: Double, b: Double): Double =
        // Returns the multiply of the two input values
        a * b
}