package com.mououoo.featurecalculator.domain

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlin.test.Test
import kotlin.test.assertNull

/*
CalculatorUseCaseTest (Domain - Use Case Layer)

What it tests:
- Verifies that the correct operation is selected and executed based on the operator.

Explanation:
- This test does NOT verify calculation logic.
- It ensures the UseCase delegates to the correct operation.
- Uses fake implementations to isolate behavior.

Example:
“If operator is '+', does it call AddOperation?”

Key focus:
- Operator → correct operation mapping
- Interaction between components
- Handling invalid operators
 */

class CalculatorUseCaseTest {

    // Fake implementation used only for testing
    // It returns a predefined result and records if it was called
    private class FakeOperation(
        private val result: Double? // predefined value returned when apply() is called
    ) : CalculatorOperation {
        var called = false // indicates whether this operation was executed
        override fun apply(a: Double, b: Double): Double? {
            called = true // mark that this operation is executed
            return result // return the predefined result (no real calculation)
        }
    }

    // This test ensures that when the '+' operator is used,
    // the UseCase calls only the add operation and no other operations.
    @Test
    fun plusOperator_callsAddOnly() {
        // Create fake operations with predefined results
        val add = FakeOperation(5.0)
        val subtract = FakeOperation(0.0)
        val multiply = FakeOperation(0.0)
        val divide = FakeOperation(0.0)

        // Inject fake dependencies into UseCase
        val useCase = CalculatorUseCase(add, subtract, multiply, divide)

        // Execute calculation with "+" operator
        // The input values (2 and 3) are only for simulation.
        // The result comes from FakeOperation, not real calculation logic.
        val result = useCase.calculate("+", 2.0, 3.0)

        // Verify result is correct
        assertEquals(5.0, result)

        // It ensures that only the correct operation is executed, and no other operations are accidentally called
        // Verify ONLY add operation is called
        assertTrue(add.called)

        // Verify other operations are NOT called
        assertFalse(subtract.called)
        assertFalse(multiply.called)
        assertFalse(divide.called)
    }

    // This test ensures that when an invalid operator is used,
    // the UseCase returns null and does not execute any operation.
    @Test
    fun invalidOperator_returnsNull_andNoOperationCalled() {
        // Create fake operations with predefined results
        val add = FakeOperation(0.0)
        val subtract = FakeOperation(0.0)
        val multiply = FakeOperation(0.0)
        val divide = FakeOperation(0.0)

        val useCase = CalculatorUseCase(add, subtract, multiply, divide)

        // Execute calculation with an unsupported operator
        val result = useCase.calculate("%", 2.0, 3.0)

        // Verify result is null for invalid operator
        assertNull(result)

        // Verify no operation is executed
        assertFalse(add.called)
        assertFalse(subtract.called)
        assertFalse(multiply.called)
        assertFalse(divide.called)
    }

}