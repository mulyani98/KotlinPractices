package com.mououoo.featurecalculator.domain

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class SubtractOperationTest {
    private lateinit var subtractOperation: SubtractOperation

    @Before
    fun setUp() {
        subtractOperation = SubtractOperation()
    }

    @Test
    fun apply_shouldReturnCorrectResult() {
        val result = subtractOperation.apply(4.0, 3.0)
        TestCase.assertEquals(1.0, result, 0.0)
    }

}