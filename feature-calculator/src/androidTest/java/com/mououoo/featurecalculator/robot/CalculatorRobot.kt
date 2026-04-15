package com.mououoo.featurecalculator.robot

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

/*
CalculatorRobot (UI Test - Robot Pattern Helper)

What it is:
- A helper class used in UI tests to encapsulate user interactions and assertions for the Calculator screen.

What it tests:
- UI behavior of the Calculator feature through Compose testing APIs.

Explanation:
- This class implements the Robot Pattern for UI testing.
- It abstracts direct Compose testing calls into reusable and readable functions.
- UI interactions such as button clicks and assertions are centralized in a single place.
- Test cases interact with this robot instead of directly using Compose test APIs.

Purpose:
- Improve readability of UI tests by separating test logic from UI interaction details.
- Reduce code duplication across multiple test cases.
- Improve maintainability when UI structure changes.
- Provide a consistent abstraction layer over Compose testing APIs.

How it works:
- clickButton(): performs click action on a button identified by its visible text.
- assertExpression(): verifies that the expression text displayed on screen matches the expected value.
- assertResult(): verifies that the result text displayed on screen matches the expected value.

Key focus:
- UI interaction abstraction
- Test maintainability
- Readable and structured test design
*/
class CalculatorRobot(private val composeTestRule: ComposeContentTestRule) {

    // Performs a click action on a calculator button identified by its displayed text
    fun clickButton(text: String) {
        // Locates a UI node by matching the visible text and performs a click action on the found element
        composeTestRule
            .onNodeWithText(text)
            .performClick()
    }

    // Verifies that the expression text displayed on screen matches the expected value
    fun assertExpression(expected: String) {
        // Locates a UI node using the test tag "expression"
        // and verifies that the displayed text exactly matches the expected value
        composeTestRule
            .onNodeWithTag("expression")
            .assertTextEquals(expected)
    }

    // Verifies that the result text displayed on screen matches the expected value
    fun assertResult(expected: String) {
        // Locates a UI node using the test tag "expression"
        // and verifies that the displayed text exactly matches the expected value
        composeTestRule
            .onNodeWithTag("result")
            .assertTextEquals(expected)
    }

/*
onNodeWithText() - A Compose Testing API function used to:
- Find a UI element (node) based on the visible text displayed on the screen
- Commonly used for simulating user interactions like clicking buttons or verifying text
- Can be less reliable when multiple nodes contain the same text

onNodeWithTag() - A Compose Testing API function used to:
- Find a UI element (node) based on a predefined test tag (Modifier.testTag)
- Provide a unique and stable way to identify UI elements regardless of visible text
- Commonly used for assertions and stable UI testing

Key difference:
- onNodeWithText() depends on visible UI text (user-facing content)
- onNodeWithTag() depends on internal test identifier (test-only stability layer)
*/
}