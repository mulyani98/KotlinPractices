package com.mououoo.kotlinpractices

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mououoo.kotlinpractices.ui.TaskThreeScreen
import com.mououoo.kotlinpractices.ui.theme.MyAppTheme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskThreeActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme {
                TaskThreeScreen(
                    onRunDefault = { onResult -> runWithDispatcher(Dispatchers.Default, onResult) },
                    onRunIO = { onResult -> runWithDispatcher(Dispatchers.IO, onResult) },
                    onRunMain = { onResult -> runWithDispatcher(Dispatchers.Main, onResult) },
                    onRunUnconfined = { onResult -> runWithDispatcher(Dispatchers.Unconfined, onResult) }
                )
            }
        }
    }

    private fun runWithDispatcher(
        dispatcher: CoroutineDispatcher,
        onResult: (String) -> Unit
    ) {
        coroutineScope.launch(dispatcher) {
            val threadName = Thread.currentThread().name
            SafeLogKotlin.d("DispatcherRun", "Running on: $threadName")

            // Ambil data menggunakan suspend function fetchData()
            val data = fetchData()

            // Gabungkan info thread dengan data
            val finalResult = "Running on: $threadName\n$data"

            // Update UI via Main dispatcher
            withContext(Dispatchers.Main) {
                Toast.makeText(this@TaskThreeActivity, finalResult, Toast.LENGTH_SHORT).show()
                onResult(finalResult)
            }
        }
    }

    suspend fun fetchData(): String {
        delay(2000) // Simulasi delay 2 detik
        return "✅ Data berhasil diambil dari coroutine!"
    }
}
