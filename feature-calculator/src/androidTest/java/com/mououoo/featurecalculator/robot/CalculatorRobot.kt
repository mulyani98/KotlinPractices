package com.mououoo.featurecalculator.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

class CalculatorRobot(private val composeTestRule: ComposeContentTestRule) {

    fun clickButton(text: String) {
        composeTestRule.onNodeWithText(text).performClick()
    }

    fun assertExpression(expected: String) {
        composeTestRule.onNodeWithText(expected).assertIsDisplayed()
    }

    fun assertResult(expected: String) {
        composeTestRule.onNodeWithText(expected).assertIsDisplayed()
    }
}