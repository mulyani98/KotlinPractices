package com.mououoo.featurecalculator.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mououoo.featurecalculator.viewmodel.CalculatorViewModel

class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sets the Compose UI content for this Activity
        setContent {
            MaterialTheme {
                // Provides a Material surface container
                Surface {
                    // Obtains a ViewModel scoped to this Activity
                    val vm: CalculatorViewModel = viewModel()

                    // Passes the ViewModel to the screen composable
                    CalculatorScreen(viewModel = vm)
                }
            }
        }
    }
}