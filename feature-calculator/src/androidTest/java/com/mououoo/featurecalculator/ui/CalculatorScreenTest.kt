package com.mououoo.featurecalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import com.mououoo.featurecalculator.robot.CalculatorRobot
import com.mououoo.featurecalculator.viewmodel.CalculatorEvent
import com.mououoo.featurecalculator.viewmodel.CalculatorUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*
CalculatorScreenTest (UI Test - Compose UI Layer)

What it tests:
- Verifies UI behavior of the Calculator screen using Compose UI testing.

Explanation:
- This test class validates user interactions on the Calculator UI.
- It uses a Robot Pattern (CalculatorRobot) to abstract UI actions and assertions.
- The tests simulate real user behavior such as clicking buttons and verifying displayed results.
- A simplified in-test state implementation is used to mimic ViewModel behavior.

Test strategy:
- UI is rendered using CalculatorContent directly.
- State is managed locally using mutableStateOf for controlled testing.
- Events are manually handled to simulate ViewModel logic.
- Assertions are performed through robot methods for readability and reuse.

Test coverage:
- Number input updates expression correctly
- Clear action resets UI state
- Basic addition operation produces correct result

Key focus:
- UI interaction correctness
- State rendering validation
- User flow simulation (click → state update → UI assertion)
*/

class CalculatorScreenTest {

    // JUnit rule that sets up Compose testing environment
    @get:Rule
    val composeTestRule = createComposeRule()

    // Robot helper used to interact with UI and perform assertions
    private lateinit var robot: CalculatorRobot

    // Runs before each test case
    @Before
    fun setup() {
        // Initialize robot with Compose test rule
        robot = CalculatorRobot(composeTestRule)
    }

    // This test ensures that when number buttons are clicked,
    // the calculator expression is updated correctly by appending input values.
    @Test
    fun typingNumbers_UpdatesExpression() {
        // Given the calculator screen is displayed
        startCalculator()

        // When user clicks number buttons
        robot.clickButton("7")
        robot.clickButton("5")

        // Then expression should reflect entered numbers
        robot.assertExpression("75")
    }

    // This test ensures that when the clear button is clicked,
    // the calculator expression is reset to an empty state.
    @Test
    fun clickingClear_ResetsDisplay() {
        startCalculator()

        // When user enters number and clears input
        robot.clickButton("9")
        robot.clickButton("C")

        // Then expression should be reset to empty state
        robot.assertExpression("")
    }

    // This test ensures that basic addition operation produces the correct result
    // when the user inputs two numbers and presses the equal button.
    @Test
    fun basicAddition_ShowsCorrectResult() {
        startCalculator()

        // When user performs addition operation
        robot.clickButton("1")
        robot.clickButton("+")
        robot.clickButton("2")
        robot.clickButton("=")

        // Then result should display correct calculation output
        robot.assertResult("= 3")
    }

    // Sets up the calculator UI for testing by providing a controllable state
    // and simulating ViewModel behavior inside the test environment.
    private fun startCalculator() {
        // Render the composable UI in test environment
        composeTestRule.setContent {

            // Create local state to simulate UI state (replaces ViewModel in test)
            var state by remember { mutableStateOf(CalculatorUiState()) }

            // Display UI
            CalculatorContent(
                state = state,

                // Handle UI events manually to simulate business logic
                onEvent = { event ->
                    state = when (event) {

                        // When a number button is clicked,
                        // append the number to the current expression
                        is CalculatorEvent.OnNumberClick -> {
                            state.copy(
                                expression = state.expression + event.number
                            )
                        }

                        // When an operator button is clicked,
                        // append the operator to the current expression
                        is CalculatorEvent.OnOperatorClick -> {
                            state.copy(
                                expression = state.expression + event.operator
                            )
                        }

                        // When clear button is clicked,
                        // reset the state to initial value
                        is CalculatorEvent.OnClearClick -> {
                            CalculatorUiState()
                        }

                        // When equal button is clicked,
                        // evaluate the expression and update the result
                        is CalculatorEvent.OnEqualClick -> {
                            val result = evaluateExpression(state.expression)
                            state.copy(
                                result = "= $result"
                            )
                        }
                    }
                }
            )
        }
    }

    // Evaluates a simple mathematical expression and returns the result
    // Supports basic addition by splitting the expression using "+" operator.
    private fun evaluateExpression(expression: String): Int {
        return try {
            expression
                .split("+") // Split expression into parts based on "+" operator (e.g., "1+2" → ["1", "2"])
                .filter { it.isNotBlank() } // Remove any empty values to avoid parsing errors
                .sumOf { it.toInt() } // Convert each part to integer and sum all values
        } catch (_: Exception) {
            // Return 0 if the expression is invalid or parsing fails
            0
        }
    }
}