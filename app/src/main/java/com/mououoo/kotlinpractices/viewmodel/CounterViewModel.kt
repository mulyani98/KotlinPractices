package com.mououoo.kotlinpractices.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mououoo.kotlinpractices.model.CounterModel

class CounterViewModel : ViewModel() {
    private val counterModel = CounterModel()
    private val x = MutableLiveData(counterModel.valueX)
    private val y = MutableLiveData(counterModel.valueY)
    private val z = MutableLiveData(counterModel.valueZ)

    val valueX: LiveData<Int> get() = x
    val valueY: LiveData<Int> get() = y
    val valueZ: LiveData<Int> get() = z

    fun increaseValueByFive() {
        counterModel.increaseValueByFive()
        x.value = counterModel.valueX
        y.value = counterModel.valueY
        z.value = counterModel.valueZ
    }
}