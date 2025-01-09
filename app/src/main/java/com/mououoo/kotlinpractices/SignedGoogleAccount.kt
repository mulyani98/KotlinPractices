package com.mououoo.kotlinpractices

import android.app.PendingIntent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.AuthorizationRequest
import com.google.android.gms.auth.api.identity.AuthorizationResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.Scope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.api.services.drive.DriveScopes
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.mououoo.kotlinpractices.ui.SignedScreen
import com.mououoo.kotlinpractices.ui.theme.MyAppTheme
import com.mououoo.kotlinpractices.viewmodel.SignInViewModel
import kotlinx.coroutines.launch

class SignedGoogleAccount : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signInViewModel: SignInViewModel

    companion object {
        private const val TAG = "GoogleSignIn"
        private const val CLIENT_ID_WEB = "578060869145-97i1evdjtjvq51qn9kjlb0hno15igi3b.apps.googleusercontent.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                SignedScreen(
                    signInViewModel = signInViewModel,
                    onSignOutComplete = {
                        finish()
                        Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize the ViewModel
        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        // Observe if the user is signed in
        signInViewModel.isSignedIn.observe(this) { isSignedIn ->
            val user = auth.currentUser
            if (isSignedIn) {
                // User is signed in
                val displayName = user?.displayName
                val email = user?.email
                Toast.makeText(this, "Already signed in as $displayName, $email", Toast.LENGTH_SHORT).show()
            } else {
                // If the user is not signed in, you can start the sign-in process
                signIn()
            }
        }

        signInViewModel.isAuthorized.observe(this) { isAuthorized ->
            if (isAuthorized) {
                SafeLogKotlin.d(TAG, "User is already authorized for Google Drive")
            }
        }
    }

    private fun signIn() {
        val credentialManager = CredentialManager.create(this)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(CLIENT_ID_WEB)
            .build()

        //import from androidx.CredentialManager
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                //import from androidx.CredentialManager
                val result: GetCredentialResponse = credentialManager.getCredential(
                    request = request,
                    context = this@SignedGoogleAccount,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) { //import from androidx.CredentialManager
                Toast.makeText(this@SignedGoogleAccount, "Google Sign-In failed: ${e.errorMessage}", Toast.LENGTH_SHORT).show()
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
                        val idToken = googleIdTokenCredential.idToken
                        firebaseAuthWithGoogle(idToken)
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
                    val user: FirebaseUser? = auth.currentUser
                    if (user != null) {
                        val displayName = user.displayName
                        val email = user.email
                        val photoUrl = user.photoUrl

                        Toast.makeText(this@SignedGoogleAccount, "Sign-In Success: $displayName, $email", Toast.LENGTH_SHORT).show()
                        SafeLogKotlin.d(TAG, "User: $displayName, $email, $photoUrl")
                        gDriveRequest()
                    }
                } else {
                    Toast.makeText(this@SignedGoogleAccount, "signInWithCredential failure: ${task.exception}", Toast.LENGTH_SHORT).show()
                    SafeLogKotlin.e(TAG, "signIn failure: ${task.exception}")
                }
            }
    }

    private fun gDriveRequest() {
        if (signInViewModel.isAuthorized.value == true) {
            // User is already authorized
            Toast.makeText(this, "Already authorized for Google Drive", Toast.LENGTH_SHORT).show()
            SafeLogKotlin.d(TAG, "Already authorized for Google Drive")
            return
        }

        // Proceed with authorization process
        val requestedScopes = listOf(Scope(DriveScopes.DRIVE_APPDATA))
        val authorizationRequest = AuthorizationRequest.builder().setRequestedScopes(requestedScopes).build()
        Identity.getAuthorizationClient(this)
            .authorize(authorizationRequest)
            .addOnSuccessListener { authorizationResult ->
                if (authorizationResult.hasResolution()) {
                    val pendingIntent: PendingIntent? = authorizationResult.pendingIntent
                    try {
                        if (pendingIntent != null) {
                            authorizeLauncher.launch(
                                IntentSenderRequest.Builder(pendingIntent.intentSender).build()
                            )
                        }
                    } catch (e: SendIntentException) {
                        SafeLogKotlin.e(TAG, "Couldn't start Authorization UI: " + e.localizedMessage)
                    }
                } else {
                    saveToDriveAppFolder(authorizationResult)
                }
            }
            .addOnFailureListener { e -> SafeLogKotlin.e(TAG, "Failed to authorize", e) }
    }

    private val authorizeLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val authorizationResult = Identity.getAuthorizationClient(this).getAuthorizationResultFromIntent(result.data)
            saveToDriveAppFolder(authorizationResult)
        }
    }

    private fun saveToDriveAppFolder(authorizationResult: AuthorizationResult) {
        SafeLogKotlin.d(TAG, "Authorization successful. Proceed to save data to Google Drive = $authorizationResult")
        Toast.makeText(this@SignedGoogleAccount, "Authorization successful: $authorizationResult", Toast.LENGTH_SHORT).show()

        // Set the authorization status to true
        signInViewModel.setAuthorizationStatus(true)
    }
}