package com.mououoo.kotlinpractices

import kotlin.test.Test
import kotlin.test.assertEquals

class PlusTest() {

    @Test
    fun testPlus_twoPositiveNumbers_returnsCorrectResult() {
        val plus = Plus()
        val result = plus.plusFunction(2.0, 3.0)
        assertEquals(5.0, result, 0.0)
    }

    @Test
    fun testPlus_withZero_returnsCorrectResult() {
        val plus = Plus()
        val result = plus.plusFunction(0.0, 5.0)
        assertEquals(5.0, result, 0.0)
    }

    @Test
    fun testPlus_twoNegativeNumbers_returnsCorrectResult() {
        val plus = Plus()
        val result = plus.plusFunction(-2.0, -3.0)
        assertEquals(-5.0, result, 0.0)
    }

    @Test
    fun testPlus_positiveAndNegative_returnsCorrectResult() {
        val plus = Plus()
        val result = plus.plusFunction(5.0, -3.0)
        assertEquals(2.0, result, 0.0)
    }
}