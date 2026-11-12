package com.mououoo.kotlinpractices.trial.user_repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserRepoViewModel (
    private val repository : UserRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Idle)

    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun loadUser() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading

            try {
                val userName = repository.getUserName()
                _uiState.value = UserUiState.Success(userName)
            } catch (exception: Exception) {
                _uiState.value = UserUiState.Error(exception.message ?: "Unknown Error")
            }

        }
    }
}