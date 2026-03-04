package com.mououoo.featurecalculator.domain

import junit.framework.TestCase.assertEquals
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertNull


class DivideOperationTest{
    private lateinit var divideOperation: DivideOperation

    @Before
    fun setUp() {
        divideOperation = DivideOperation()
    }

    @Test
    fun apply_shouldDivideCorrectResult() {
        val result = divideOperation.apply(4.0, 2.0)
        assertEquals(2.0, result!!, 0.0) // delta 0.0 for double
    }

    @Test
    fun apply_divideByZero_shouldReturnNull() {
        val result = divideOperation.apply(4.0, 0.0)
        assertNull(result)
    }

}