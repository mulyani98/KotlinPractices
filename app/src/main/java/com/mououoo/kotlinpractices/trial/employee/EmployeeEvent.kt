package com.mououoo.kotlinpractices.trial.employee

sealed interface EmployeeEvent {
    data object EmployeeLoaded : EmployeeEvent

    data class Error(
        val message: String
    ) : EmployeeEvent


}