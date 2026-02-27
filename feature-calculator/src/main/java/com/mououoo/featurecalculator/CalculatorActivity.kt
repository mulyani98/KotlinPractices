package com.mououoo.featurecalculator

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mououoo.featurecalculator.ui.CalculatorScreen
import com.mououoo.featurecalculator.ui.theme.MyAppTheme

class CalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                CalculatorScreen()
            }
        }
    }
}