package com.mououoo.kotlinpractices

import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class TestClass {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun test() {
        startWithThread()
    }

    private fun startWithThread() = runTest {
        println("test")

        // The code here runs on the main thread
        // running thread
        val runningThread = Thread {
            // The code here runs on the main worker thread
            try {
                println("${System.currentTimeMillis()}: thread is running")
                Thread.sleep(3000) // Delay 3 sec
                println("${System.currentTimeMillis()}: thread after delay")
            } catch (e: InterruptedException) {
                println("${System.currentTimeMillis()}: error $e")
            }
        }
        runningThread.start()
        // "After executing runningThread.start(), the subsequent code will continue to run independently
        // and will not depend on the worker thread process.

        runningThread.join() // Join the thread to wait for its completion.
        println("${System.currentTimeMillis()}: after join")
    }
}