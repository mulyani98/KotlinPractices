package com.mououoo.kotlinpractices.trial.employee

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EmployeeScreen(viewModel: EmployeeViewModel = EmployeeViewModel()) {
    val employee by viewModel.stateEmployee.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.event.collect { event ->
            when (event) {
                EmployeeEvent.EmployeeLoaded -> {
                    Toast.makeText(
                        context,
                        "Employee Loaded!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is EmployeeEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Name: ${employee?.name?: "Loading..."}"
        )

        Text(
            text = "NIK: ${employee?.nik?: "Loading..."}"
        )

        Text(
            text = "History: ${employee?.history?: "Loading..."}"
        )
    }
}