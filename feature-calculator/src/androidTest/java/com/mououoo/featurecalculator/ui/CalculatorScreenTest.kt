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

class CalculatorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var robot: CalculatorRobot

    @Before
    fun setup() {
        robot = CalculatorRobot(composeTestRule)
    }

    @Test
    fun typingNumbers_UpdatesExpression() {
        // Given the screen is loaded
        startCalculator()

        // When clicking numbers
        robot.clickButton("7")
        robot.clickButton("5")

        // Then the expression should show "75"
        robot.assertExpression("75")
    }

    @Test
    fun clickingClear_ResetsDisplay() {
        startCalculator()

        robot.clickButton("9")
        robot.clickButton("C")

        robot.assertExpression("")
    }

    @Test
    fun basicAddition_ShowsCorrectResult() {
        startCalculator()

        robot.clickButton("1")
        robot.clickButton("+")
        robot.clickButton("2")
        robot.clickButton("=")

        // Replace "= 3" with whatever your ViewModel logic outputs
        robot.assertResult("= 3")
    }

    private fun startCalculator() {
        composeTestRule.setContent {
            // We use the Content composable directly to control the state
            // OR use the real ViewModel if it's already integrated.
            // For a pure UI test, testing CalculatorContent is often cleaner:
            var state by remember { mutableStateOf(CalculatorUiState()) }

            CalculatorContent(
                state = state,
                onEvent = { event ->
                    // Simple mock logic to simulate ViewModel behavior
                    state = when(event) {
                        is CalculatorEvent.OnNumberClick ->
                            state.copy(expression = state.expression + event.number)
                        is CalculatorEvent.OnClearClick ->
                            CalculatorUiState()
                        // ... add other event simulations if testing pure UI
                        else -> state
                    }
                }
            )
        }
    }
}