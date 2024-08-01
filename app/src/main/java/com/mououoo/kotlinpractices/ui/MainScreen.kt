package com.mououoo.kotlinpractices.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mououoo.kotlinpractices.viewmodel.CounterViewModel

@Composable
fun MainScreen(viewModel: CounterViewModel) {
    // Observe LiveData from ViewModel
    val value1 = viewModel.valueX.collectAsState()
    val value2 = viewModel.valueY.collectAsState()
    val value3 = viewModel.valueZ.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Text fields displaying the values
        Text(text = "Value 1: ${value1.value}")
        Text(text = "Value 2: ${value2.value}")
        Text(text = "Value 3: ${value3.value}")

        // Button to increase values
        Button(onClick = { viewModel.increaseValueByFive() }) {
            Text("Increase Values")
        }
    }
}