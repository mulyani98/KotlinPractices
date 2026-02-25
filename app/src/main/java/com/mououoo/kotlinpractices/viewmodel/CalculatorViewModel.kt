package com.mououoo.kotlinpractices.viewmodel

import androidx.lifecycle.ViewModel
import com.mououoo.kotlinpractices.Plus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel: ViewModel() {

    private val plus = Plus()

    private val _result = MutableStateFlow(0.0) // the mutable ones can be changed, the ones here are private
    val result: StateFlow<Double> = _result // immutable, so that it cannot be accessed and changed outside vm

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    fun onPlusClicked(a: Double, b: Double){
       _result.value = plus.plusFunction(a,b)
    }

    fun onNumberClicked(number: String) {
        _inputText.value = number
    }
}
