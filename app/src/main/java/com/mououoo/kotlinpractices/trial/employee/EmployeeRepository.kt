package com.mououoo.kotlinpractices.trial.employee

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlin.time.Duration.Companion.milliseconds

class EmployeeRepository {
    fun getEmployee(): Flow<Employee> {

        val name = flow {
            delay(2000.milliseconds)
            emit("Mulyani")
            emit("Aser")
            emit("Kholish")
            throw IllegalStateException("boom")
        }
            .take(1)
            .map { name ->
                name.uppercase()
            }
            .filter { name ->
                name.length > 5
            }
            .transform { name ->
                emit("$name Muu")
            }

        val nik = flow {
            delay(2000.milliseconds)

            val result = "21040006"
            Log.d("EmployeeRepository", "NIK: $result")

            emit(result)
        }

        val history = flow {
            delay(2000.milliseconds)

            val result = listOf(
                "iPrint",
                "Finder",
                "Creative",
            )

            Log.d("EmployeeRepository", "History: $result")

            emit(result)
        }
            .transform { history ->
                emit(
                    history + "Smart Panel"
                )
            }

        return combine(
            name,
            nik,
            history
        ) { name, nik, history ->
            Employee(
                name = name,
                nik = nik,
                history = history
            )
        }
    }
}