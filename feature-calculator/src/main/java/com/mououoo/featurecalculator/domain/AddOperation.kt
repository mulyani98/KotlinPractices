package com.mououoo.featurecalculator.domain

// Concrete implementation of CalculatorOperation for addition
class AddOperation : CalculatorOperation {
    // Overrides the apply function defined in the interface
    override fun apply(a: Double, b: Double): Double =
        a + b // Returns the sum of the two input values
}