package com.mououoo.kotlinpractices

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class CoroutineActivity : AppCompatActivity() {
    private val activityScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var fetchJob: Job? = null
    private var dispatcherJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val runFetchButton = findViewById<Button>(R.id.runFetchButton)
        val cancelFetchButton = findViewById<Button>(R.id.cancelFetchButton)
        val runDefaultButton = findViewById<Button>(R.id.runDefaultButton)
        val runIOButton = findViewById<Button>(R.id.runIOButton)
        val runMainButton = findViewById<Button>(R.id.runMainButton)
        val runUnconfinedButton = findViewById<Button>(R.id.runUnconfinedButton)
        val cancelDispatcherButton = findViewById<Button>(R.id.cancelDispatcherButton)

        runFetchButton.setOnClickListener {
            fetchJob = activityScope.launch {
                resultTextView.text = getString(R.string.fetch_data)
                try {
                    val result = fetchData()
                    resultTextView.text = result
                } catch (e: CancellationException) {
                    resultTextView.text = getString(R.string.fetch_data_canceled)
                }
            }
        }

        cancelFetchButton.setOnClickListener {
            fetchJob?.cancel()
        }

        runDefaultButton.setOnClickListener {
            dispatcherJob = runWithDispatcher(Dispatchers.Default, resultTextView)
        }

        runIOButton.setOnClickListener {
            dispatcherJob = runWithDispatcher(Dispatchers.IO, resultTextView)
        }

        runMainButton.setOnClickListener {
            dispatcherJob = runWithDispatcher(Dispatchers.Main, resultTextView)
        }

        runUnconfinedButton.setOnClickListener {
            dispatcherJob = runWithDispatcher(Dispatchers.Unconfined, resultTextView)
        }

        cancelDispatcherButton.setOnClickListener {
            if (dispatcherJob?.isActive == true) {
                dispatcherJob?.cancel(CancellationException(getString(R.string.coroutine_dispatcher_canceled)))
                resultTextView.text = getString(R.string.coroutine_dispatcher_canceled)
            } else {
                resultTextView.text = getString(R.string.dispatcher_not_running)
            }
        }
    }

    private fun runWithDispatcher(
        dispatcher: CoroutineDispatcher,
        resultTextView: TextView
    ): Job {
        return activityScope.launch(dispatcher) {
            val threadName = Thread.currentThread().name

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@CoroutineActivity,
                    "Running on: $threadName",
                    Toast.LENGTH_SHORT
                ).show()
                resultTextView.text = getString(R.string.running_on, threadName)
            }
        }
    }

    private suspend fun fetchData(): String {
        delay(2000)
        return withContext(Dispatchers.Main) {
            getString(R.string.fetch_data_success)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.cancel()
    }

}