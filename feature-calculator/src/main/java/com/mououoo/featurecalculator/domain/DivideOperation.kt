package com.mououoo.featurecalculator.domain

// Concrete implementation of CalculatorOperation for division
class DivideOperation : CalculatorOperation {
    // Overrides the apply function defined in the interface
    override fun apply(a: Double, b: Double): Double? {
        // Prevent division by zero to avoid invalid mathematical result
        return if (b == 0.0)
            // Return null to indicate an invalid operation
            null
        else
            // Perform safe division when divisor is not zero
            a / b
    }
}