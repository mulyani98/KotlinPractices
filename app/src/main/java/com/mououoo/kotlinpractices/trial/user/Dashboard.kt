package com.mououoo.kotlinpractices.trial.user

data class Dashboard(
    val user: User,
    val posts: List<Post>
)