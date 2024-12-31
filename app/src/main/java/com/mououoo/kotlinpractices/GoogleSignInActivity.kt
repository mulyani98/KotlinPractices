package com.mououoo.kotlinpractices

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.mououoo.kotlinpractices.ui.GoogleSignInScreen
import com.mououoo.kotlinpractices.ui.theme.MyAppTheme
import kotlinx.coroutines.launch


class GoogleSignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        // Define any static members here
        private const val  CLIENT_ID_DEBUG = "578060869145-n6671mce6ld8akc2hvt0obhtfmohu80i.apps.googleusercontent.com"
        private const val  CLIENT_ID_RELEASE = "578060869145-bcbhaecvb7p7dkvfccccibp8oo6lc5jt.apps.googleusercontent.com"
        private const val  CLIENT_ID_WEB = "578060869145-97i1evdjtjvq51qn9kjlb0hno15igi3b.apps.googleusercontent.com"
        private const val  TAG = "GoogleSignIn"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                GoogleSignInScreen()
            }
        }

        // Initialize Firebase Auth
        auth = Firebase.auth
        signIn()
    }

    private fun signIn() {
        val credentialManager = CredentialManager.create(this) //import from androidx.CredentialManager

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(CLIENT_ID_WEB) //from https://console.firebase.google.com/project/my-firebase-chat-2aac3/authentication/providers
            .build()

        val request = GetCredentialRequest.Builder() //import from androidx.CredentialManager
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result: GetCredentialResponse = credentialManager.getCredential( //import from androidx.CredentialManager
                    request = request,
                    context = this@GoogleSignInActivity,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) { //import from androidx.CredentialManager
                Toast.makeText(this@GoogleSignInActivity, "Google Sign-In failed: ${e.errorMessage}", Toast.LENGTH_SHORT).show()
                SafeLogKotlin.d(TAG, "Client ID: $CLIENT_ID_WEB")
                SafeLogKotlin.d(TAG, "Request created: $request")
                SafeLogKotlin.e(TAG, e.message.toString())
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        SafeLogKotlin.e(TAG, "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    SafeLogKotlin.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                SafeLogKotlin.e(TAG, "Unexpected type of credential")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this@GoogleSignInActivity, "Google Sign-In Success", Toast.LENGTH_SHORT).show()
                    SafeLogKotlin.d(TAG, "signInWithCredential: success")
                    SafeLogKotlin.d(TAG, "user: $user")
                } else {
                    // If sign in fails, display a message to the user.
                    SafeLogKotlin.e(TAG, "signInWithCredential failure: $task.exception")
                }
            }
    }

}

