package com.mououoo.kotlinpractices.trial.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mououoo.kotlinpractices.util.SafeLogKotlin
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

data class ApplicationTeam(
    val name: String,
    val no: String,
    val history: List<String>
)


class EmployeeTrialViewModel : ViewModel() {
    private val _stateApplicationTeam = MutableStateFlow<ApplicationTeam?>(null)

    val stateApplicationTeam: StateFlow<ApplicationTeam?> = _stateApplicationTeam.asStateFlow()

    init {
        whoApplicationTeam()
    }

    private fun whoApplicationTeam() {
        viewModelScope.launch {

            supervisorScope {
                val memberName = async {
                    delay(2000)
//                    throw Exception ("Failed to read the name") // example throw an exception on child
                    val result = "Mulyani"
                    SafeLogKotlin.d("EmployeeTrialViewModel", "result: $result")

                    result
                }.apply {
                    invokeOnCompletion { cause ->
                        SafeLogKotlin.e("EmployeeTrialViewModel", cause?.message.toString())
                    }
                }

                val memberNo = async {
                    delay(5000)

                    val result = "89988998"
                    SafeLogKotlin.d("EmployeeTrialViewModel", "result: $result")

                    result
                }.apply {
                    invokeOnCompletion { cause ->
                        SafeLogKotlin.e("EmployeeTrialViewModel", cause?.message.toString())
                    }
                }

                val memberHistory = async {
                    delay(2000)

                    val result = listOf("Sleep", "Sleep", "Sleep")
                    SafeLogKotlin.d("EmployeeTrialViewModel", "result: $result")

                    result
                }.apply {
                    invokeOnCompletion { cause ->
                        SafeLogKotlin.e("EmployeeTrialViewModel", cause?.message.toString())
                    }
                }


                try {
                    _stateApplicationTeam.value = ApplicationTeam(
                        name = memberName.await(),
                        no = memberNo.await(),
                        history = memberHistory.await()
                    )
                } catch (e: Exception) {
                    _stateApplicationTeam.value = ApplicationTeam(
                        name = e.message.toString(),
                        no = "",
                        history = listOf()
                    )
                }
            }

//            cancel() // cancel all child
        }
    }
}