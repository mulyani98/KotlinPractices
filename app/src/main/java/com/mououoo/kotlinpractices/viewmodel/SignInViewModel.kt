package com.mououoo.kotlinpractices.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {
    private val _isSignedIn = MutableLiveData<Boolean>()
    val isSignedIn: LiveData<Boolean> get() = _isSignedIn

    private val _isAuthorized = MutableLiveData<Boolean>()
    val isAuthorized: LiveData<Boolean> get() = _isAuthorized

    init {
        checkIfSignedIn()
        _isAuthorized.value = false // Initially, not authorized
    }

    private fun checkIfSignedIn() {
        // Check if a user is currently signed in
        val user = FirebaseAuth.getInstance().currentUser
        _isSignedIn.value = user != null
    }

    fun signOut(onSignOutComplete: () -> Unit) {
        FirebaseAuth.getInstance().signOut()
        _isSignedIn.value = false // Update sign-in status
        onSignOutComplete() // Trigger finish() or any action passed as a callback
    }

    fun setAuthorizationStatus(isAuthorized: Boolean) {
        _isAuthorized.value = isAuthorized
    }
}