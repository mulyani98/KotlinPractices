package com.mououoo.kotlinpractices.trial.user

class UserApi {
    suspend fun getPosts(): List<Post> {
        return listOf(
            Post("asdjkfa"),
            Post("jangan")
        )
    }

    suspend fun getUser(): User {
        return User(
            username = "mulyani",
            email = "mulyanimu@gmail.com"
        )
    }
}