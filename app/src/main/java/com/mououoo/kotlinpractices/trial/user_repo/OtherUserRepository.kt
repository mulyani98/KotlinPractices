package com.mououoo.kotlinpractices.trial.user_repo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class OtherUserRepository (
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadUser(): String = withContext(ioDispatcher) {
        delay(1000.milliseconds)
        "Mulyani"
    }
}