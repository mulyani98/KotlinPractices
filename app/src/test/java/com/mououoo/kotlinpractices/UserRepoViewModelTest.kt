package com.mououoo.kotlinpractices

import com.mououoo.kotlinpractices.trial.user_repo.FakeUserRepo
import com.mououoo.kotlinpractices.trial.user_repo.UserRepoViewModel
import com.mououoo.kotlinpractices.trial.user_repo.UserUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class UserRepoViewModelTest {

    @Test
    fun loadUser_updatesUiState() = runTest {
        val repository = FakeUserRepo()
        val viewModel = UserRepoViewModel(repository)

        viewModel.loadUser()

        advanceUntilIdle()

        assertEquals(
            UserUiState.Success("Mulyani"),
            viewModel.uiState.value
        )
    }

}