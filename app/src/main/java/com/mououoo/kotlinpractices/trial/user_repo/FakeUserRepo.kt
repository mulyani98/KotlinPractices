package com.mououoo.kotlinpractices.trial.user_repo

class FakeUserRepo : UserRepo {
    override suspend fun getUserName(): String {
        return "Mulyani"
    }
}