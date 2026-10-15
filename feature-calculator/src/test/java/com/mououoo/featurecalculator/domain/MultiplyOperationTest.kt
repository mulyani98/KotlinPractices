package com.mououoo.featurecalculator.domain

import junit.framework.TestCase.assertEquals
import org.junit.Before
import kotlin.test.Test

class MultiplyOperationTest{
    private lateinit var multiplyOperation: MultiplyOperation

    @Before
    fun setUp() {
        multiplyOperation = MultiplyOperation()
    }

    @Test
    fun apply_shouldReturnCorrectResult() {
        val result = multiplyOperation.apply(4.0, 3.0)
        assertEquals(12.0, result, 0.0)
    }

}