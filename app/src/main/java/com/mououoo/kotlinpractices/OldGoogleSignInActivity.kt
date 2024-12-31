package com.mououoo.kotlinpractices

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.mououoo.kotlinpractices.ui.GoogleSignInScreen
import com.mououoo.kotlinpractices.ui.theme.MyAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OldGoogleSignInActivity : AppCompatActivity() {

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

        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(CLIENT_ID_WEB).build()

        // Initialize Credential Manager
        val credentialManager = CredentialManager.create(this)

        // Create the GetCredentialRequest
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        // Launch a coroutine to call the suspend function
        CoroutineScope(Dispatchers.Main).launch {
            try {
                SafeLogKotlin.d(TAG, "Starting Google Sign-In")
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@OldGoogleSignInActivity,
                )
                SafeLogKotlin.d(TAG, "Google Sign-In successful")
                Toast.makeText(this@OldGoogleSignInActivity, "Google Sign-In successful", Toast.LENGTH_SHORT).show()
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Toast.makeText(this@OldGoogleSignInActivity, "Google Sign-In failed: ${e.errorMessage}", Toast.LENGTH_SHORT).show()

                SafeLogKotlin.e(TAG, "Google Sign-In failed: ${e.errorMessage}")

                if (e is GetCredentialCancellationException) {
                    SafeLogKotlin.e(TAG, "Sign-In was cancelled by the user")
                } else {
                    SafeLogKotlin.e(TAG, "Sign-In failed due to an error: ${e.cause}")
                    SafeLogKotlin.e(TAG, "Sign-In failed due to an error: ${e.type}")
                }
            }
        }
        
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        if (credential is CustomCredential) {
            // Check if the credential type is Google ID Token credential
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    // Extract the Google ID token from the credential
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                    // Use the ID Token to validate and authenticate the user on your server
                    val idToken = googleIdTokenCredential.idToken
                    val displayName = googleIdTokenCredential.displayName

                    // Log the values to ensure everything is correct
                    SafeLogKotlin.d(TAG, "Google ID Token: $idToken")
                    SafeLogKotlin.d(TAG, "Display Name: $displayName")

                    // Now you can send this ID Token to your server for validation and authentication
                    // Example: send to your server using a network request

                } catch (e: GoogleIdTokenParsingException) {
                    SafeLogKotlin.e(TAG, "Received an invalid Google ID token", e)
                }
            } else {
                // If the credential is not of the expected type
                SafeLogKotlin.e(TAG, "Unexpected credential type: ${credential.type}")
            }
        } else {
            // If the credential is not a CustomCredential
            SafeLogKotlin.e(TAG, "Unexpected credential type: $credential")
        }
    }
}

