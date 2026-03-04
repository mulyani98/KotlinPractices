package com.mououoo.featurecalculator.domain

import junit.framework.TestCase
import org.junit.Before
import kotlin.test.Test

class AddOperationTest {

    private lateinit var addOperation: AddOperation

    @Before
    fun setUp() {
        addOperation = AddOperation()
    }

    @Test
    fun apply_shouldReturnCorrectResult() {
        val result = addOperation.apply(4.0, 3.0)
        TestCase.assertEquals(7.0, result, 0.0)
    }
}