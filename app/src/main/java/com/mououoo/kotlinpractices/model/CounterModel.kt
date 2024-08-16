package com.mououoo.kotlinpractices.model

class CounterModel {
    var valueX: Int = 0
        private set

    var valueY: Int = 0
        private set

    var valueZ: Int = 0
        private set

    fun increaseValueByFive(){
        valueX += 5
        valueY += 5
        valueZ += 5
    }

    fun resetValueToDefault(){
        valueX = 0
        valueY = 0
        valueZ = 0
    }
}