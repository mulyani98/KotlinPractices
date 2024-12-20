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


class GoogleSignInActivity : AppCompatActivity() {

    companion object {
        // Define any static members here
        private const val  CLIENT_ID = "578060869145-n6671mce6ld8akc2hvt0obhtfmohu80i.apps.googleusercontent.com"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                GoogleSignInScreen(
                    onBackClick = { finish()}
                )
            }
        }

        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(CLIENT_ID).build()

        // Initialize Credential Manager
        val credentialManager = CredentialManager.create(this)

        // Create the GetCredentialRequest
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        // Launch a coroutine to call the suspend function
        CoroutineScope(Dispatchers.Main).launch {
            try {
                SafeLogKotlin.d("GSIAnalysis", "Starting Google Sign-In")
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@GoogleSignInActivity,
                )
                SafeLogKotlin.d("GSIAnalysis", "Google Sign-In successful")
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Toast.makeText(this@GoogleSignInActivity, "Google Sign-In failed: ${e.errorMessage}", Toast.LENGTH_SHORT).show()

                SafeLogKotlin.e("GSIAnalysis", "Google Sign-In failed: ${e.errorMessage}")

                if (e is GetCredentialCancellationException) {
                    SafeLogKotlin.e("GSIAnalysis", "Sign-In was cancelled by the user")
                } else {
                    SafeLogKotlin.e("GSIAnalysis", "Sign-In failed due to an error: ${e.cause}")
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
                    SafeLogKotlin.d("GSIAnalysis", "Google ID Token: $idToken")
                    SafeLogKotlin.d("GSIAnalysis", "Display Name: $displayName")

                    // Now you can send this ID Token to your server for validation and authentication
                    // Example: send to your server using a network request

                } catch (e: GoogleIdTokenParsingException) {
                    SafeLogKotlin.e("GSIAnalysis", "Received an invalid Google ID token", e)
                }
            } else {
                // If the credential is not of the expected type
                SafeLogKotlin.e("GSIAnalysis", "Unexpected credential type: ${credential.type}")
            }
        } else {
            // If the credential is not a CustomCredential
            SafeLogKotlin.e("GSIAnalysis", "Unexpected credential type: $credential")
        }
    }

//    private fun handleSignIn(result: GetCredentialResponse) {
//        // Handle the successfully returned credential.
//        when (val credential = result.credential) {
//            is CustomCredential -> {
//                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//                    try {
//                        // Use googleIdTokenCredential and extract id to validate and
//                        // authenticate on your server.
//                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
//                    } catch (e: GoogleIdTokenParsingException) {
//                        SafeLogKotlin.e("GSIAnalysis", "Received an invalid google id token response", e)
//                    }
//                }
//            }
//
//            else -> {
//                // Catch any unrecognized credential type here.
//                SafeLogKotlin.e("GSIAnalysis", "Unexpected type of credential")
//            }
//        }
//    }
/* END [DESIGN 3] */


    /* DO NOT DELETE [DESIGN 2] */
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyAppTheme {
//                GoogleSignInScreen(
//                    onBackClick = { finish()}
//                )
//            }
//        }
//
//        // Initialize Credential Manager
//        val credentialManager = CredentialManager.create(this)
//
//        // Define the Google Sign-In option
//        val googleSignInOption = GetSignInWithGoogleOption.Builder(CLIENT_ID)
//            .build()
//
//        // Create the GetCredentialRequest
//        val getCredentialRequest = GetCredentialRequest.Builder()
//            .addCredentialOption(googleSignInOption)
//            .build()
//
//        // Launch a coroutine to call the suspend function
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    credentialManager.getCredential(this@GoogleSignInActivity, getCredentialRequest)
//                }
//
//                val credential = response.credential as SignInCredential
//                val idToken = credential.googleIdToken
//                val displayName = credential.displayName
//
//                Log.d(TAG, "Google ID Token: $idToken")
//                Log.d(TAG, "Display Name: $displayName")
//            } catch (e: Exception) {
//                Log.e(TAG, "Error retrieving credentials", e)
//            }
//        }
//    }
    /* END [DESIGN 2] */

    /* DO NOT DELETE [DESIGN 1] */
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyAppTheme {
//                GoogleSignInScreen(
//                    onBackClick = { finish()}
//                )
//            }
//        }
//        // Access companion object members like this
//        Log.d("GoogleSignInActivity", "Request Code: $TAG")
//
//        // Create GetSignInIntentRequest with CLIENT_ID
//        val signInIntentRequest = GetSignInIntentRequest.Builder()
//            .setServerClientId(CLIENT_ID) // Set your Web Client ID
//            .build()
//
//        // Get the SignInClient
//        val signInClient: SignInClient = Identity.getSignInClient(this)
//
//        // Start the sign-in process and get the PendingIntent
//        val signInIntentTask = signInClient.getSignInIntent(signInIntentRequest)
//
//        signInIntentTask.addOnSuccessListener { pendingIntent ->
//            try {
//                // Use IntentSenderRequest to launch the PendingIntent
//                val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
//                signInLauncher.launch(intentSenderRequest)
//            } catch (e: Exception) {
//                Log.e(TAG, "Error launching sign-in intent", e)
//            }
//        }.addOnFailureListener { exception ->
//            Log.e(TAG, "Error creating sign-in intent: ", exception)
//        }
//
//    }
//
//    // Register an ActivityResultLauncher for sign-in
//    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
//        if (result.resultCode == RESULT_OK) {
//            val data = result.data
//            if (data != null) {
//                try {
//                    // Manually extract the credentials
//                    val extras = data.extras
//                    if (extras != null) {
//                        val idToken = extras.getString("id_token")
//                        val displayName = extras.getString("display_name")
//
//                        Log.d(TAG, "ID Token: $idToken")
//                        Log.d(TAG, "Display Name: $displayName")
//                    }
//                } catch (e: Exception) {
//                    Log.e(TAG, "Error retrieving sign-in credential", e)
//                }
//            }
//        } else {
//            Log.e(TAG, "Sign-in failed with result code: ${result.resultCode}")
//        }
//    }
    /* END [DESIGN 1] */
}

