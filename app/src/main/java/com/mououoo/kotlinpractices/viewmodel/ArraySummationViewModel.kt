package com.mououoo.kotlinpractices.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mououoo.kotlinpractices.model.ArraySummation

class ArraySummationViewModel : ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    fun calculateSum() {
        val array1 = intArrayOf(1, 2, 3, 4)
        val array2 = intArrayOf(5, 6, 7)
        val resultArray = arraySummation.sumArrays(array1, array2)
        _result.value = resultArray.joinToString(", ")
    }

    // ArraySummation interface
    private val arraySummation = object : ArraySummation {
        override fun sumArrays(a: IntArray, b: IntArray): IntArray {
            val maxLength = maxOf(a.size, b.size)
            val result = IntArray(maxLength)
            for (i in 0 until maxLength) {
                val valueA = a.getOrNull(i) ?: 0
                val valueB = b.getOrNull(i) ?: 0
                result[i] = valueA + valueB
            }
            return result
        }
    }
}