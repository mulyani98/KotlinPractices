package com.mououoo.kotlinpractices

import com.mououoo.kotlinpractices.trial.user_repo.OtherUserRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRepositoryTest {

    @Test
    fun loadUser_returnsExpectedName() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val repository = OtherUserRepository(dispatcher)

        val result = repository.loadUser()
        assertEquals("Mulyani", result)
    }

}