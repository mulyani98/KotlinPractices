package com.mououoo.featurecalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mououoo.featurecalculator.viewmodel.CalculatorEvent
import com.mououoo.featurecalculator.viewmodel.CalculatorUiState
import com.mououoo.featurecalculator.viewmodel.CalculatorViewModel

@Composable
// Composable function that connects ViewModel to UI layer
fun CalculatorScreen(
    viewModel: CalculatorViewModel
) {
    // Collects StateFlow as Compose State in a lifecycle-aware way
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Passes current state and event handler to UI content
    CalculatorContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
// Main UI layout for calculator
fun CalculatorContent(
    state: CalculatorUiState,
    onEvent: (CalculatorEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {

        // Text for expression
        Text(
            text = state.expression,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Text for result
        Text(
            text = state.result,
            style = MaterialTheme.typography.h2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        val buttons = listOf(
            listOf("7","8","9","/"),
            listOf("4","5","6","*"),
            listOf("1","2","3","-"),
            listOf("C","0","=","+")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            when (label) {
                                "C" -> onEvent(CalculatorEvent.OnClearClick)
                                "=" -> onEvent(CalculatorEvent.OnEqualClick)
                                "+", "-", "*", "/"
                                    -> onEvent(CalculatorEvent.OnOperatorClick(label))
                                else
                                    -> onEvent(CalculatorEvent.OnNumberClick(label))
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(label)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCalculator() {
    CalculatorContent(
        state = CalculatorUiState(
            expression = "7+5",
            result = "= 12"
        ),
        onEvent = {}
    )
}