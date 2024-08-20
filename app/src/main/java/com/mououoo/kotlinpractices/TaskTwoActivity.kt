package com.mououoo.kotlinpractices

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mououoo.kotlinpractices.ui.TaskTwoScreen
import com.mououoo.kotlinpractices.viewmodel.ArraySummationViewModel

class TaskTwoActivity : AppCompatActivity() {
    private lateinit var viewModel: ArraySummationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ArraySummationViewModel::class.java]
        setContent {
            TaskTwoScreen(
                viewModel = viewModel,
                onBackClick = { finish() }
            )
        }
    }
}