package com.mououoo.kotlinpractices.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mououoo.kotlinpractices.AboutActivity
import com.mououoo.kotlinpractices.GoogleSignInActivity
import com.mououoo.kotlinpractices.KotlinNotesActivity
import com.mououoo.kotlinpractices.R
import com.mououoo.kotlinpractices.TaskOneActivity
import com.mououoo.kotlinpractices.TaskTwoActivity

@Composable
fun MainScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.app_name))
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            // Navigate to new activity
                            context.startActivity(Intent(context, AboutActivity::class.java))
                        }) {
                            Icon(Icons.Filled.Info, contentDescription = stringResource(R.string.info))
                        }
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
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center), // Center the Column
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Center content inside the Column
            ) {
                // Button to Task One
                Button(onClick = {
                    // Navigate to TaskTwoActivity
                    context.startActivity(Intent(context, TaskOneActivity::class.java))
                }) {
                    Text(text = stringResource(id = R.string.go_to_task_one))
                }

                // Button to Task Two
                Button(onClick = {
                    // Navigate to TaskTwoActivity
                    context.startActivity(Intent(context, TaskTwoActivity::class.java))
                }) {
                    Text(text = stringResource(id = R.string.go_to_task_two))
                }

                // Button to Kotlin Notes
                Button(onClick = {
                    // Navigate to KotlinNotesActivity
                    context.startActivity(Intent(context, KotlinNotesActivity::class.java))
                }) {
                    Text(text = stringResource(id = R.string.go_to_kotlin_notes))
                }

                // Button to Google Sign In
                Button(onClick = {
                    // Navigate to Google Sign In Activity
                    context.startActivity(Intent(context, GoogleSignInActivity::class.java))
                }) {
                    Text(text = stringResource(id = R.string.go_to_google_sign_in))
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}