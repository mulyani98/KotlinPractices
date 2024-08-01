package com.mououoo.kotlinpractices

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mououoo.kotlinpractices.ui.MainScreen
import com.mououoo.kotlinpractices.ui.theme.MyAppTheme
import com.mououoo.kotlinpractices.viewmodel.CounterViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CounterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CounterViewModel::class.java)

        setContent {
            MyAppTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}