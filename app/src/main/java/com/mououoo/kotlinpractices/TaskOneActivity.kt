package com.mououoo.kotlinpractices

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mououoo.kotlinpractices.ui.TaskOneScreen
import com.mououoo.kotlinpractices.ui.theme.MyAppTheme
import com.mououoo.kotlinpractices.viewmodel.CounterViewModel

class TaskOneActivity : AppCompatActivity() {
    private lateinit var viewModel: CounterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CounterViewModel::class.java]

        setContent {
            MyAppTheme {
                TaskOneScreen(viewModel = viewModel)
            }
        }
    }
}
