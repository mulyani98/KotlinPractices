package com.mououoo.kotlinpractices

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class FlowDemoTest {

    private fun log(msg: String) = println("[DEMO] $msg")

    @Test
    fun `cold flow test`() = runBlocking {
        val coldFlow = flow {
            log("STARTED")
            emit(1)
            emit(2)
            emit(3)
            emit(3)
            emit("4")
            emit(4)
        }

        val secondColdFlow = flow {
            log("STARTED Second")
            emit(1)
            emit(2)
        }

        log("First Collector")
        coldFlow.collect {
            log("Collected data: $it")
        }

        log("Second Collector")
        coldFlow.collect {
            log("Collected data B: $it")
        }

        log("Second Cold Flow collector")
        secondColdFlow.collect {
            log("Collected second stream: $it")
        }
    }

    @Test
    fun `operator flow demo`() = runBlocking {
        flowOf(1, 2, 3, 4, 5, 6)
            .filter {
                it % 2 == 0
            }

            .also {
                log("Filter applied (even numbers only)")
            }

            .map {
                it * 10
            }

            .also {
                log("Map applied (multiple by 10)")
            }

            .transform {
                emit(it + 5)
                emit(it + 2)
            }

            .also {
                log("Transform applied (plus 5)")
            }

            /* Notes:

            .take(3) // collect 3 first data -> 1,2,3
            .drop(2) // drop 2 first data -> 3,4,5
            .distinctUntilChanged() // collect only unique number for ex 1, 2, 2, 3, 1 -> 1, 2, 3, 1 (previous value only, boom)

            */

            .collect {
                log("Result: $it")
            }
    }

    @Test
    fun `combine vs zip`() = runBlocking {
        val a = flow {
            emit("A")
            delay(100.milliseconds)
            emit("B")
            delay(100.milliseconds)
            emit("C")
        }

        val b = flow {
            delay(50.milliseconds)
            emit(1)
            delay(100.milliseconds)
            emit(2)
        }

        log("-- combine --")
        a.combine(b) { x, y ->
            "$x$y"
        }.collect {
            log("combine result: $it")
        }

        log("-- zip --")
        a.zip(b) { x, y ->
            "$x$y"
        }.collect {
            log("zip result: $it")
        }
    }

}