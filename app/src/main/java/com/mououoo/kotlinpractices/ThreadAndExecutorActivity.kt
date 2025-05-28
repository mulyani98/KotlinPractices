package com.mououoo.kotlinpractices

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ThreadAndExecutorActivity : AppCompatActivity() {
    private lateinit var btnThread: Button
    private lateinit var btnExecutor: Button
    private lateinit var btnCancel: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvStatus: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    // Executor task
    private var executorFuture: Future<*>? = null

    // Thread task
    private var runningThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_executor)

        btnThread = findViewById(R.id.btnStartThread)
        btnExecutor = findViewById(R.id.btnStartExecutor)
        btnCancel = findViewById(R.id.btnCancel)
        progressBar = findViewById(R.id.progressBar)
        tvStatus = findViewById(R.id.tvStatus)

        btnThread.setOnClickListener {
            startWithThread()
        }

        btnExecutor.setOnClickListener {
            startWithExecutor()
        }

        btnCancel.setOnClickListener {
            cancelTask()
        }
    }

    private fun startWithThread() {
        resetUI()
        tvStatus.text = getString(R.string.running_with_thread)
        btnCancel.isEnabled = true

        runningThread = Thread {
            try {
                Thread.sleep(3000) // Delay 3 sec
                updateStatus(getString(R.string.thread_finished))
            } catch (e: InterruptedException) {
                updateStatus(getString(R.string.thread_cancelled_interrupted))
            } finally {
                finishTask()
            }
        }
        runningThread?.start()
    }

    private fun startWithExecutor() {
        resetUI()
        tvStatus.text = getString(R.string.running_with_executor)
        btnCancel.isEnabled = true

        executorFuture = executor.submit {
            try {
                Thread.sleep(3000)  // Delay 3 sec
                updateStatus(getString(R.string.executor_finished))
            } catch (e: InterruptedException) {
                updateStatus(getString(R.string.executor_task_cancelled_interrupted))
            } finally {
                finishTask()
            }
        }
    }

    private fun cancelTask() {
        btnCancel.isEnabled = false
        tvStatus.text = getString(R.string.cancelling)

        runningThread?.interrupt()
        executorFuture?.cancel(true)
    }

    private fun resetUI() {
        progressBar.visibility = View.VISIBLE
        tvStatus.text = ""
    }

    private fun updateStatus(text: String) {
        handler.post {
            tvStatus.text = text
        }
    }

    private fun finishTask() {
        handler.post {
            progressBar.visibility = View.GONE
            btnCancel.isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdownNow()
        runningThread?.interrupt()
    }
}