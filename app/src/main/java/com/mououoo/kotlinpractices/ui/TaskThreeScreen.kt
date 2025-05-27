package com.mououoo.kotlinpractices.ui

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mououoo.kotlinpractices.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TaskThreeScreen(
    onRunDefault: (onResult: (String) -> Unit) -> Unit,
    onRunIO: (onResult: (String) -> Unit) -> Unit,
    onRunMain: (onResult: (String) -> Unit) -> Unit,
    onRunUnconfined: (onResult: (String) -> Unit) -> Unit
) {
    val activity = LocalContext.current as? Activity
    val coroutineScope = rememberCoroutineScope()
    var result by remember { mutableStateOf("Belum dijalankan") }

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
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_btn)
                            )
                        }
                        Text(text = stringResource(id = R.string.go_to_task_three))
                        Spacer(modifier = Modifier.weight(1f))
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = result) // ✅ Menampilkan hasil coroutine

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                coroutineScope.launch {
                    result = fetchData()
                }
            }) {
                Text("Jalankan Coroutine")
            }

            Button(onClick = {
                onRunDefault { data ->
                    result = data
                }
            }) {
                Text("Run with Default")
            }
            Button(onClick = {
                onRunIO { data ->
                    result = data
                }
            }) {
                Text("Run with IO")
            }
            Button(onClick = {
                onRunMain { data ->
                    result = data
                }
            }) {
                Text("Run with Main")
            }
            Button(onClick = {
                onRunUnconfined { data ->
                    result = data
                }
            }) {
                Text("Run with Unconfined")
            }
        }
    }
}


// Simulasi suspend function
suspend fun fetchData(): String {
    delay(2000) // Simulasi delay 2 detik
    return "✅ Data berhasil diambil dari coroutine!"
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskThreeScreen() {
    TaskThreeScreen(
        onRunDefault = {},
        onRunIO = {},
        onRunMain = {},
        onRunUnconfined = {}
    )
}

