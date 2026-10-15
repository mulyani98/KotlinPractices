package com.mououoo.featurecalculator.viewmodel

import com.mououoo.featurecalculator.domain.CalculatorOperation
import com.mououoo.featurecalculator.domain.CalculatorUseCase
import junit.framework.TestCase.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals

/* CalculatorViewModelTest (Presentation Layer)

What it tests:
- Verifies how the ViewModel reacts to user actions and updates UI state.

Explanation:
- This test does NOT verify calculation logic or operation selection.
- It simulates user input and checks UI state changes.
- Uses fake UseCase behavior to control outcomes.

Example:
- “When user presses '=', is the result displayed correctly?”

Key focus:
- User events (input, operator, equal)
- State updates (result, error)
- UI behavior

Main Differences:
- Operation Test checks calculation logic
- UseCase Test checks operation selection
- ViewModel Test checks UI state and user flow
*/

class CalculatorViewModelTest {

    // Fake implementation used to control the result returned by the UseCase
    private class FakeOperation(
        private val result: Double? // predefined result that will always be returned
    ) : CalculatorOperation {

        override fun apply(a: Double, b: Double): Double? {
            // Ignore input values (a, b)
            // Always return the predefined result (ignore real logic) to simulate specific scenarios
            return result
        }
    }

    // This test ensures that when "=" is pressed,
    // the ViewModel updates the result based on the UseCase output.
    @Test
    fun onEqual_calculatesResult() {
        // Create UseCase with fake operations
        // "+" operation is configured to return 10.0
        val useCase = CalculatorUseCase(
            add = FakeOperation(10.0),
            subtract = FakeOperation(0.0),
            multiply = FakeOperation(0.0),
            divide = FakeOperation(0.0)
        )

        // Create ViewModel with injected UseCase
        val viewModel = CalculatorViewModel(useCase)

        // Simulate user input: first number "5"
        viewModel.onEvent(CalculatorEvent.OnNumberClick("5"))

        // Simulate user selecting "+" operator
        viewModel.onEvent(CalculatorEvent.OnOperatorClick("+"))

        // Simulate user input: second number "5"
        viewModel.onEvent(CalculatorEvent.OnNumberClick("5"))

        // Simulate pressing "=" button
        viewModel.onEvent(CalculatorEvent.OnEqualClick)
        // Inside OnEqualClick, the ViewModel calls UseCase.calculate()
        // Since FakeOperation is used, it directly returns the predefined result (10.0)

        // Retrieve the latest UI state from ViewModel
        val state = viewModel.uiState.value
        // Contains updated UI state after "=" is pressed (including the result, here 10.0).

        // Verify that the result matches the predefined value from FakeOperation
        // The value "10" comes from the fake implementation, not real calculation
        assertEquals(" 10", state.result)
    }

    // This test ensures that when an invalid calculation occurs (e.g., divide by zero),
    // the ViewModel enters an error state and displays an error message.
    @Test
    fun onEqual_divideByZero_setsError() {
        // Create UseCase where division returns null
        // This simulates an error condition
        val useCase = CalculatorUseCase(
            add = FakeOperation(0.0),
            subtract = FakeOperation(0.0),
            multiply = FakeOperation(0.0),
            divide = FakeOperation(null) // null result triggers error handling
        )

        // Create ViewModel with injected UseCase
        val viewModel = CalculatorViewModel(useCase)

        // Simulate user input: first number "5"
        viewModel.onEvent(CalculatorEvent.OnNumberClick("5"))

        // Simulate selecting "/" operator
        viewModel.onEvent(CalculatorEvent.OnOperatorClick("/"))

        // Simulate user input: second number "0"
        viewModel.onEvent(CalculatorEvent.OnNumberClick("0"))

        // Simulate pressing "=" button
        viewModel.onEvent(CalculatorEvent.OnEqualClick)

        // Retrieve the latest UI state
        val state = viewModel.uiState.value

        // Verify that ViewModel enters error state
        assertTrue(state.isError)

        // Verify that error message is displayed
        assertEquals("Error", state.result)
    }
}