package com.mououoo.kotlinpractices

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTesting {

    private fun log(message: String) {
        println("[DEMO] $message")
    }

    private fun realElapsedMillis(startNanos: Long) : Long {
        return (System.nanoTime() - startNanos) / 1_000_000 // 1000.milliseconds
    }


    @Test
    fun exampleWithRunTest() {
        val startNanos = System.nanoTime()
        runTest {
            log("Before delay virtualTime = $currentTime ms")
            delay(1000.milliseconds) // The test doesn't need to wait a single real second as long as the delay is running

            log("After delay virtualTime = $currentTime ms")
            assertEquals(1000.milliseconds, currentTime.milliseconds)
        }
        log("Real elapsed time = ${realElapsedMillis(startNanos)} ms")
    }

    @Test
    fun exampleWithRunBlocking() {
        val startNanos = System.nanoTime()
        runBlocking {
            log("Before delay")
            delay(1000.milliseconds) // Uses real time in a normal runBlocking environment
            log("After delay")
        }
        log("Real elapsed time = ${realElapsedMillis(startNanos)} ms")
    }


    @Test
    fun launchedCoroutine_requiresSchedulerProgress() = runTest {
        var result = "Initial"
        launch {
            result = "Updated"
        }

        assertEquals("Initial", result)
        runCurrent()
        assertEquals("Updated", result)
    }

    @Test
    fun launchedCoroutine_canBeJoined() = runTest {
        var result = "Initial"
        val job = launch {
            delay(1000.milliseconds)
            result = "Updated"
        }

        val jobTwo = launch {
            delay(500.milliseconds)
            result = "Updated JobTwo"
        }

        job.join() // wait for job finished
        log("Virtual time = $currentTime ms")
        assertEquals("Updated", result)
    }

    @Test(expected = IllegalStateException::class)
    fun childFailure_isReportedByRunTest() = runTest {
        launch {
            throw IllegalStateException("Unexpected State")
        }

        advanceUntilIdle()
    }

    @Test
    fun dispatchers_shareOneScheduler() = runTest {
        val ioDispatcher = StandardTestDispatcher(testScheduler)
        val defaultDispatcher = StandardTestDispatcher(testScheduler)

        launch (ioDispatcher) {
            log("IO")
        }

        launch (defaultDispatcher) {
            log("DEFAULT")
        }

        advanceUntilIdle()
    }

    @Test
    fun standardDispatcher_logShowsQueuedExecution() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        var state = "Initial"

        log("1. Before launch: state = $state")

        launch (dispatcher) {
            log("3. Coroutine body starts")
            state = "Updated"
            log("4. Coroutine changes state = $state")
        }

        log("2. After launch: state = $state")
        assertEquals("Initial", state)

        runCurrent()

        log("5. After runCurrent: state = $state")
        assertEquals("Updated", state)
    }

    @Test
    fun unconfinedDispatcher_startsImmediatelyButStillSuspend() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        var state = "Initial"

        launch (dispatcher) {
            state = "Started"
            log("Coroutine started: state = $state, time = $currentTime")
            delay(1000.milliseconds)
            state = "Finished"
            log("Coroutine resumed: state = $state, time = $currentTime")
        }

        log("Immediately after launch: state = $state")
        assertEquals("Started", state)

        advanceUntilIdle()

        log("After advanceUntilIdle: state = $state")
        assertEquals("Finished", state)
    }

}