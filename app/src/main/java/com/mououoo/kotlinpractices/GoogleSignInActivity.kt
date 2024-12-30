package com.mououoo.kotlinpractices

import android.os.Bundle
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
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.mououoo.kotlinpractices.ui.GoogleSignInScreen
import com.mououoo.kotlinpractices.ui.theme.MyAppTheme
import kotlinx.coroutines.launch


class GoogleSignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        // Define any static members here
        private const val  CLIENT_ID = "578060869145-bcbhaecvb7p7dkvfccccibp8oo6lc5jt.apps.googleusercontent.com"
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

        /*
        * Source:
        *       https://medium.com/firebase-indonesia/solusi-googlesigninclient-dan-signinclient-deprecated-pada-firebase-auth-dengan-google-04984a0b90df
        *       https://stackoverflow.com/questions/78503580/how-to-migrate-from-googlesigninclient-to-credential-manager-for-user-authentica
        *       https://medium.com/@saithitlwin/google-sign-in-for-android-using-credential-manager-dca95eccf794
        */
        signIn()
    }

    private fun signIn() {
        val credentialManager = CredentialManager.create(this) //import from androidx.CredentialManager

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(CLIENT_ID) //from https://console.firebase.google.com/project/my-firebase-chat-2aac3/authentication/providers
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
                SafeLogKotlin.d(TAG, "Client ID: $CLIENT_ID")
                SafeLogKotlin.d(TAG, "Request created: $request")
                SafeLogKotlin.d(TAG, e.message.toString())
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
                    SafeLogKotlin.d(TAG, "signInWithCredential:success")
//                    val user: FirebaseUser? = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    SafeLogKotlin.e(TAG, "signInWithCredential:failure" + task.exception)
//                    updateUI(null)
                }
            }
    }

}

