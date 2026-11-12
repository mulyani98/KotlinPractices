package com.mououoo.kotlinpractices.trial.user_repo

interface UserRepo {
    suspend fun getUserName(): String
}