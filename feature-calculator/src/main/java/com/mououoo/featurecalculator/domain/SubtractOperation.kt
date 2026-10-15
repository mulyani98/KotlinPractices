package com.mououoo.featurecalculator.domain

// Concrete implementation of CalculatorOperation for subtraction
class SubtractOperation : CalculatorOperation {
    // Overrides the apply function defined in the interface
    override fun apply(a: Double, b: Double): Double =
        // Returns the difference between the first and second input values
        a - b
}