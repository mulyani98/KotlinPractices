package com.mououoo.kotlinpractices.trial.user_repo

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class UserRepoImpl : UserRepo {
    override suspend fun getUserName(): String {
        delay(1000.milliseconds)
        return "Mulyani"
    }
}