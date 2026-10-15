package com.mououoo.featurecalculator.viewmodel

// Represents all possible user actions in the calculator
sealed class CalculatorEvent {
    // Event triggered when a number button is clicked
    data class OnNumberClick(val number: String) : CalculatorEvent()

    // Event triggered when an operator button (+, -, *, /) is clicked
    data class OnOperatorClick(val operator: String) : CalculatorEvent()

    // Event triggered when "=" button is clicked
    object OnEqualClick : CalculatorEvent()

    // Event triggered when "C" (clear) button is clicked
    object OnClearClick : CalculatorEvent()
}