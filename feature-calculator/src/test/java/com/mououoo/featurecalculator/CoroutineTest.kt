package com.mououoo.featurecalculator

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTest {

    fun threadForKotlin() {
        val thread = Thread {
            println("Running on thread: ${Thread.currentThread()}")
        }
        thread.start()
        thread.join()
    }

    @Test
    fun testThread() {
        threadForKotlin()
    }

    // ---------------------
    fun coroutineCheck(): Job {
        val scope = CoroutineScope(Dispatchers.Default)
        return scope.launch {
            println("Running on Coroutine")
        }
    }

    @Test
    fun testCoroutine() {
        runTest {
            coroutineCheck().join()
        }
    }

    // ----------------- scope test
    /*
    *
    fun coroutineCheck() = runTest {
        val scope = (this)
        scope.launch {
            println("Running on Coroutine")
        }
    }

    @Test
    fun testCoroutine() {
        coroutineCheck()
    }
    *
    */

    /*
    * coroutineCheck() → launch coroutine
    * launch() → menghasilkan Job
    * Job dikembalikan ke test
    * .join() → tunggu Job sampai coroutine selesai
    * baru test selesai
    *
    */

    // ---------------------

    suspend fun suspendCoroutine() {
        delay(1000.milliseconds)
        println("Suspend Coroutine")
    }

    @Test
    fun testSuspendPR() = runTest {
        // apakah "after suspendCoroutine" akan muncul setelah "suspendCoroutine"? YES
        suspendCoroutine()
        println("After Suspend Coroutine")
    }

    // ----------------------------------

    /*
    * PR:

misal ada fungsi:

var resumed = false
suspend fun suspendCoroutine() {
        delay(3000.milliseconds)
        Log.d("Test", "suspendCoroutine")
        resumed = true
    }
// TODO buat test untuk cek resumed ketika kurang dari 3000, pas 3000, dan lebih dari 3000

* cari bedanya
SupervisorJob() dan Job()

praktikkan di unit test

 *
 * coba scope.runCatching()

 */

    private var resumed = false

    private suspend fun suspendCoroutinePR() {
        delay(3000.milliseconds)
        println("suspendCoroutine")
        resumed = true
    }

    // =========================
    // delay < 3000 ms
    // =========================

    @Test
    fun `less than 3000ms`() = runTest {
        resumed = false

        launch {
            suspendCoroutinePR()
        }

        advanceTimeBy(2999)

        println("Time: 2999ms")
        println("resumed: $resumed")
    }

    // =========================
    // delay = 3000 ms
    // =========================

    @Test
    fun `exactly 3000ms`() = runTest {
        resumed = false

        launch {
            suspendCoroutinePR()
        }

        advanceTimeBy(3000)
        runCurrent()

        println("Time: 3000ms")
        println("resumed: $resumed")
    }

    // =========================
    // delay > 3000 ms
    // =========================

    @Test
    fun `more than 3000ms`() = runTest {
        resumed = false

        launch {
            suspendCoroutinePR()
        }

        advanceTimeBy(3001)
        runCurrent()

        println("Time: 3001ms")
        println("resumed: $resumed")
    }

    // =========================
    // Job
    // =========================

    @Test
    fun jobTest() {

        val scope = CoroutineScope(Job())

        val childA = scope.launch {
            println("Child A: started")

            throw RuntimeException("Child A failed")
        }

        val childB = scope.launch {
            println("Child B: started")

            delay(1000)

            println("Child B: finished")
        }

        Thread.sleep(100)

        println("Child A cancelled: ${childA.isCancelled}")
        println("Child B cancelled: ${childB.isCancelled}")

        scope.cancel()
    }

    // =========================
    // SupervisorJob
    // =========================

    @Test
    fun supervisorJobTest() = runTest {

        val dispatcher = StandardTestDispatcher(testScheduler)

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            println("Exception: ${throwable.message}")
        }

        val scope = CoroutineScope(
            SupervisorJob() + dispatcher + exceptionHandler
        )

        val childA = scope.launch {
            println("Child A: started")

            throw RuntimeException("Child A failed")
        }

        val childB = scope.launch {
            println("Child B: started")

            delay(1000)

            println("Child B: finished")
        }

        childA.join()

        advanceTimeBy(1000)
        runCurrent()

        println("Child A cancelled: ${childA.isCancelled}")
        println("Child B cancelled: ${childB.isCancelled}")

        scope.cancel()
    }

    // =========================
    // runCatching
    // =========================

    @Test
    fun `runCatching direct exception`() = runTest {

        val result = runCatching {
            throw RuntimeException("Something went wrong")
        }

        println("isSuccess: ${result.isSuccess}")
        println("isFailure: ${result.isFailure}")
        println("exception: ${result.exceptionOrNull()}")
    }

    // ----------------------------------


    @Test
    fun testAsync() = runTest {
        val result = async {
            delay(1000.milliseconds)
            10 + 20
        }

        println("Doing other work...")

        println("Result = ${result.await()}")
    }

    suspend fun getUser(): String {
        delay(1000)
        return "User"
    }

    suspend fun getPosts(): List<String> {
        delay(1000)
        return listOf("Post 1", "Post 2")
    }

    @Test
    fun testParallelAsync() = runTest {
        val user = async {
            getUser()
        }

        val posts = async {
            getPosts()
        }

        println(user.await())
        println(posts.await())
    }

    @Test
    fun testParallelLaunch() = runTest {
        val user = launch {
            getUser()
        }

        val posts = launch {
            getPosts()
        }

        println(user)
        println(posts)
    }
}