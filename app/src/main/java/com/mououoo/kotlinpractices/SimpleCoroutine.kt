package com.mououoo.kotlinpractices

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class SimpleCoroutine : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnStartCoroutine: Button
    private lateinit var btnCancel: Button
    private var wasCancelled = false

    private var job: Job? = null  // control coroutine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_coroutine)

        tvStatus = findViewById(R.id.tvStatus)
        progressBar = findViewById(R.id.progressBar)
        btnStartCoroutine = findViewById(R.id.btnStartCoroutine)
        btnCancel = findViewById(R.id.btnCancel)

        btnStartCoroutine.setOnClickListener {
            startCoroutineTask()
        }

        btnCancel.setOnClickListener {
            cancelCoroutineTask()
        }
    }

    private fun startCoroutineTask() {
        resetUI()
        tvStatus.text = getString(R.string.running_coroutine_task)
        btnCancel.isEnabled = true

        // Launch coroutine on thread background
        job = CoroutineScope(Dispatchers.Default).launch {
            try {
                delay(3000) // delay non-blocking 3 sec
                withContext(Dispatchers.Main) {
                    tvStatus.text = getString(R.string.coroutine_task_finished)
                }
            } catch (e: CancellationException) {
                wasCancelled = true
            } finally {
                withContext(Dispatchers.Main) {
                    if (!wasCancelled) {
                        progressBar.visibility = ProgressBar.GONE
                        btnCancel.isEnabled = false
                    }
                }
            }
        }
    }

    private fun cancelCoroutineTask() {
        job?.cancel()
        tvStatus.text = getString(R.string.cancelled_by_user)
        progressBar.visibility = ProgressBar.GONE
        btnCancel.isEnabled = false
    }

    private fun resetUI() {
        progressBar.visibility = ProgressBar.VISIBLE
        tvStatus.text = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()  // cancel coroutine
    }
}