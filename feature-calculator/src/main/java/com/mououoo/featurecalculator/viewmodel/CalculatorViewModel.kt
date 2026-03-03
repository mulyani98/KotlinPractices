package com.mououoo.featurecalculator.viewmodel

import androidx.lifecycle.ViewModel
import com.mououoo.featurecalculator.domain.AddOperation
import com.mououoo.featurecalculator.domain.CalculatorUseCase
import com.mououoo.featurecalculator.domain.DivideOperation
import com.mououoo.featurecalculator.domain.MultiplyOperation
import com.mououoo.featurecalculator.domain.SubtractOperation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel(
    // Injects CalculatorUseCase with default operation implementations
    private val useCase: CalculatorUseCase = CalculatorUseCase(
        add = AddOperation(),
        subtract = SubtractOperation(),
        multiply = MultiplyOperation(),
        divide = DivideOperation()
    )
) : ViewModel() {

    // Internal mutable state holder
    private val _uiState = MutableStateFlow(CalculatorUiState())

    // Public immutable state exposed to UI
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    // Handles all incoming UI events
    fun onEvent(event: CalculatorEvent) {
        when (event) {
            // Triggered when number button is clicked
            is CalculatorEvent.OnNumberClick -> onNumber(event.number)

            // Triggered when operator button is clicked
            is CalculatorEvent.OnOperatorClick -> onOperator(event.operator)

            // Triggered when equal button is clicked
            CalculatorEvent.OnEqualClick -> onEqual()

            // Triggered when clear button is clicked
            CalculatorEvent.OnClearClick -> onClear()
        }
    }

    // Handles number input logic
    private fun onNumber(number: String) {
        // Get current state snapshot
        val current = _uiState.value

        // If currently in error state, reset and start fresh
        if (current.isError) {
            _uiState.value = CalculatorUiState(
                currentInput = number
            )
            return
        }

        // Prevent leading zero duplication
        val newInput =
            if (current.currentInput == "0") number
            else current.currentInput + number

        // Update state with new input and rebuilt expression
        _uiState.value = current.copy(
            currentInput = newInput,
            expression = buildExpression(
                current.firstOperand,
                current.operator,
                newInput
            ),
            result = ""
        )
    }

    // Handles operator input logic
    private fun onOperator(operator: String) {
        // Get current state snapshot
        val current = _uiState.value

        // Ignore if no number has been entered
        if (current.currentInput.isBlank()) return

        // Save first operand and selected operator
        _uiState.value = current.copy(
            firstOperand = current.currentInput,
            operator = operator,
            currentInput = "",
            expression = current.currentInput + operator,
            result = ""
        )
    }

    // Handles equal button logic
    private fun onEqual() {
        // Get current state snapshot
        val current = _uiState.value

        // Convert operands to Double safely
        val first = current.firstOperand.toDoubleOrNull()
        val second = current.currentInput.toDoubleOrNull()

        // Validate operands and operator
        if (first == null || second == null || current.operator.isBlank()) {
            setError()
            return
        }

        // Perform calculation through use case
        val resultValue = useCase.calculate(current.operator, first, second)

        // If calculation failed (e.g., division by zero), show error
        if (resultValue == null) {
            setError()
            return
        }

        // Format result for display
        val formatted = formatResult(resultValue)

        // Update state with calculated result
        _uiState.value = current.copy(
            result = "=  $formatted"
        )
    }

    // Clears entire calculator state
    private fun onClear() {
        _uiState.value = CalculatorUiState()
    }

    // Sets calculator into error state
    private fun setError() {
        _uiState.value = CalculatorUiState(
            expression = "",
            result = "Error",
            isError = true
        )
    }

    // Builds display expression dynamically
    private fun buildExpression(
        first: String,
        operator: String,
        second: String
    ): String {
        return when {
            // If no first operand, show only current input
            first.isBlank() -> second

            // If no operator, show only current input
            operator.isBlank() -> second

            // If second operand not entered yet, show first + operator
            second.isBlank() -> "$first$operator"

            // Otherwise show full expression
            else -> "$first$operator$second"
        }
    }

    // Formats result to remove unnecessary decimal part
    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0) {
            // If whole number, convert to Long
            value.toLong().toString()
        } else {
            // Otherwise keep decimal value
            value.toString()
        }
    }
}