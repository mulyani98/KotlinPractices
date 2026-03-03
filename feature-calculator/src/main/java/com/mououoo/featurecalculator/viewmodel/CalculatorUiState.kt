package com.mououoo.featurecalculator.viewmodel

// Represents the entire UI state of the calculator screen
data class CalculatorUiState(
    // Full expression displayed to the user (e.g., "7+5")
    val expression: String = "",

    // Result displayed under the expression (e.g., "= 12")
    val result: String = "",

    // Stores the first number before an operator is selected
    val firstOperand: String = "",

    // Stores the selected operator (+, -, *, /)
    val operator: String = "",

    // Stores the number currently being typed
    val currentInput: String = "",

    // Indicates whether the calculator is currently in error state
    val isError: Boolean = false
)