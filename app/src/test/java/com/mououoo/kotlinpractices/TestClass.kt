package com.mououoo.kotlinpractices

import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TestClass {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun test() {
        startWithThread()
    }

    private fun startWithThread() = runTest {
        println("tes")

        // code di sini adalah main thread
        // running thread
        val runningThread = Thread {
            // code di sini worker thread
            try {
                println("${System.currentTimeMillis()}: thread is running")
                Thread.sleep(3000) // Delay 3 sec
                println("${System.currentTimeMillis()}: thread after delay")
            } catch (e: InterruptedException) {
                println("${System.currentTimeMillis()}: error $e")
            }
        }
        runningThread.start()
        // setelah eksekusi runningThread.start(), maka akan eksekusi code lain
        // dan tidak akan bergantung pada proses worker thread

        runningThread.join() // menunggu thread yg dieksekusi selesai
        println("${System.currentTimeMillis()}: setelah join")
    }
}