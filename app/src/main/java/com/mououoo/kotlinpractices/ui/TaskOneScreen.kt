package com.mououoo.kotlinpractices.ui

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mououoo.kotlinpractices.R
import com.mououoo.kotlinpractices.viewmodel.CounterViewModel

@Composable
fun TaskOneScreen(viewModel: CounterViewModel) {
    // Observe LiveData from ViewModel
    val value1 = viewModel.valueX.observeAsState()
    val value2 = viewModel.valueY.observeAsState()
    val value3 = viewModel.valueZ.observeAsState()
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            activity?.finish()
                        }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_btn))
                        }
                        Text(text = stringResource(id = R.string.go_to_task_one))
                        Spacer(modifier = Modifier.weight(1f))
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Scrollable content
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp) // Space for the back button
            ) {
                item {
                    Text(
                        text = stringResource(id = R.string.position),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp))
                }

                item {
                    // Text fields displaying the values
                    Text(text = context.getString(R.string.value_x, value1.value))
                    Text(text = context.getString(R.string.value_y, value2.value))
                    Text(text = context.getString(R.string.value_z, value3.value),
                        modifier = Modifier.padding(bottom = 16.dp))
                }

                item {
                    // Button to increase values
                    Button(onClick = { viewModel.increaseValueByFive() }) {
                        Text(text = stringResource(id = R.string.increase_value))
                    }
                }

                item {
                    // Button to reset values
                    Button(onClick = { viewModel.resetValueToDefault() }) {
                        Text(text = stringResource(id = R.string.reset_value))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskOneScreen() {
    val viewModel = CounterViewModel()
    TaskOneScreen(
        viewModel = viewModel
    )
}