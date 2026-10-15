package com.mououoo.kotlinpractices

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException

class SupervisorJobTestClass {

    @Test
    fun test() {
        supervisorJobTest()
    }

    private fun supervisorJobTest() = runTest {
        val parentJob = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        // Launch child jobs
        val child1 = scope.launch {
            println("Child 1 started")
            delay(1000)
            println("Child 1 completed")
        }

        val child2 = scope.launch {
            println("Child 2 started")
            delay(500)
            throw RuntimeException("Child 2 failed") // This won't cancel child1 or the scope
        }

        runBlocking {
            delay(1500)
            println("Parent is active: ${parentJob.isActive}")
        }

        child1.join()
        child2.join()
    }

    @Test
    fun testTwo() {
        supervisorJobTestTwo()
    }

    private fun supervisorJobTestTwo() = runTest {
        val supervisorJob = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + supervisorJob)

        val child1 = scope.launch {
            try {
                println("Child 1 started")
                delay(3000)
                println("Child 1 finished")
            } catch (e: CancellationException) {
                println("Child 1 cancelled due to parent failure")
            }
        }

        val child2 = scope.launch {
            println("Child 2 started")
            delay(1000)
            println("Cancelling parent job")
            supervisorJob.cancel() // Cancels the parent → all children cancelled
        }

        runBlocking {
            delay(4000)
        }

        child1.join()
        child2.join()
    }
}