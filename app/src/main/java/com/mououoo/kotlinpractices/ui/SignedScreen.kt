package com.mououoo.kotlinpractices.ui

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mououoo.kotlinpractices.R
import com.mououoo.kotlinpractices.viewmodel.SignInViewModel

@Composable
fun SignedScreen(
    signInViewModel: SignInViewModel,
    onSignOutComplete: () -> Unit
) {
    val activity = LocalContext.current as? Activity
    val isSignedIn by signInViewModel.isSignedIn.observeAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            activity?.finish()
                        }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_btn))
                        }
                        Text(text = stringResource(id = R.string.google_sign_in))
                        Spacer(modifier = Modifier.weight(1f))
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.google_sign_in),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            // Sign Out Button
            Button(
                onClick = {
                    if (isSignedIn) {
                        signInViewModel.signOut {
                            onSignOutComplete() // Notify the activity to finish
                        }
                    }
                },
                enabled = isSignedIn, // Disable the button if not signed in
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isSignedIn) MaterialTheme.colors.secondary else MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                    contentColor = if (isSignedIn) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter) // Align button to the bottom
            ) {
                Icon(
                    Icons.Filled.Logout,
                    contentDescription = stringResource(R.string.sign_out),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.sign_out),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignedScreen() {
    SignedScreen(
        signInViewModel = SignInViewModel(),
        onSignOutComplete = {}
    )
}