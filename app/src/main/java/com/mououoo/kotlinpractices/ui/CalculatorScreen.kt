package com.mououoo.kotlinpractices.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mououoo.kotlinpractices.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(
    vm: CalculatorViewModel = viewModel()
) {
    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF6200EE)
        )
    ) {
        val result: Double by vm.result.collectAsStateWithLifecycle()
        val inputText: String by vm.inputText.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Spacer(Modifier.size(8.dp))
                Text(
                    text = inputText
                )

                Spacer(Modifier.size(8.dp))
                Text(
                    text = "="
                )

                Spacer(Modifier.size(8.dp))
                Text(
                    text = result.toString()
                )
            }

            Row {
                Button(
                    onClick = {
                        vm.onPlusClicked(2.0, 3.0)
                        // there is possibility assign value here, forbidden, violates contract
                        // vm.result.value = 2.0
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("+")
                }

                Spacer(Modifier.size(2.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("-")
                }

                Spacer(Modifier.size(2.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("*")
                }
            }

            Row {
                Button(
                    onClick = {
                        vm.onNumberClicked("1")
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("1")
                }

                Spacer(Modifier.size(2.dp))
                Button(
                    onClick = {
                        vm.onNumberClicked("2")
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("2")
                }

                Spacer(Modifier.size(2.dp))
                Button(
                    onClick = {
                        vm.onNumberClicked("3")
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("3")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorScreen() {
    CalculatorScreen()
}