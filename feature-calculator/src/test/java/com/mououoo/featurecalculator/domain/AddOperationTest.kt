package com.mououoo.featurecalculator.domain

import junit.framework.TestCase.assertEquals
import org.junit.Before
import kotlin.test.Test

/*
AddOperationTest (Domain - Operation Layer)

What it tests:
- Verifies the correctness of the calculation logic.

Explanation:
- This test ensures that the operation itself works correctly.
- It checks pure mathematical behavior (e.g., addition, subtraction).
- No dependencies involved.

Example:
- “Does 4 + 3 return 7?”

Key focus:
- Business logic (calculation)
- Deterministic result
 */
class AddOperationTest {

    // Declare variable for the class under test (AddOperation)
    // 'lateinit' means it will be initialized later (not at declaration time)
    private lateinit var addOperation: AddOperation

    @Before
    fun setUp() {
        // Initialize the AddOperation before each test runs
        addOperation = AddOperation()
    }

    // This test ensures that AddOperation correctly performs addition
    @Test
    fun apply_shouldReturnCorrectResult() {
        // Call the function with sample input values
        val result = addOperation.apply(4.0, 3.0)

        // Verify that the result of 4 + 3 equals 7
        // The last parameter (0.0) is delta, used for comparing double values
        assertEquals(7.0, result, 0.0)
    }
}