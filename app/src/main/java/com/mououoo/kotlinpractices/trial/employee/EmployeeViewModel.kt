package com.mououoo.kotlinpractices.trial.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn


class EmployeeViewModel(
    repository: EmployeeRepository = EmployeeRepository()
) : ViewModel() {

    private val _event = MutableSharedFlow<EmployeeEvent>()
    val event: SharedFlow<EmployeeEvent> = _event.asSharedFlow()

    val stateEmployee: StateFlow<Employee?> =
        repository
            .getEmployee()
            .onEach {
                _event.emit(
                    EmployeeEvent.EmployeeLoaded
                )
            }
            .catch { e ->
                emit(
                    Employee(
                        name = e.message.toString(),
                        nik = "",
                        history = emptyList()
                    )
                )

                _event.emit(
                    EmployeeEvent.Error(
                        e.message ?: "Unknown error"
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )
}