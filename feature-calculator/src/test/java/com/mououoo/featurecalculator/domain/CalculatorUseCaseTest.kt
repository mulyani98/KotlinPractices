package com.mououoo.featurecalculator.domain

import junit.framework.TestCase.assertEquals
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertNull

class CalculatorUseCaseTest {
    private lateinit var useCase: CalculatorUseCase

    // Mock implementations, only test CalculatorUseCase
    private val addOperation = object : CalculatorOperation {
        override fun apply(a: Double, b: Double): Double? = a + b
    }

    private val subtractOperation = object : CalculatorOperation {
        override fun apply(a: Double, b: Double): Double? = a - b
    }

    private val multiplyOperation = object : CalculatorOperation {
        override fun apply(a: Double, b: Double): Double? = a * b
    }

    private val divideOperation = object : CalculatorOperation {
        override fun apply(a: Double, b: Double): Double? =
            if (b == 0.0) null else a / b
    }

    @Before
    fun setUp() {
        useCase = CalculatorUseCase(
            addOperation,
            subtractOperation,
            multiplyOperation,
            divideOperation
        )
    }

    @Test
    fun calculate_addition_shouldReturnCorrectResult() {
        val result = useCase.calculate("+", 4.0, 3.0)
        assertEquals(7.0, result!!, 0.0)
    }

    @Test
    fun calculate_subtraction_shouldReturnCorrectResult() {
        val result = useCase.calculate("-", 5.0, 3.0)
        assertEquals(2.0, result!!, 0.0)
    }

    @Test
    fun calculate_multiplication_shouldReturnCorrectResult() {
        val result = useCase.calculate("*", 4.0, 3.0)
        assertEquals(12.0, result!!, 0.0)
    }

    @Test
    fun calculate_division_shouldReturnCorrectResult() {
        val result = useCase.calculate("/", 6.0, 3.0)
        assertEquals(2.0, result!!, 0.0)
    }

    @Test
    fun calculate_divideByZero_shouldReturnNull() {
        val result = useCase.calculate("/", 6.0, 0.0)
        assertNull(result)
    }

    @Test
    fun calculate_invalidOperator_shouldReturnNull() {
        val result = useCase.calculate("%", 4.0, 2.0)
        assertNull(result)
    }

}